package com.example.recipes.user;

import com.example.recipes.user.security.RecipeUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.example.recipes.utils.RecipeUtils.getUserDto;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserRegistrationController.class)
class UserRegistrationControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private RecipeUserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenUserDto_whenRegister_then200OK() throws Exception {

        when(userService.registerNewUser(any())).thenReturn(true);

        getResultActions()
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void givenUserDto_whenRegister_thenBadRequest() throws Exception {

        when(userService.registerNewUser(any())).thenReturn(false);

        getResultActions()
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private ResultActions getResultActions() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getUserDto())))
                .andDo(print());
    }

}