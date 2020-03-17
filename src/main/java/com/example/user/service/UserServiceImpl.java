package com.example.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.user.entity.User;
import com.example.user.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public List<User> getAllUser() {
		return userRepo.findAll();
	}

	@Override
	public Optional<User> getUserById(long userId) {
		return userRepo.findById(userId);
	}

}
