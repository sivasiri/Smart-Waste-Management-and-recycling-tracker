package com.akatsuki.project.service;

import com.akatsuki.project.Model.*;
import com.akatsuki.project.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

@Autowired
private UserRepository userRepository;

public User registerUser(User user) {
return ((Object) userRepository).save(user);
}

public User loginUser(String email, String password) {
User existingUser = userRepository.findByEmail(email);
if (existingUser != null && existingUser.getPassword().equals(password)) {
return existingUser;
}
return null;
}
}}
