package com.mydata.fastapi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserController {
	private static final String USERS_FILE_PATH = "users.json";
	private List<User> users = new ArrayList<>();
	
	public UserController() {
		loadUsersFromFile();
	}
	
	@GetMapping("/")
	public String hello() {
		return "Hello, World";
	}
	
	@GetMapping("/users")
	public List<User> getUsers(){
		return users;
	}
	
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		 if (users.stream().anyMatch(u -> u.getId().equals(user.getId()))) {
	            throw new IllegalArgumentException("User with this ID already exists");
	        }
		users.add(user);
		saveUsersToFile();
		return user;
	}
	
	private void loadUsersFromFile() {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		try {
			File file = new File(USERS_FILE_PATH);
			if(file.exists()) {
				User[] userArray = mapper.readValue(file, User[].class);
				for(User user : userArray) {
					users.add(user);
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveUsersToFile() {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(USERS_FILE_PATH), users);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
