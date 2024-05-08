package com.wiktormalyska.backend.controllers;

import com.wiktormalyska.backend.dto.CreateUserDto;
import com.wiktormalyska.backend.dto.UserDto;
import com.wiktormalyska.backend.model.User;
import com.wiktormalyska.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Collection<UserDto>> getUsers() {
        Collection<UserDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody CreateUserDto user) {
        User newUser = new User(
                user.getUsername(),
                user.getPassword()
        );
        switch (userService.addUser(newUser)) {
            case "user already exists" -> {
                return ResponseEntity.badRequest().body("User already exists");
            }
            case "user added successfully" -> {
                return ResponseEntity.ok("User added successfully");
            }
        }
        return ResponseEntity.status(500).body("An unexpected error occurred");
    }

    @PostMapping("/get/{user}")
    public ResponseEntity<UserDto> getUser(@PathVariable int user) {
        UserDto userdto = userService.getUser(user);
        if (userdto != null) {
            return ResponseEntity.ok(userdto);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/remove/{user}")
    public ResponseEntity<String> removeUser(@PathVariable int user) {
        UserDto userToRemove = userService.getUser(user);
        if (userToRemove== null) return ResponseEntity.badRequest().body("user does not exist");
        userService.removeUser(userToRemove.getUser());
        return ResponseEntity.ok("user removed successfully");
    }
}
