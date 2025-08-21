package com.ecommerce.user.controller;

import com.ecommerce.user.service.UserService;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody UserRequest user){
         userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<Optional<UserResponse>> getUser(@PathVariable String  id){
        return ResponseEntity.ok( userService.fetchUser(id));
    }
    public ResponseEntity<String> updateUser(@PathVariable String id,
                                             @RequestBody UserRequest updateUserRequest){
        boolean updated = userService.updateUser(id, updateUserRequest);
        if (updated)
            return ResponseEntity.ok("User updated successfully");
        return ResponseEntity.notFound().build();
    }
}
