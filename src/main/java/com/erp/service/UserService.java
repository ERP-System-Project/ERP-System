package com.erp.service;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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

    public String verify(User user) {

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getName());
        }
        return "Fail";
    }

}
