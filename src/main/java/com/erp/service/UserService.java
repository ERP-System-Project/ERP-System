package com.erp.service;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import com.erp.model.Role;
import com.erp.model.User;
import com.erp.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtService jwtService;

    public User register(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User assignRolesToUser(Long userId, Set<Role> roles) {
        User user = userRepository.findById(userId).get();
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public ResponseEntity<?> verify(User user) {
        try {
                Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword())
                );

                if (authentication.isAuthenticated()) {
                    String token = jwtService.generateToken(user.getName());
                    return ResponseEntity.ok().body(Map.of("token", token));
                } else {
                    return ResponseEntity.status(401).body(Map.of("error", "Authentication failed")); 
                }
            } catch (Exception e) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials")); 
        }
    }

}
