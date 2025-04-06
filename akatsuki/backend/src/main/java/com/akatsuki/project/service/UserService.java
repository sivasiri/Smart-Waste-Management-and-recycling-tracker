package com.akatsuki.project.service;

import com.akatsuki.project.dto.UserUpdateRequest;
import com.akatsuki.project.model.User;
import com.akatsuki.project.repository.UserRepository;
import com.akatsuki.project.util.JwtUtil;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {
	
	@Autowired
	private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    public String registerUser(String firstName, String lastName, String email, String password, String phone,
            String address, int age, String sex, MultipartFile profilePicture) {
if (userRepository.findByEmail(email) != null) {
return "User already exists!";
}

User user = new User();
user.setFirstName(firstName);
user.setLastName(lastName);
user.setEmail(email);
user.setPassword(password);
user.setPhone(phone);
user.setAddress(address);
user.setAge(age);
user.setSex(sex);

try {
if (profilePicture != null && !profilePicture.isEmpty()) {
user.setProfilePicture(profilePicture.getBytes());
}
} catch (IOException e) {
return "Failed to read profile picture.";
}

userRepository.save(user);
return "User registration successful!";
}


    // Find user by email (used in login, etc.)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public String loginUser(String email, String password) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            return "User not found!";
        }

        if (!existingUser.getPassword().equals(password)) {
            return "Invalid credentials!";
        }

        String token = jwtUtil.generateToken(email);
        return "Login successful! Token: " + token;
    }
    
    public String updateUserProfile(String email, UserUpdateRequest updateRequest) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "User not found!";
        }

        user.setFirstName(updateRequest.getFirstName());
        user.setLastName(updateRequest.getLastName());
        user.setPhone(updateRequest.getPhone());
        user.setAddress(updateRequest.getAddress());
        user.setAge(updateRequest.getAge());
        user.setSex(updateRequest.getSex());
        user.setProfilePicture(updateRequest.getProfilePicture());


        userRepository.save(user);
        return "Profile updated successfully!";
    }

    
    public void updateProfilePicture(String email, byte[] profilePicBytes) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setProfilePicture(profilePicBytes);
            userRepository.save(user);
        }
    }
    
    public String loginUserAndGenerateToken(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return jwtUtil.generateToken(email);
        }
        return null;
    }




    

}
