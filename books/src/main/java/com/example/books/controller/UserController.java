package com.example.books.controller;


import com.example.books.entities.UserEntity;
import com.example.books.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/register-user", method = RequestMethod.POST)
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user){
        System.out.println("Register user");
        UserEntity registeredUser = userService.registerUser(user);
        return ResponseEntity.status(200).body(registeredUser);
    }

}
