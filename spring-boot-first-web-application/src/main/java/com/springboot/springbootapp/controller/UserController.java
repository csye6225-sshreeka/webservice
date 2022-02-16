package com.springboot.springbootapp.controller;


import com.springboot.springbootapp.entity.User;

import com.springboot.springbootapp.errors.RegistrationStatus;
import com.springboot.springbootapp.repository.UserRepository;
import com.springboot.springbootapp.service.UserService;
import com.springboot.springbootapp.validators.UserValidator;
import java.util.Base64;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping(path = "v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserRepository repository;


    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity getUser( @PathVariable("username") String username , HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String sd = authorizationHeader.replace("Basic ", "");
        byte[] decodedBytes = Base64.getDecoder().decode(sd);
        String decoded = new String(decodedBytes);
        String[] parts = decoded.split(":");

        if (username.equals(parts[0])) {
            User user = userService.getUser(parts[0]);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not authorized");
    }


    @PutMapping(value="/update")
    public ResponseEntity updateUser(@RequestBody User user, HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String sd = authorizationHeader.replace("Basic ", "");
        byte[] decodedBytes = Base64.getDecoder().decode(sd);
        String decoded = new String(decodedBytes);
        String[] parts = decoded.split(":");

        User existingUser = repository.findById(user.getId()).orElse(null);

        if (!existingUser.getEmailId().equals(parts[0])){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" 400 Bad Request, cant update ");

        }
        if (userService.pwdvalidate(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(user.getId()+":" +"Invalid password");

        }
        if ((user.getEmailId() != null) || (user.getPassword().equals("") || (user.getFname().equals("")) || (user.getLname().equals("")))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" 400 Bad Request");
        } else {
            User updated_user = userService.updateUser(user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updated_user);
        }
    }
    @PostMapping(value="/add")
    public ResponseEntity register(@Valid @RequestBody User user, BindingResult errors, HttpServletResponse response) throws Exception{
        RegistrationStatus registrationStatus;

        if(errors.hasErrors()) {
            registrationStatus = userService.getRegistrationStatus(errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(registrationStatus);
        }else {
            registrationStatus = new RegistrationStatus();
            userService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
    }

}
