package com.example.recipes.utils;

import com.example.recipes.recipe.Recipe;
import com.example.recipes.user.User;
import com.example.recipes.user.UserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static UserDto getDefaultUserDto() {
        return new UserDto(getDefaultEmail(), getDefaultPassword());
    }

    public static String getDefaultEmail() {
        return "test@test.com";
    }

    public static String getDefaultDifferentEmail() {
        return "testDifferent@test.com";
    }

    public static String getDefaultPassword() {
        return "test_test";
    }

    public static User getDefaultDifferentUser() {
        User user = getDefaultUser();
        user.setEmail(getDefaultDifferentEmail());
        return user;
    }

    public static User getDefaultUser() {
        return new User(getDefaultEmail(), getDefaultPassword(), false, null);
    }

    public static User getRegisteredUser() {
        User user = getDefaultUser();
        user.setEmail(getDefaultDifferentEmail());
        user.setPassword(getEncodedPassword());
        user.setEnabled(true);
        user.setRoles("ROLE_USER");
        return user;
    }

    public static String getEncodedPassword() {
        return Base64.getEncoder().encodeToString(getDefaultPassword().getBytes(StandardCharsets.UTF_8));
    }

    public static Recipe getDefaultRecipe() {
        return new Recipe(
                "Green Tea",
                "beverage",
                LocalDateTime.of(2020, 1, 1, 10, 0, 0),
                "Green tea improve brain function",
                List.of("1 table spoon of green tea", "1 lemon slice"),
                List.of("Place all ingredients in a mug", "Steep for 5-10 minutes"));
    }

    public static Recipe getDefaultRecipeWithUser() {
        Recipe defaultRecipeWithUser = getDefaultRecipe();
        defaultRecipeWithUser.setUser(getDefaultUser());
        return defaultRecipeWithUser;
    }

    public static Map<String, String> getWrongParam() {
        Map<String, String> params = new HashMap<>();
        params.put("test", "test");
        return params;
    }

    public static Map<String, String> getCategoryParam() {
        Map<String, String> params = new HashMap<>();
        params.put("category", "test");
        return params;
    }

    public static Map<String, String> getNameParam() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "test");
        return params;
    }
}
