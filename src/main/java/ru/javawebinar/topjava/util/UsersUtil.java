package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    public static final List<Meal> userMeals = Arrays.asList(
            new Meal(LocalDateTime.of(2024, Month.APRIL, 1, 7, 0), "Завтрак", 380, USER_ID),
            new Meal(LocalDateTime.of(2024, Month.APRIL, 1, 12, 0), "Обед", 380, USER_ID),
            new Meal(LocalDateTime.of(2024, Month.APRIL, 1, 20, 0), "Ужин", 900, USER_ID)
    );

    public static final List<Meal> adminMeals = Arrays.asList(
            new Meal(LocalDateTime.of(2024, Month.APRIL, 1, 7, 0), "Админ Завтрак", 200, ADMIN_ID),
            new Meal(LocalDateTime.of(2024, Month.APRIL, 1, 12, 0), "Админ Обед", 500, ADMIN_ID),
            new Meal(LocalDateTime.of(2024, Month.APRIL, 1, 20, 0), "Админ Ужин", 1200, ADMIN_ID)
    );
}

