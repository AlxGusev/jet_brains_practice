package com.example.recipes.web.controller;

import com.example.recipes.service.RecipeService;
import com.example.recipes.web.dto.RecipeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

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
        return recipeService.findRecipeById(id);
    }

    @PostMapping("/new")
    public ResponseEntity<Object> addRecipe(@Valid @RequestBody RecipeDto recipeDto, HttpServletRequest request) {
        return recipeService.saveRecipe(recipeDto, request.getUserPrincipal().getName());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecipe(@PathVariable("id") Long id, @Valid @RequestBody RecipeDto recipeDto, HttpServletRequest request) {
        return recipeService.update(id, recipeDto, request.getUserPrincipal().getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable("id") Long id, HttpServletRequest request) {
        return recipeService.deleteRecipe(id, request.getUserPrincipal().getName());
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchRecipe(@RequestParam Map<String, String> searchParam) {
        return recipeService.findAllByParameter(searchParam);
    }
}
