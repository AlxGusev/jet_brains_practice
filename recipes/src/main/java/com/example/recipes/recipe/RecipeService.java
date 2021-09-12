package com.example.recipes.recipe;


import com.example.recipes.user.IUserRepository;
import com.example.recipes.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class RecipeService {

    private final IRecipeRepository recipeRepository;
    private final IUserRepository userRepository;

    @Autowired
    public RecipeService(IRecipeRepository recipeRepository, IUserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public Optional<Recipe> findRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public Recipe saveRecipe(Recipe recipe, String email) {

        Optional<User> userByEmail = userRepository.findByEmail(email);

        if (userByEmail.isEmpty()) {
            return recipe;
        }

        recipe.setUser(userByEmail.get());
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Long id, Recipe recipeToUpdate, String email) {

        Optional<Recipe> recipeById = recipeRepository.findById(id);

        if (recipeById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Recipe recipe = recipeById.get();
        if (recipe.getUser().getEmail().equals(email)) {
            recipe.setName(recipeToUpdate.getName());
            recipe.setCategory(recipe.getCategory());
            recipe.setDate(recipeToUpdate.getDate());
            recipe.setDescription(recipeToUpdate.getDescription());
            recipe.setIngredients(recipeToUpdate.getIngredients());
            recipe.setDirections(recipeToUpdate.getDirections());
            return recipeRepository.save(recipe);
        }
        return recipeToUpdate;
    }

    public boolean deleteRecipe(Long id, String email) {

        Optional<Recipe> recipeById = recipeRepository.findById(id);

        if (recipeById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Recipe recipe = recipeById.get();
        if (recipe.getUser().getEmail().equals(email)) {
            recipeRepository.delete(recipe);
            return true;
        }
        return false;
    }

    public List<Recipe> findAllByParameter(Map<String, String> searchParam) {

        if (searchParam.size() == 1 && (searchParam.containsKey("category") || searchParam.containsKey("name"))) {
            if (searchParam.containsKey("category")) {
                return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(searchParam.get("category"));
            }
            if (searchParam.containsKey("name")) {
                return recipeRepository.findByNameIgnoreCaseContainingOrderByDateDesc(searchParam.get("name"));
            }
        }
        return Collections.emptyList();
    }
}
