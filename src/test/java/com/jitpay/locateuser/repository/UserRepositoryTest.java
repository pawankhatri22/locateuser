package com.jitpay.locateuser.repository;

import com.jitpay.locateuser.LocateUserApplication;
import com.jitpay.locateuser.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LocateUserApplication.class)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testFindById() {
        User user = getUser();
        userRepository.save(user);
        User result = userRepository.findById(user.getUserId()).get();
        assertEquals(user.getUserId(), result.getUserId());
    }
    @Test
    public void testFindAll() {
        User user = getUser();
        userRepository.save(user);
        List<User> result = new ArrayList<>();
        userRepository.findAll().forEach(e -> result.add(e));
        assertEquals(result.size(), 1);
    }



    @Test
    public void testSave() {
        User user = getUser();
        userRepository.save(user);
        User found = userRepository.findById(user.getUserId()).get();
        assertEquals(user.getUserId(), found.getUserId());
    }
    @Test
    public void testDeleteById() {
        User user = getUser();
        userRepository.save(user);
        userRepository.deleteById(user.getUserId());
        List<User> result = new ArrayList<>();
        userRepository.findAll().forEach(e -> result.add(e));
        assertEquals(result.size(), 0);
    }
//
    private User getUser() {
        User user = new User("2e3b11b0-07a4-4873-8de5-d2ae2eab26b1", LocalDateTime.now(), "alex.schmid@gmail.com","Asd", "Schmid");
        return user;
    }

}