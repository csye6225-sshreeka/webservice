package com.springboot.springbootapp.restControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.springbootapp.controller.UserController;
import com.springboot.springbootapp.entity.User;
import com.springboot.springbootapp.repository.UserRepository;
import com.springboot.springbootapp.service.UserService;
import com.springboot.springbootapp.validators.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class UserRestControllerTest {
    private MockMvc mockMvc;
//    @Autowired
//    private WebApplicationContext context;

    @InjectMocks
    private UserController userRestControllerTest;

    @Mock
    private UserService userServiceTest;

//    @Mock
//    private UserValidator userValidator;

//    @Autowired
//    private UserRepository repository;



    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userRestControllerTest).build();
    }

    @Test
    public void getUser() throws Exception {

        mockMvc.perform(get("/v1/user/shreekar@gmail.com").header("Authorization", "Basic "+ "c2hyZWVrYXJAZ21haWwuY29tOlNocmVla2FyMTIzJCQk")
                        .accept("application/json"))
                .andExpect(status().isAccepted());
    }
    @Test
    public void registerUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user/add")
                        .accept("application/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fname\": \"Shreekar\", \"lname\": \"rr\", \"emailId\": \"shreekar@gmail.com\", \"password\": \"Shreekar123$$$\"}"))
                .andExpect(status().isCreated());


    }
}

