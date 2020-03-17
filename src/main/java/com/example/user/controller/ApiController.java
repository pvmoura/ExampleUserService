package com.example.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.example.user.entity.User;
import com.example.user.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class ApiController {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "Retrieves all rows from the hypothetical user table.")
	@GetMapping(path = "/user", produces = "application/json")
	public List<User> getAllUsers() {
		return userService.getAllUser();
	}
	
    @ApiOperation(value = "Retrieves the user given a specific user id.")
    @ApiImplicitParam(name = "userId", value = "a long identifier for the userId of the user.")
    @ApiResponses({
    	@ApiResponse(code = 404, message = "User Id not found in table")
    })
	@GetMapping(path = "/user/{userId}", produces = "application/json")
	public User getUserById(@PathVariable("userId") long userId) {
		return userService.getUserById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Id not found in table"));
	}
      
}
