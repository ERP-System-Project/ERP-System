package com.erp.dto;

import java.util.Set;

import com.erp.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    private String name;
    
    private String email;

    private Set<Role> roles;
}