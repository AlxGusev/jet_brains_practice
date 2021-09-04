package com.example.recipes;

import com.example.recipes.spring.RecipeSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipesApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class[] {RecipesApplication.class, RecipeSecurityConfig.class}, args);
    }

}
