package com.erp.service;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.model.Role;
import com.erp.model.User;
import com.erp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User createUser(User user){
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
}
