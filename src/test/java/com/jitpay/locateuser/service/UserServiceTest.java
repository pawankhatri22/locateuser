package com.jitpay.locateuser.service;

import com.jitpay.locateuser.dto.UserDto;
import com.jitpay.locateuser.entity.Location;
import com.jitpay.locateuser.entity.User;
import com.jitpay.locateuser.exception.UserNotFoundException;
import com.jitpay.locateuser.repository.LocationRepository;
import com.jitpay.locateuser.repository.UserRepository;
import com.jitpay.locateuser.util.Utility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void saveUser(){

        UserDto userDto = getUserDto();
        User user = getUser();
        given(userService.saveUser(userDto)).willReturn(user);
        // when -  action or the behaviour that we are going test
        User savedEmployee = userService.saveUser(userDto);
        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    public void updateUserException(){
        // given - precondition or setup
        UserDto userDto = getUserDto();
        User user = getUser();
//        given(userRepository.findById(userDto.getUserId()))
//                .willReturn(Optional.of(user));

        Map<String, Object> map = new HashMap<>();
        map.put("firstName", "Test1");

        // when -  action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(userDto.getUserId(), map);
        });

        // then
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void updateUser(){
        // given - precondition or setup
        UserDto userDto = getUserDto();
        User user = getUser();
        given(userRepository.findById(userDto.getUserId()))
                .willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);

        Map<String, Object> map = new HashMap<>();
        map.put("firstName", "Test1");

        // when -  action or the behaviour that we are going test
        User updateUser = userService.updateUser(userDto.getUserId(), map);

        assertEquals(updateUser.getFirstName(), user.getFirstName() );
    }

    @Test
    public void fetchUserWithLatestLocation(){
        // given - precondition or setup
        UserDto userDto = getUserDto();
        User user = getUser();
        given(userRepository.findById(userDto.getUserId()))
                .willReturn(Optional.of(user));
        Location location = new Location(1l, LocalDateTime.now(), "22.1","11123",user);
        given(locationRepository.findTop1ByUserOrderByCreatedOnDesc(user)).willReturn(location);

        // when -  action or the behaviour that we are going test
        Map<String, Object> updateUser = userService.fetchUserByIdWithLocation(userDto.getUserId());

        assertEquals(updateUser.get(Utility.USER_ID), user.getUserId());

    }


    @Test
    public void fetchUserWithLatestLocationBetween(){
        // given - precondition or setup
        UserDto userDto = getUserDto();
        User user = getUser();
        given(userRepository.findById(userDto.getUserId()))
                .willReturn(Optional.of(user));
        Location location = new Location(1l, LocalDateTime.now(), "22.1","11123",user);
        given(locationRepository.findByUserAndCreatedOnBetween(user,LocalDateTime.now(),LocalDateTime.now())).willReturn(Arrays.asList(location));

        // when -  action or the behaviour that we are going test
        Map<String, Object> updateUser = userService.fetchUserByIdWithLocationBetween(userDto.getUserId(), LocalDateTime.now(), LocalDateTime.now());

        assertEquals(updateUser.get(Utility.USER_ID), user.getUserId());

    }





    private UserDto getUserDto() {
        UserDto user = new UserDto("2e3b11b0-07a4-4873-8de5-d2ae2eab26b1", LocalDateTime.now(), "alex.schmid@gmail.com","Asd", "Schmid");
        return user;
    }

    private User getUser() {
        User user = new User("2e3b11b0-07a4-4873-8de5-d2ae2eab26b1", LocalDateTime.now(), "alex.schmid@gmail.com","Asd", "Schmid");
        return user;
    }
}
