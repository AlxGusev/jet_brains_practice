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

    public static final Long ID = 1L;
    public static final String EMAIL = "test@test.com";
    public static final String PASSWORD = "test_test";
    public static final String DIFFERENT_EMAIL = "testDifferent@test.com";
    public static final Map<String, String> WRONG_PARAM = getWrongParam();
    public static final Map<String, String> CATEGORY_PARAM = getCategoryParam();
    public static final Map<String, String> NAME_PARAM = getNameParam();
    public static final String ENCODED_PASSWORD = Base64.getEncoder().encodeToString(PASSWORD.getBytes(StandardCharsets.UTF_8));

    public static UserDto getUserDto() {
        return new UserDto(EMAIL, PASSWORD);
    }

    public static User getDifferentUser() {
        User user = getUser();
        user.setEmail(DIFFERENT_EMAIL);
        return user;
    }

    public static User getUser() {
        return new User(EMAIL, PASSWORD, false, null);
    }

    public static User getRegisteredUser() {
        User user = getUser();
        user.setEmail(DIFFERENT_EMAIL);
        user.setPassword(ENCODED_PASSWORD);
        user.setEnabled(true);
        user.setRoles("ROLE_USER");
        return user;
    }

    public static Recipe getRecipe() {
        return new Recipe(
                "Green Tea",
                "beverage",
                LocalDateTime.now(),
                "Green tea improve brain function",
                List.of("1 table spoon of green tea", "1 lemon slice"),
                List.of("Place all ingredients in a mug", "Steep for 5-10 minutes"));
    }

    public static Recipe getRecipeWithUser() {
        Recipe defaultRecipeWithUser = getRecipe();
        defaultRecipeWithUser.setUser(getUser());
        return defaultRecipeWithUser;
    }

    private static Map<String, String> getWrongParam() {
        Map<String, String> params = new HashMap<>();
        params.put("test", "test");
        return params;
    }

    private static Map<String, String> getCategoryParam() {
        Map<String, String> params = new HashMap<>();
        params.put("category", "test");
        return params;
    }

    private static Map<String, String> getNameParam() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "test");
        return params;
    }
}
