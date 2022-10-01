package com.jitpay.locateuser.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jitpay.locateuser.dto.UserDto;
import com.jitpay.locateuser.entity.User;
import com.jitpay.locateuser.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({UserController.class})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;


    @Test
    public void saveUser() throws Exception {

        User user = getUser();
        UserDto userDto = getUserDto();

        when(userService.saveUser(userDto)).thenReturn(user);


        mockMvc.perform(
                        MockMvcRequestBuilders.post("/user")
                                .content(asJsonString(userDto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());

    }


    @Test
    public void updateUser() throws Exception {

        User user = getUser();
        UserDto userDto = getUserDto();

        when(userService.saveUser(userDto)).thenReturn(user);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/user")
                                .content(asJsonString(userDto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());


        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/user")
                                .param("userId", userDto.getUserId())
                                .content("{\n" +
                                        "   \"email\":\"asd@hail.com\"\n" +
                                        "}")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }


    private UserDto getUserDto() {
        UserDto user = new UserDto("2e3b11b0-07a4-4873-8de5-d2ae2eab26b1", LocalDateTime.now(), "alex.schmid@gmail.com","Asd", "Schmid");
        return user;
    }

    private User getUser() {
        User user = new User("2e3b11b0-07a4-4873-8de5-d2ae2eab26b1", LocalDateTime.now(), "alex.schmid@gmail.com","Asd", "Schmid");
        return user;
    }





    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}