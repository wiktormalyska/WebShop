package com.wiktormalyska.backend.services;

import com.wiktormalyska.backend.dao.IRoleRepository;
import com.wiktormalyska.backend.dao.IUserRepository;
import com.wiktormalyska.backend.dao.hibernate.UserDAO;
import com.wiktormalyska.backend.dto.UserDto;
import com.wiktormalyska.backend.model.Role;
import com.wiktormalyska.backend.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Optional<User> findByUsername(String username) {
        return userRepository.getUser(username);
    }

    @Override
    @Transactional
    public String registerUser(User user) {
        if (userRepository.getUser(user.getUsername()).isPresent()) {
            return "failure";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("USER");
        if (userRole != null) {
            user.getRoles().add(userRole);
        }
        else {
            Role role = new Role();
            role.setName("USER");
            user.getRoles().add(role);
        }
        userRepository.addUser(user);
        return "success";
    }

    public Collection<UserDto> getUsers(){
        Collection<UserDto> userDtos = new ArrayList<>();
        Collection<User> users = userRepository.getUsers();
        for (User user : users) {
            UserDto userDto = new UserDto(user);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public UserDto getUser(int id) {
        User user = userRepository.getUser(id);
        if (user != null)
            return new UserDto(user);
        else
            return null;
    }

    public String addUser(User user){
        User checkUser = userRepository.getUsers().stream().filter(u -> u.equals(user)).findFirst().orElse(null);
        if (checkUser != null)
            return "user already exists";
        userRepository.addUser(user);
        return "user added successfully";
    }

    public String removeUser(User user){
        User checkUser = userRepository.getUsers().stream().filter(u -> u.equals(user)).findFirst().orElse(null);
        if (checkUser == null)
            return "user does not exist";
        userRepository.removeUser(user.getId());
        return "user removed successfully";
    }
}
