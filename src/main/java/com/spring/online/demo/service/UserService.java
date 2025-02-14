package com.spring.online.demo.service;

import com.spring.online.demo.DAO.UserDao;
import com.spring.online.demo.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender mailSender;

    public UserService() throws NoSuchAlgorithmException {
        // Generate RSA key pair once and store it
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // Set key size
        KeyPair keyPair = keyGen.generateKeyPair();
    }


    public Object addUser(String email, String password, String username) throws MessagingException {
        // Check if user already exists
        Optional<User> existingUser = Optional.ofNullable(userDao.findByEmail(email));
        if (existingUser.isPresent()) {
            return "Email already registered";
        }

        // Create and save new user
        User user = new User();
        String hashedPassword = passwordEncoder.encode(password); // Hashing here ✅
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setEmail(email);
        userDao.save(user);

        // Send welcome email

        sendMail(email);

        return user;
    }

    public Object getAllUser() {
        return userDao.findAll();
    }

    public Object getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public Object validateUser(String username, String password) throws NoSuchAlgorithmException {
        Optional<User> user = Optional.ofNullable(userDao.getUserByUsername(username));

        if (user.isEmpty()) {
            return "User not found";
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            return "Wrong password";
        }

        User userFound = user.get();

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyGen.generateKeyPair();

        String jwtToken = Jwts.builder().setSubject(user.get().getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.RS256, keyPair.getPrivate())
                .compact();

        class JWTResponse {
            private String jwtToken;
            private User user;

            public JWTResponse(String jwtToken, User user) {
                this.jwtToken = jwtToken;
                this.user = user;
            }

            public String getJwtToken() {
                return jwtToken;
            }

            public User getUser() {
                return user;
            }

            public void setUser(User user) {
                this.user = user;
            }

            public void setJwtToken(String jwtToken) {
                this.jwtToken = jwtToken;
            }
        }

        return new JWTResponse(jwtToken, userFound);
    }



    public Object sendMail(String email) throws MessagingException {
        Optional<User> user = Optional.ofNullable(userDao.findByEmail(email));
        if (user.isEmpty()) {
            return "User not found";
        }

        String username = user.get().getUsername(); // Get the actual username

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        String htmlContent = String.format("""
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Welcome to TuneUp</title>
            <style>
                body, html { margin: 0; padding: 0; font-family: 'Poppins', sans-serif; background: linear-gradient(135deg, #1DB954 0%%, #191414 100%%); color: #ffffff; }
                .email-container { max-width: 600px; margin: 20px auto; background: rgba(255, 255, 255, 0.1); border-radius: 15px; padding: 30px; text-align: center; }
                .header-title { font-size: 36px; font-weight: 700; color: #ffffff; }
                .welcome-message { font-size: 24px; font-weight: 600; color: #1ED760; }
                .cta-button { display: inline-block; padding: 15px 30px; background: #1ED760; color: #191414; text-decoration: none; border-radius: 50px; font-weight: 700; }
                .cta-button:hover { background: #1DB954; }
                .email-footer { margin-top: 20px; font-size: 12px; color: #ccc; }
            </style>
        </head>
        <body>
            <div class="email-container">
                <h1 class="header-title">Welcome to TuneUp, %s! 🎵</h1>
                <p class="welcome-message">Your Musical Universe Awaits!</p>
                <p>Get ready to dive into a world where music meets magic. TuneUp is more than a platform—it's your personal soundtrack.</p>
                <a href="#" class="cta-button">Unleash Your Playlist</a>
                <div class="email-footer">© 2025 TuneUp. All rights reserved.</div>
            </div>
        </body>
        </html>
    """, username); // Inject username into the string

        helper.setTo(email);
        helper.setSubject("Welcome to TuneUp!");
        helper.setText(htmlContent, true);

        mailSender.send(mimeMessage);

        return "Mail Sent Successfully";
    }

}
