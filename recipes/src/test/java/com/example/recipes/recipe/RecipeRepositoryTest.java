package com.example.recipes.recipe;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.example.recipes.utils.Utils.getRecipe;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RecipeRepositoryTest {

    @Autowired
    private IRecipeRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(getRecipe());
        repository.save(getRecipe());
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void givenCategory_whenFindByCategoryIgnoreCaseOrderByDateDesc_thenSuccess() {

        List<Recipe> recipesByCategory = repository.findByCategoryIgnoreCaseOrderByDateDesc(getRecipe().getCategory());

        assertThat(recipesByCategory.size()).isEqualTo(2);
        assertThat(recipesByCategory.get(0).getDate()).isAfter(recipesByCategory.get(1).getDate());
    }

    @Test
    void givenCategory_whenFindByCategoryIgnoreCaseOrderByDateDesc_thenNotFound() {

        List<Recipe> recipesByCategory = repository.findByCategoryIgnoreCaseOrderByDateDesc("food");

        assertThat(recipesByCategory.size()).isEqualTo(0);
    }

    @Test
    void givenName_whenFindByNameIgnoreCaseContainingOrderByDateDesc_thenSuccess() {

        List<Recipe> recipeByName = repository.findByNameIgnoreCaseContainingOrderByDateDesc(getRecipe().getName());

        assertThat(recipeByName.size()).isEqualTo(2);
        assertThat(recipeByName.get(0).getDate()).isAfter(recipeByName.get(1).getDate());
    }

    @Test
    void givenName_whenFindByNameIgnoreCaseContainingOrderByDateDesc_thenNotFound() {

        List<Recipe> recipesByCategory = repository.findByNameIgnoreCaseContainingOrderByDateDesc("juice");

        assertThat(recipesByCategory.size()).isEqualTo(0);
    }
}