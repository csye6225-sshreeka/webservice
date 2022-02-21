package com.springboot.springbootapp.restControllers;

import com.springboot.springbootapp.controller.UserController;
import com.springboot.springbootapp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class UserRestControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userRestControllerTest;

    @Mock
    private UserService userServiceTest;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userRestControllerTest).build();
    }

    @Test
    public void registerUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user/")
                        .accept("application/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fname\": \"Shreekar\", \"lname\": \"rr\", \"emailId\": \"shreekar@gmail.com\", \"password\": \"Shreekar123$$$\"}"))
                .andExpect(status().isCreated());
    }
    @Test
    public void getUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/self").header("Authorization", "Basic c2hyZWVrYXJAZ21haWwuY29tOlNocmVla2FyMTIzJCQk"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void updateUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/user/").header("Authorization", "Basic "+ "c2hyZWVrYXJAZ21haWwuY29tOlNocmVla2FyMTIzJCQk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("application/json")
                .content("{\"fname\": \"Shreekar\", \"lname\": \"Shast\",\"password\": \"Shreekar123$$$\"}"))
                .andExpect(status().isAccepted());

    }


    @Test
    public void welcomeTest() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/healthz"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
