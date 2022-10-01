package com.jitpay.locateuser.repository;

import com.jitpay.locateuser.LocateUserApplication;
import com.jitpay.locateuser.entity.Location;
import com.jitpay.locateuser.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LocateUserApplication.class)
class LocationRepositoryTest {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    UserRepository userRepository;
    @Test
    void findTop1ByUserOrderByCreatedOnDesc() {
        User user = getUser();
        userRepository.save(user);
        Location location = new Location("23.111", "41.111",LocalDateTime.now(),user);
        locationRepository.save(location);
        Location locationFetch = locationRepository.findTop1ByUserOrderByCreatedOnDesc(user);
        assertEquals(location.getId(), locationFetch.getId());


    }



    private User getUser() {
        User user = new User("2e3b11b0-07a4-4873-8de5-d2ae2eab26b1", LocalDateTime.now(), "alex.schmid@gmail.com","Asd", "Schmid");
        return user;
    }
}