package com.example.recipes.persistence;

import com.example.recipes.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findByNameIgnoreCaseContainingOrderByDateDesc(String name);
}
