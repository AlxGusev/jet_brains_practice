package com.example.recipes.recipe;

import com.example.recipes.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private LocalDateTime date;

    private String description;

    @ElementCollection
    private List<String> ingredients;

    @ElementCollection
    private List<String> directions;

    @ManyToOne
    private User user;

    public Recipe() {
    }

    public Recipe(String name, String category, LocalDateTime date, String description, List<String> ingredients, List<String> directions) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(getId(), recipe.getId()) && Objects.equals(getName(), recipe.getName()) && Objects.equals(getCategory(), recipe.getCategory()) && Objects.equals(getDate(), recipe.getDate()) && Objects.equals(getDescription(), recipe.getDescription()) && Objects.equals(getIngredients(), recipe.getIngredients()) && Objects.equals(getDirections(), recipe.getDirections()) && Objects.equals(getUser(), recipe.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCategory(), getDate(), getDescription(), getIngredients(), getDirections(), getUser());
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients +
                ", directions=" + directions +
                ", user=" + user +
                '}';
    }
}
