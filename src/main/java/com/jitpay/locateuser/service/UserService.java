package com.jitpay.locateuser.service;

import com.jitpay.locateuser.dto.UserDto;
import com.jitpay.locateuser.dto.UserLocationDto;
import com.jitpay.locateuser.entity.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public interface UserService {
    public String saveUserLocation(UserLocationDto userDto);
    public User saveUser(UserDto userDto);
    public User updateUser(String userId, Map<String, Object> userDto);
    public Map<String, Object> fetchUserByIdWithLocation(String userId);
    public Map<String, Object> fetchUserByIdWithLocationBetween(String userId, LocalDateTime from , LocalDateTime to);
}
