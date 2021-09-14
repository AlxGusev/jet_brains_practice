package com.example.recipes.recipe;

import com.example.recipes.user.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

import static com.example.recipes.utils.Utils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

        recipeService.findRecipeById(ID);

        verify(recipeRepository).findById(ID);
    }

    @Test
    public void givenRecipeAndEmail_whenSaveRecipe_thenSuccess() {

        Recipe defaultRecipe = getRecipe();
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(getUser()));

        recipeService.saveRecipe(defaultRecipe, EMAIL);
        defaultRecipe.setUser(getUser());

        verify(recipeRepository).save(defaultRecipe);
    }

    @Test
    public void givenRecipeAndEmail_whenSaveRecipe_thenNotFound() {

        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        recipeService.saveRecipe(getRecipe(), EMAIL);

        verifyNoInteractions(recipeRepository);
    }

    @Test
    public void givenIdAndRecipeAndEmail_whenUpdateRecipe_thenSuccess() {

        Recipe defaultRecipeWithUser = getRecipeWithUser();
        when(recipeRepository.findById(ID)).thenReturn(Optional.of(defaultRecipeWithUser));

        recipeService.updateRecipe(ID, getRecipe(), EMAIL);

        verify(recipeRepository).save(defaultRecipeWithUser);
    }

    @Test
    public void givenIdAndRecipeAndEmail_whenUpdateRecipe_thenNotFound() {

        when(recipeRepository.findById(ID)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> recipeService.updateRecipe(ID, getRecipe(), EMAIL));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verifyNoMoreInteractions(recipeRepository);
    }

    @Test
    public void givenIdAndRecipeAndEmail_whenUpdateRecipe_thenForbidden() {

        when(recipeRepository.findById(ID)).thenReturn(Optional.of(getRecipeWithUser()));

        recipeService.updateRecipe(ID, getRecipe(), DIFFERENT_EMAIL);

        verifyNoMoreInteractions(recipeRepository);
    }

    @Test
    public void givenIdAndEmail_whenDeleteRecipe_thenSuccess() {

        Recipe defaultRecipeWithUser = getRecipeWithUser();
        when(recipeRepository.findById(ID)).thenReturn(Optional.of(defaultRecipeWithUser));

        recipeService.deleteRecipe(ID, EMAIL);

        verify(recipeRepository).delete(defaultRecipeWithUser);
    }

    @Test
    public void givenIdAndEmail_whenDeleteRecipe_thenNotFound() {

        when(recipeRepository.findById(ID)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> recipeService.deleteRecipe(ID, EMAIL));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verifyNoMoreInteractions(recipeRepository);
    }

    @Test
    public void givenIdAndEmail_whenDeleteRecipe_thenForbidden() {

        when(recipeRepository.findById(ID)).thenReturn(Optional.of(getRecipeWithUser()));

        recipeService.deleteRecipe(ID, DIFFERENT_EMAIL);

        verify(recipeRepository).findById(ID);
        verifyNoMoreInteractions(recipeRepository);
    }

    @Test
    public void givenParameterCategory_whenFindAllByParameter_thenSuccess() {

        Map<String, String> categoryParam = CATEGORY_PARAM;

        recipeService.findAllByParameter(categoryParam);

        verify(recipeRepository).findByCategoryIgnoreCaseOrderByDateDesc(categoryParam.get("category"));
    }

    @Test
    public void givenParameterName_whenFindAllByParameter_thenSuccess() {

        Map<String, String> nameParam = NAME_PARAM;

        recipeService.findAllByParameter(nameParam);

        verify(recipeRepository).findByNameIgnoreCaseContainingOrderByDateDesc(nameParam.get("name"));
    }

    @Test
    public void givenParameter_whenFindAllByParameter_thenNotFound() {

        recipeService.findAllByParameter(WRONG_PARAM);

        verifyNoInteractions(recipeRepository);
    }






}