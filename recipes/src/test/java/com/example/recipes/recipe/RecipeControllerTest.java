package com.example.recipes.recipe;

import com.example.recipes.user.security.RecipeUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.recipes.utils.RecipeUtils.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private RecipeUserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = EMAIL, password = PASSWORD)
    public void givenId_whenRequestGetRecipeById_then200OkAndCorrectBody() throws Exception {

        when(recipeService.findRecipeById(anyLong())).thenReturn(Optional.of(getRecipe()));

        mockMvc.perform(
                get(BASE_URL_ID, anyLong()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(getRecipe().getName())));
    }

    @Test
    @WithMockUser(username = EMAIL, password = PASSWORD)
    public void givenId_whenGetRecipeById_then404NotFound() throws Exception {

        when(recipeService.findRecipeById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(
                get(BASE_URL_ID, anyLong()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenId_whenRequestGetRecipeById_then401Unauthorized() throws Exception {

        mockMvc.perform(
                get(BASE_URL_ID, ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = EMAIL, password = PASSWORD)
    public void givenRecipeDtoAndId_whenSaveRecipe_then200OkAndCorrectBody() throws Exception {

        when(recipeService.saveRecipe(any(Recipe.class), anyString())).thenReturn(getRecipeWithUser());

        mockMvc.perform(
                post(BASE_URL_NEW)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getRecipe())))
                .andDo(print())
                .andExpect(jsonPath("$.id", equalTo(ID.intValue())));
    }

    @Test
    @WithMockUser(username = EMAIL, password = PASSWORD)
    public void givenRecipeDtoAndId_whenSaveRecipe_then404NotFound() throws Exception {

        when(recipeService.saveRecipe(any(Recipe.class), anyString())).thenReturn(getRecipe());

        mockMvc.perform(
                post(BASE_URL_NEW)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getRecipe())))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenRecipeDtoAndId_whenSaveRecipe_then401Unauthorized() throws Exception {

        mockMvc.perform(
                post(BASE_URL_NEW)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getRecipe())))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = EMAIL, password = PASSWORD)
    public void givenIdAndRecipeDto_whenUpdateRecipe_then200OkAndCorrectBody() throws Exception {

        when(recipeService.updateRecipe(anyLong(), any(Recipe.class), anyString())).thenReturn(getRecipeWithUser());

        mockMvc.perform(
                put(BASE_URL_ID, ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getRecipeWithUser())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(getRecipeWithUser().getName())));
    }

    @Test
    @WithMockUser(username = EMAIL, password = PASSWORD)
    public void givenIdAndRecipeDto_whenUpdateRecipe_then403Forbidden() throws Exception {

        when(recipeService.updateRecipe(anyLong(), any(Recipe.class), anyString())).thenReturn(getRecipe());

        mockMvc.perform(
                put(BASE_URL_ID, ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getRecipe())))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenIdAndRecipeDto_whenUpdateRecipe_then401Unauthorized() throws Exception {

        mockMvc.perform(
                put(BASE_URL_ID, ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getRecipe())))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = EMAIL, password = PASSWORD)
    public void givenId_whenDeleteRecipe_then204NoContent() throws Exception {

        when(recipeService.deleteRecipe(ID, EMAIL)).thenReturn(true);

        mockMvc.perform(
                delete(BASE_URL_ID, ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = EMAIL, password = PASSWORD)
    public void givenId_whenDeleteRecipe_then403Forbidden() throws Exception {

        when(recipeService.deleteRecipe(ID, EMAIL)).thenReturn(false);

        mockMvc.perform(
                delete(BASE_URL_ID, ID))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenId_whenDeleteRecipe_then401Unauthorized() throws Exception {

        mockMvc.perform(
                delete(BASE_URL_ID, ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = EMAIL, password = PASSWORD)
    public void givenParameterCategory_whenSearchRecipe_then200OkAndCorrectBody() throws Exception {

        when(recipeService.findAllByParameter(getCategoryParam())).thenReturn(getListOfRecipes());

        mockMvc.perform(
                get(BASE_URL_SEARCH)
                .param("category", "beverage"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String contentAsString = result.getResponse().getContentAsString();
                    List<Recipe> recipes = Arrays.asList(new ObjectMapper().readValue(contentAsString, Recipe[].class));
                    assertEquals(2, recipes.size());
                    recipes.forEach(recipe ->
                            assertEquals("beverage", recipe.getCategory()));
                });
    }

    @Test
    @WithMockUser(username = EMAIL, password = PASSWORD)
    public void givenParameterName_whenSearchRecipe_then200OkAndCorrectBody() throws Exception {

        when(recipeService.findAllByParameter(getNameParam())).thenReturn(getListOfRecipes());

        mockMvc.perform(
                get(BASE_URL_SEARCH)
                        .param("name", "Green Tea"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String contentAsString = result.getResponse().getContentAsString();
                    List<Recipe> recipes = Arrays.asList(new ObjectMapper().readValue(contentAsString, Recipe[].class));
                    assertEquals(2, recipes.size());
                    recipes.forEach(recipe ->
                            assertEquals("Green Tea", recipe.getName()));
                });
    }

    @Test
    @WithMockUser(username = EMAIL, password = PASSWORD)
    public void givenWrongParameter_whenSearchRecipe_then400BadRequest() throws Exception {

        when(recipeService.findAllByParameter(getWrongParam())).thenReturn(Collections.emptyList());

        mockMvc.perform(
                get(BASE_URL_SEARCH)
                        .param("test", "test"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenParameterCategory_whenSearchRecipe_then401Unauthorized() throws Exception {

        mockMvc.perform(
                get(BASE_URL_SEARCH)
                        .param("test", "test"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}