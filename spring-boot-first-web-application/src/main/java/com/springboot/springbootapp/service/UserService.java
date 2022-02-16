package com.springboot.springbootapp.service;


import com.springboot.springbootapp.entity.CustomUserInfo;
import com.springboot.springbootapp.entity.User;
import com.springboot.springbootapp.errors.RegistrationStatus;
import com.springboot.springbootapp.repository.UserRepository;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;


    @Autowired
    private BCryptPasswordEncoder passwordencoder;

    public void register(User user) {
        user.setAccTimestamp(Timestamp.from(Instant.now()));
        user.setPassword(passwordencoder.encode(user.getPassword()));
        repository.save(user);
    }

    public Boolean isEmailPresent(String emailId) {
        return repository.isEmailPresent(emailId) > 0 ? true : false;
    }

    public User getUser(String email) {
        return repository.findByEmailId(email);


    }

    public RegistrationStatus getRegistrationStatus(BindingResult errors) {
        FieldError emailIdError = errors.getFieldError("emailId");
        FieldError passwordError = errors.getFieldError("password");
        String emailIdErrorMessage = emailIdError == null ? "-" : emailIdError.getCode();
        String passwordErrorMessage = passwordError == null ? "-" : passwordError.getCode();
        return new RegistrationStatus(emailIdErrorMessage, passwordErrorMessage);
    }

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        UserDetails result;
        User user = repository.findByEmailId(emailId);
        if (user == null) throw new UsernameNotFoundException("User with given emailId does not exist");
        else {
            result = new CustomUserInfo(user);
        }
        return result;
    }

    public User updateUser(User user) {
        User existingUser = repository.findById(user.getId()).orElse(null);
        existingUser.setFname(user.getFname());
        if (pwdvalidate(user.getPassword())) {
            existingUser.setPassword(passwordencoder.encode(user.getPassword()));
        }

        existingUser.setAccUpdateTimestamp(Timestamp.from(Instant.now()));

        return repository.save(existingUser);
    }

    public Boolean pwdvalidate(String password) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(9, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()));
        RuleResult result = validator.validate(new PasswordData(password));
        if (!result.isValid()) {
            return true;
        }
        return false;

    }
}