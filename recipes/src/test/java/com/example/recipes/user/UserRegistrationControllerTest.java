package com.example.recipes.user;

import com.example.recipes.RecipesApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static com.example.recipes.utils.Utils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RecipesApplication.class})
@WebAppConfiguration
class UserRegistrationControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;
    private UserRegistrationController controller;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        controller = new UserRegistrationController(userService);
    }

    @Test
    public void givenWAC_whenServletContext_thenItProvidesUserRegistrationController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("userRegistrationController"));
    }

    @Test
    public void givenRegisterPage_whenMockMVC_thenVerifyResponse() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/register")
                .contentType("application/json;charset=UTF-8")
                .content(mapper.writeValueAsString(getDefaultUserDto())))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void givenUserDto_whenRegister_thenSuccess() {
        when(userService.registerNewUser(getDefaultUser())).thenReturn(true);
        ResponseEntity<Object> responseEntity = controller.register(getDefaultUserDto());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService, atMostOnce()).registerNewUser(getDefaultUser());
    }

    @Test
    public void givenUserDto_whenRegister_thenBadRequest() {
        when(userService.registerNewUser(getDefaultUser())).thenReturn(false);
        ResponseEntity<Object> responseEntity = controller.register(getDefaultUserDto());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        verify(userService, atMostOnce()).registerNewUser(getDefaultDifferentUser());
    }

}