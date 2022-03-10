//package com.springboot.springbootapp;
//
//import com.springboot.springbootapp.controller.UserController;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.junit.Before;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SpringBootFirstWebApplicationTests {
//    private MockMvc mockMvc;
//    @InjectMocks
//    private UserController userRestControllerTest;
//
//    @Before
//    public void setUp() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(userRestControllerTest).build();
//    }
//
//    @Test
//    public void welcomeTest() throws Exception{
//        mockMvc.perform(MockMvcRequestBuilders.get("/healthz"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//}
