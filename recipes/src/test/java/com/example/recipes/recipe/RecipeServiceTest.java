package com.example.recipes.recipe;

import com.example.recipes.user.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

import static com.example.recipes.utils.Utils.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private IRecipeRepository recipeRepository;
    @Mock
    private IUserRepository userRepository;

    private RecipeService recipeService;

    @BeforeEach
    public void setup() {
        recipeService = new RecipeService(recipeRepository, userRepository);
    }

    @Test
    public void givenRecipeId_whenFindRecipeById_thenSuccess() {
        recipeService.findRecipeById(1L);
        verify(recipeRepository).findById(1L);
    }

    @Test
    public void givenRecipeAndEmail_whenSaveRecipe_thenSuccess() {
        when(userRepository.findByEmail(getDefaultEmail())).thenReturn(Optional.of(getDefaultUser()));
        recipeService.saveRecipe(getDefaultRecipe(), getDefaultEmail());
        verify(recipeRepository).save(getDefaultRecipeWithUser());
    }

    @Test
    public void givenRecipeAndEmail_whenSaveRecipe_thenNotFound() {
        when(userRepository.findByEmail(getDefaultEmail())).thenReturn(Optional.empty());
        recipeService.saveRecipe(getDefaultRecipe(), getDefaultEmail());
        verifyNoInteractions(recipeRepository);
    }

    @Test
    public void givenIdAndRecipeAndEmail_whenUpdateRecipe_thenSuccess() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(getDefaultRecipeWithUser()));
        recipeService.updateRecipe(1L, getDefaultRecipe(), getDefaultEmail());
        verify(recipeRepository).save(getDefaultRecipeWithUser());
    }

    @Test
    public void givenIdAndRecipeAndEmail_whenUpdateRecipe_thenNotFound() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> recipeService.updateRecipe(1L, getDefaultRecipe(), getDefaultEmail()));
    }

    @Test
    public void givenIdAndRecipeAndEmail_whenUpdateRecipe_thenForbidden() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(getDefaultRecipeWithUser()));
        recipeService.updateRecipe(1L, getDefaultRecipe(), getDefaultDifferentEmail());
        verifyNoMoreInteractions(recipeRepository);
    }

    @Test
    public void givenIdAndEmail_whenDeleteRecipe_thenSuccess() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(getDefaultRecipeWithUser()));
        recipeService.deleteRecipe(1L, getDefaultEmail());
        verify(recipeRepository).delete(getDefaultRecipeWithUser());
    }

    @Test
    public void givenIdAndEmail_whenDeleteRecipe_thenNotFound() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> recipeService.deleteRecipe(1L, getDefaultEmail()));
    }

    @Test
    public void givenIdAndEmail_whenDeleteRecipe_thenForbidden() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(getDefaultRecipeWithUser()));
        recipeService.deleteRecipe(1L, getDefaultDifferentEmail());
        verify(recipeRepository).findById(1L);
        verifyNoMoreInteractions(recipeRepository);
    }

    @Test
    public void givenParameterCategory_whenFindAllByParameter_thenSuccess() {
        Map<String, String> categoryParam = getCategoryParam();
        recipeService.findAllByParameter(categoryParam);
        verify(recipeRepository).findByCategoryIgnoreCaseOrderByDateDesc(categoryParam.get("category"));
    }

    @Test
    public void givenParameterName_whenFindAllByParameter_thenSuccess() {
        Map<String, String> nameParam = getNameParam();
        recipeService.findAllByParameter(nameParam);
        verify(recipeRepository).findByNameIgnoreCaseContainingOrderByDateDesc(nameParam.get("name"));
    }

    @Test
    public void givenParameter_whenFindAllByParameter_thenNotFound() {
        recipeService.findAllByParameter(getWrongParam());
        verifyNoInteractions(recipeRepository);
    }






}