package com.wiktormalyska.backend.services;

import com.wiktormalyska.backend.dao.IUserRepository;
import com.wiktormalyska.backend.dto.UserDto;
import com.wiktormalyska.backend.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public Collection<UserDto> getUsers(){
        Collection<UserDto> userDtos= new ArrayList<>();
        Collection<User> users = userRepository.findAll();
        for (User user : users) {
            UserDto userDto = new UserDto(user);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Transactional
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null)
            return new UserDto(user);
        else
            return null;
    }

    @Transactional
    public UserDto getUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null)
            return new UserDto(user);
        else
            return null;
    }

    @Transactional
    public String addUser(User user){
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        User checkUser = userRepository.findByUsername(user.getUsername());
        if (checkUser != null)
            return "user already exists";
        userRepository.save(user);
        return "user added successfully";
    }

    @Transactional
    public String removeUser(User user){
        User checkUser = userRepository.findByUsername(user.getUsername());
        if (checkUser == null)
            return "user does not exist";
        userRepository.deleteById(user.getId());
        return "user removed successfully";
    }
}
