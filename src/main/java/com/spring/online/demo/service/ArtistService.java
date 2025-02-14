//package com.spring.online.demo.service;
//
//
//import com.spring.online.demo.DAO.ArtistDao;
//import com.spring.online.demo.models.Artist;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.util.Optional;
//
//@Service
//public class ArtistService {
//
//    @Autowired
//    ArtistDao artistDao;
//
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    JavaMailSender mailSender;
//
//    public ArtistService() throws NoSuchAlgorithmException {
//        // Generate RSA key pair once and store it
//        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
//        keyGen.initialize(2048); // Set key size
//        KeyPair keyPair = keyGen.generateKeyPair();
//    }
//
//    public Object addUser(String email, String password, String username, String penName) {
//
//        Optional<Artist> existingArtist = Optional.ofNullable(artistDao.findByPenName(penName));
//        if (existingArtist.isPresent()) {
//            return "PenName Already Exists";
//        }
//
//        Artist artist = new Artist();
//        String hashedPassword = bCryptPasswordEncoder.encode(password);
//        artist.setPassword(hashedPassword);
//        artist.setUsername(username);
//        artist.setPenName(penName);
//        artist.setEmail(email);
//        artistDao.save(artist);
//
//        sendMain(email);
//        return artist;
//
//    }
//
//    public Object getAllArtists() {
//        return artistDao.findAll();
//    }
//
//    public Object getArtistByPenName(String PenName) {
//
//        return artistDao.getArtistByPenName(PenName);
//
//    }
//
//    public Object validateArtist(String email, String password, String username) {
//
//        Optional<Artist> artist = Optional.ofNullable(artistDao.getArtistByUsername(username));
//
//    }
//
//
//}
