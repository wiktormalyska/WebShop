package com.wiktormalyska.backend.services;

import com.wiktormalyska.backend.dao.IUserRepository;
import com.wiktormalyska.backend.dao.hibernate.UserDAO;
import com.wiktormalyska.backend.dto.UserDto;
import com.wiktormalyska.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

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
