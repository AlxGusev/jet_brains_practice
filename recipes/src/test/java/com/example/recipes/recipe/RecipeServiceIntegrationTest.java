package com.example.recipes.recipe;

import com.example.recipes.user.IUserRepository;
import com.example.recipes.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        verify(recipeRepository).save(getDefaultRecipeWithUser());
    }

    @Test
    void givenRecipeAndEmail_whenSaveRecipe_thenNotFound() {
        when(userRepository.findByEmail(getDefaultEmail())).thenReturn(Optional.empty());
        recipeService.saveRecipe(getDefaultRecipe(), getDefaultEmail());
        verifyNoInteractions(recipeRepository);
    }

    @Test
    void givenIdAndRecipeAndEmail_whenUpdateRecipe_thenSuccess() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(getDefaultRecipeWithUser()));
        recipeService.updateRecipe(1L, getDefaultRecipe(), getDefaultEmail());
        verify(recipeRepository).save(getDefaultRecipeWithUser());
    }

    @Test
    void givenIdAndRecipeAndEmail_whenUpdateRecipe_thenNotFound() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> recipeService.updateRecipe(1L, getDefaultRecipe(), getDefaultEmail()));
    }

    @Test
    void givenIdAndRecipeAndEmail_whenUpdateRecipe_thenForbidden() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(getDefaultRecipeWithUser()));
        recipeService.updateRecipe(1L, getDefaultRecipe(), getDefaultDifferentEmail());
        verifyNoMoreInteractions(recipeRepository);
    }

    @Test
    void givenIdAndEmail_whenDeleteRecipe_thenSuccess() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(getDefaultRecipeWithUser()));
        recipeService.deleteRecipe(1L, getDefaultEmail());
        verify(recipeRepository).delete(getDefaultRecipeWithUser());
    }

    @Test
    void givenIdAndEmail_whenDeleteRecipe_thenNotFound() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> recipeService.deleteRecipe(1L, getDefaultEmail()));
    }

    @Test
    void givenIdAndEmail_whenDeleteRecipe_thenForbidden() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(getDefaultRecipeWithUser()));
        recipeService.deleteRecipe(1L, getDefaultDifferentEmail());
        verify(recipeRepository).findById(1L);
        verifyNoMoreInteractions(recipeRepository);
    }

    @Test
    void givenParameterCategory_whenFindAllByParameter_thenSuccess() {
        Map<String, String> categoryParam = getCategoryParam();
        recipeService.findAllByParameter(categoryParam);
        verify(recipeRepository).findByCategoryIgnoreCaseOrderByDateDesc(categoryParam.get("category"));
    }

    @Test
    void givenParameterName_whenFindAllByParameter_thenSuccess() {
        Map<String, String> nameParam = getNameParam();
        recipeService.findAllByParameter(nameParam);
        verify(recipeRepository).findByNameIgnoreCaseContainingOrderByDateDesc(nameParam.get("name"));
    }

    @Test
    void givenParameter_whenFindAllByParameter_thenNotFound() {
        recipeService.findAllByParameter(getWrongParam());
        verifyNoInteractions(recipeRepository);
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
        return new User("test@test.com", "testtest8", true, "ROLE_USER");
    }
    private String getDefaultEmail() {
        return "test@test.com";
    }
    private String getDefaultDifferentEmail() {
        return "testDifferent@test.com";
    }
    private Recipe getDefaultRecipeWithUser() {
        Recipe defaultRecipeWithUser = getDefaultRecipe();
        defaultRecipeWithUser.setUser(getDefaultUser());
        return defaultRecipeWithUser;
    }
    private Map<String, String> getWrongParam() {
        Map<String, String> params = new HashMap<>();
        params.put("test", "test");
        return params;
    }
    private Map<String, String> getCategoryParam() {
        Map<String, String> params = new HashMap<>();
        params.put("category", "test");
        return params;
    }
    private Map<String, String> getNameParam() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "test");
        return params;
    }

}