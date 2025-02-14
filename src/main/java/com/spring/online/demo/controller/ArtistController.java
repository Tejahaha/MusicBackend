//package com.spring.online.demo.controller;
//
//
//import com.spring.online.demo.models.Artist;
//import com.spring.online.demo.service.ArtistService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/artist")
//@CrossOrigin(origins = "http://localhost:5173")
//public class ArtistController {
//
//    @Autowired
//    ArtistService artistService;
//
//    @PostMapping("/add")
//    public String add(@RequestBody Artist artist) {
//        return artistService.addUser(
//                artist.getEmail(),
//                artist.getPassword(),
//                artist.getUsername(),
//                artist.getPenName()
//        );
//    }
//
//}
