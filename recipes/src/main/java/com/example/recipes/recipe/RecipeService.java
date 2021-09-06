package com.example.recipes.recipe;


import com.example.recipes.user.User;
import com.example.recipes.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public ResponseEntity<Object> findRecipeById(Long id) {

        Optional<Recipe> recipeById = recipeRepository.findById(id);

        if (recipeById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToRecipeDto(recipeById.get()));
    }

    public ResponseEntity<Object> saveRecipe(RecipeDto recipeDto, String email) {

        Optional<User> userByEmail = userRepository.findByEmail(email);

        if (userByEmail.isPresent()) {
            Recipe recipeToSave = convertToEntity(recipeDto);
            recipeToSave.setUser(userByEmail.get());
            return ResponseEntity.ok(convertToRecipeDtoOnlyId(recipeRepository.save(recipeToSave)));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Object> update(Long id, RecipeDto recipeDto, String email) {

        Recipe recipeToUpdate = convertToEntity(recipeDto);

        Optional<Recipe> recipeById = recipeRepository.findById(id);

        if (recipeById.isPresent()) {
            Recipe recipe = recipeById.get();
            if (recipe.getUser().getEmail().equals(email)) {
                recipe.setName(recipeToUpdate.getName());
                recipe.setCategory(recipe.getCategory());
                recipe.setDate(recipeToUpdate.getDate());
                recipe.setDescription(recipeToUpdate.getDescription());
                recipe.setIngredients(recipeToUpdate.getIngredients());
                recipe.setDirections(recipeToUpdate.getDirections());
                recipeRepository.save(recipe);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Object> deleteRecipe(Long id, String email) {

        Optional<Recipe> recipeById = recipeRepository.findById(id);

        if (recipeById.isPresent()) {
            Recipe recipe = recipeById.get();
            if (recipe.getUser().getEmail().equals(email)) {
                recipeRepository.delete(recipe);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Object> findAllByParameter(Map<String, String> searchParam) {

        if (searchParam.size() == 1 && (searchParam.containsKey("category") || searchParam.containsKey("name"))) {
            List<Recipe> recipes;
            List<RecipeDto> recipesDtoList;
            if (searchParam.containsKey("category")) {
                recipes = recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(searchParam.get("category"));
                recipesDtoList = convertToRecipesList(recipes);
                return ResponseEntity.ok(recipesDtoList);
            }
            if (searchParam.containsKey("name")) {
                recipes = recipeRepository.findByNameIgnoreCaseContainingOrderByDateDesc(searchParam.get("name"));
                recipesDtoList = convertToRecipesList(recipes);
                return ResponseEntity.ok(recipesDtoList);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    private RecipeDto convertToRecipeDtoOnlyId(Recipe recipe) {
        return new RecipeDto(recipe.getId());
    }

    private RecipeDto convertToRecipeDto(Recipe recipe) {
        return new RecipeDto(
                recipe.getName(),
                recipe.getCategory(),
                recipe.getDate(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getDirections());
    }

    private Recipe convertToEntity(RecipeDto recipeDto) {
        recipeDto.setDate(LocalDateTime.now());
        return new Recipe(
                recipeDto.getName(),
                recipeDto.getCategory(),
                recipeDto.getDate(),
                recipeDto.getDescription(),
                recipeDto.getIngredients(),
                recipeDto.getDirections());
    }

    private List<RecipeDto> convertToRecipesList(List<Recipe> list) {
        List<RecipeDto> recipesDtoList = new ArrayList<>();
        for (Recipe r : list) {
            recipesDtoList.add(convertToRecipeDto(r));
        }
        return recipesDtoList;
    }
}
