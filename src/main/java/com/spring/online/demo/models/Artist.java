//package com.spring.online.demo.models;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Artist {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//
//    private String username;
//    private String password;
//    private String email;
//
//    @Column(unique = true)
//    private String PenName;
//
//    public String getPenName() {
//        return PenName;
//    }
//
//    public void setPenName(String penName) {
//        PenName = penName;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//}
