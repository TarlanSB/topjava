package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.MealsUtil.meals;

public class InMemoryMealStorage implements MealStorage {
    private final AtomicInteger counter = new AtomicInteger(1);
    private final Map<Integer, Meal> mealStorage = new ConcurrentHashMap<>();

    {
        meals.forEach(this::create);
    }

    @Override
    public Meal create(Meal meal) {
        meal.setId(counter.getAndIncrement());
        mealStorage.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void update(Meal meal) {
        mealStorage.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        mealStorage.remove(id);
    }

    @Override
    public Meal get(int id) {
        return mealStorage.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return mealStorage.values();
    }
}
