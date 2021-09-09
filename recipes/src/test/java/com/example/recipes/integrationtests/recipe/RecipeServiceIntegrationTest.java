package com.example.recipes.integrationtests.recipe;

import com.example.recipes.recipe.IRecipeRepository;
import com.example.recipes.recipe.Recipe;
import com.example.recipes.recipe.RecipeService;
import com.example.recipes.user.IUserRepository;
import com.example.recipes.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceIntegrationTest {

    @Mock
    private IRecipeRepository recipeRepository;
    @Mock
    private IUserRepository userRepository;

    private RecipeService recipeService;

    @BeforeEach
    void setup() {
        recipeService = new RecipeService(recipeRepository, userRepository);
    }

    @Test
    void givenRecipeId_whenFindRecipeById_thenSuccess() {
        recipeService.findRecipeById(1L);
        verify(recipeRepository).findById(1L);
    }

    @Test
    void givenRecipeAndEmail_whenSaveRecipe_thenSuccess() {
        when(userRepository.findByEmail(getDefaultEmail())).thenReturn(Optional.of(getDefaultUser()));
        recipeService.saveRecipe(getDefaultRecipe(), getDefaultEmail());
        Recipe recipe = getDefaultRecipe();
        recipe.setUser(getDefaultUser());
        verify(recipeRepository).save(recipe);
    }

    @Test
    void givenRecipeAndEmail_whenSaveRecipe_thenNotFound() {
        when(userRepository.findByEmail(getDefaultEmail())).thenReturn(Optional.empty());
        recipeService.saveRecipe(getDefaultRecipe(), getDefaultEmail());
        verifyNoInteractions(recipeRepository);
    }

    @Test
    void givenIdAndRecipeAndEmail_whenUpdateRecipe_thenSuccess() {
    }

    @Test
    void givenIdAndRecipeAndEmail_whenUpdateRecipe_thenNotFound() {
    }

    @Test
    void givenIdAndRecipeAndEmail_whenUpdateRecipe_thenForbidden() {
    }

    @Test
    void givenIdAndEmail_whenDeleteRecipe_thenSuccess() {
    }

    @Test
    void givenIdAndEmail_whenDeleteRecipe_thenNotFound() {
    }

    @Test
    void givenIdAndEmail_whenDeleteRecipe_thenForbidden() {
    }

    @Test
    void givenParameter_whenFindAllByParameter_thenSuccess() {
    }

    @Test
    void givenParameter_whenFindAllByParameter_thenNotFound() {
    }



    private Recipe getDefaultRecipe() {
        return new Recipe(
                "Green Tea",
                "beverage",
                LocalDateTime.of(2020, 1, 1, 10, 0, 0),
                "Green tea improve brain function",
                List.of("1 table spoon of green tea", "1 lemon slice"),
                List.of("Place all ingredients in a mug", "Steep for 5-10 minutes"));
    }
    private User getDefaultUser() {
        return new User("testtest", "testtest8", true, "ROLE_USER");
    }
    private String getDefaultEmail() {
        return "test@test.com";
    }

}