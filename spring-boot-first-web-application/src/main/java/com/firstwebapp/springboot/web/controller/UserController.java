package com.firstwebapp.springboot.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firstwebapp.springboot.web.DTO.User;

// /login => "Hello World" ....
//instead of showing error we show hello world when user types localhost 8080
// now spring should pick up this class in order to display

@RestController
@RequestMapping("/users")
public class UserController {
	
	@SuppressWarnings("rawtypes")
	@GetMapping
	public ResponseEntity<User> getUsers()
	{
		User user=new User();
		user.setName("Shrre");
		user.setAge(25);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody User user)
	{
		user.setName(user.getName());
		user.setAge(user.getAge());
		System.out.println("User : "+user.getName()+ "age"+  user.getAge());
		return ResponseEntity.status(HttpStatus.OK).body("Succesfully created user"+user);
	}
	
	

}
