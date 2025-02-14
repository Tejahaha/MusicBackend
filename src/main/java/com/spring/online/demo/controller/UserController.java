package com.spring.online.demo.controller;


import com.spring.online.demo.models.User;
import com.spring.online.demo.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173") // Allow frontend requests
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/add")
    public Object addUser(@RequestBody User user) throws MessagingException {
        return userService.addUser(
                user.getEmail(),
                user.getPassword(),
                user.getUsername()
        );
    }



    @GetMapping("/get")
    public Object getUser() {
        return userService.getAllUser();
    }


    @GetMapping("/getbyUsername")
    public Object getUserByUsername(@RequestParam("username") String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/validate")
    public Object validateUser(@RequestBody User user) throws NoSuchAlgorithmException, MessagingException {
        return userService.validateUser(
                user.getUsername(),
                user.getPassword()
        );
    }



    @PostMapping("/gmail")
    public Object sendEmail(@RequestBody User user) throws NoSuchAlgorithmException, MessagingException {
        return userService.sendMail(
            user.getEmail()
        );

    }

    public static class ArtistController {



    }
}
