package com.example.recipes.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecipeDto {

    private Long id;

    @NotBlank(message = "name shouldn't be empty")
    private String name;

    @NotBlank(message = "category shouldn't be empty")
    private String category;

    private LocalDateTime date;

    @NotBlank(message = "description shouldn't be empty")
    private String description;

    @NotEmpty
    @Size(min = 1)
    private List<String> ingredients;

    @NotEmpty
    @Size(min = 1)
    private List<String>  directions;

    public RecipeDto() {
    }

    public RecipeDto(Long id) {
        this.id = id;
    }

    public RecipeDto(String name, String category, LocalDateTime date, String description, List<String> ingredients, List<String> directions) {
        this.name = name;
        this.category = category;
        this.date = date;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }
}
