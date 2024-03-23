package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {
    Meal create(Meal meal);

    void update(Meal meal);

    void delete(int id);

    Meal get(int id);

    List<Meal> getAll();
}
