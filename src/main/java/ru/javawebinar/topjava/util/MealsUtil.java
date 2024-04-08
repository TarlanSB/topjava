package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    public static final List<Meal> userMeals = Arrays.asList(
            new Meal(LocalDateTime.of(2024, Month.APRIL, 1, 7, 0), "Завтрак", 380),
            new Meal(LocalDateTime.of(2024, Month.APRIL, 1, 12, 0), "Обед", 380),
            new Meal(LocalDateTime.of(2024, Month.APRIL, 1, 20, 0), "Ужин", 900)
    );

    public static final List<Meal> adminMeals = Arrays.asList(
            new Meal(LocalDateTime.of(2024, Month.APRIL, 1, 7, 0), "Админ Завтрак", 200),
            new Meal(LocalDateTime.of(2024, Month.APRIL, 1, 12, 0), "Админ Обед", 500),
            new Meal(LocalDateTime.of(2024, Month.APRIL, 1, 20, 0), "Админ Ужин", 1200)
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filterByPredicate(meals, caloriesPerDay, meal -> true);
    }

    private static List<MealTo> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
