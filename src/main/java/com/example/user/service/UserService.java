package com.example.user.service;

import java.util.List;
import java.util.Optional;

import com.example.user.entity.User;

public interface UserService {
	List<User> getAllUser();
	
	Optional<User> getUserById(long userId);

}
