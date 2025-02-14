package com.spring.online.demo.DAO;


import com.spring.online.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
   
    User findByEmail(String email);

    User getUserByUsername(String username);


}
