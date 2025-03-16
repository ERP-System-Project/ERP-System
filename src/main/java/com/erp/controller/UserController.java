package com.erp.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.UserDTO;
import com.erp.model.Role;
import com.erp.model.User;
import com.erp.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        User createdUser = userService.register(user);
        UserDTO createdUserDTO = convertToDTO(createdUser);
        return ResponseEntity.ok(createdUserDTO);
    }

    @GetMapping 
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                                      .map(this::convertToDTO)
                                      .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/email")
    public ResponseEntity<UserDTO> getUserByEmail(String email) {
        User user = userService.getUserByEmail(email);
        UserDTO userDTO = convertToDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/{userId}/roles")
    public ResponseEntity<UserDTO> assignRolesToUser(@PathVariable Long userId, @RequestBody Set<Role> roles) {
        User user = userService.assignRolesToUser(userId, roles);
        UserDTO userDTO = convertToDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return userService.verify(user);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
