package com.wiktormalyska.backend.controllers;
import com.wiktormalyska.backend.dto.CreateUserDto;
import com.wiktormalyska.backend.dto.UserDto;
import com.wiktormalyska.backend.model.User;
import com.wiktormalyska.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all")
    public ResponseEntity<Collection<UserDto>> getUsers() {
        Collection<UserDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody CreateUserDto userDto) {
        User newUser = new User(
                userDto.getEmail(),
                userDto.getUsername(),
                userDto.getPassword()
        );
        String result = userService.addUser(newUser);

        if ("user already exists".equals(result)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        } else if ("user added successfully".equals(result)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        UserDto userdto = userService.getUser(userId);
        if (userdto != null) {
            return ResponseEntity.ok(userdto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable Long userId) {
        UserDto userToRemove = userService.getUser(userId);
        if (userToRemove == null) return ResponseEntity.badRequest().body("user does not exist");
        return ResponseEntity.ok(userService.removeUser(userToRemove.getUser()));
    }

}
