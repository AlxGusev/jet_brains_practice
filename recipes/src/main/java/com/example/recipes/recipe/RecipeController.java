package com.example.recipes.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRecipeById(@PathVariable("id") Long id) {

        Optional<Recipe> recipeById = recipeService.findRecipeById(id);
        if (recipeById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToRecipeDto(recipeById.get()));

    }

    @PostMapping("/new")
    public ResponseEntity<Object> addRecipe(@Valid @RequestBody RecipeDto recipeDto, HttpServletRequest request) {

        Recipe recipe = recipeService.saveRecipe(convertToEntity(recipeDto), request.getUserPrincipal().getName());

        if (recipe.getUser() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToRecipeDtoOnlyId(recipe));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecipe(@PathVariable("id") Long id, @Valid @RequestBody RecipeDto recipeDto, HttpServletRequest request) {

        Recipe recipe = recipeService.updateRecipe(id, convertToEntity(recipeDto), request.getUserPrincipal().getName());

        if (recipe.equals(convertToEntity(recipeDto))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(convertToRecipeDto(recipe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable("id") Long id, HttpServletRequest request) {

        boolean isDeleted = recipeService.deleteRecipe(id, request.getUserPrincipal().getName());

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchRecipe(@RequestParam Map<String, String> searchParam) {

        List<Recipe> allByParameter = recipeService.findAllByParameter(searchParam);

        if (allByParameter.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(convertToRecipesDtoList(allByParameter));
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

    private List<RecipeDto> convertToRecipesDtoList(List<Recipe> list) {
        List<RecipeDto> recipesDtoList = new ArrayList<>();
        for (Recipe r : list) {
            recipesDtoList.add(convertToRecipeDto(r));
        }
        return recipesDtoList;
    }
}
