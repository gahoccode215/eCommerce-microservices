package com.app.ecom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/api/users")
    public List<User> getAllUsers(){
        return userService.fetchAllUsers();
    }

    @PostMapping("/api/users")
    public List<User> createUser(@RequestBody User user){
        return userService.addUser(user);

    }

    @GetMapping("/api/users/{id}")
    public User getUser(@PathVariable int id){
        return userService.fetchUser(id);
    }
}
