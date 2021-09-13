package com.example.recipes.user;

import com.example.recipes.recipe.RecipeDto;
import com.example.recipes.user.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserDto {

    @ValidPassword(message = "Must be at least 8 character long, contains '@' and '.'")
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 8)
    private String password;

    @JsonIgnore
    private Set<RecipeDto> recipes;

    public UserDto() {
    }

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RecipeDto> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<RecipeDto> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", recipes=" + recipes +
                '}';
    }
}
