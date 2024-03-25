package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.MealsUtil.meals;

public class InMemoryMealStorage implements MealStorage {
    private final AtomicInteger counter = new AtomicInteger(1);
    private final List<Meal> mealList = new CopyOnWriteArrayList<>();

    {
        meals.forEach(this::create);
    }

    @Override
    public Meal create(Meal meal) {
        meal.setId(counter.getAndIncrement());
        mealList.add(meal);
        return meal;
    }

    @Override
    public void update(Meal meal) {
        for (int i = 0; i < mealList.size(); i++) {
            if (mealList.get(i).getId().equals(meal.getId())) {
                mealList.set(i, meal);
            }
        }
    }

    @Override
    public void delete(int id) {
        mealList.remove(get(id));
    }

    @Override
    public Meal get(int id) {
        for (Meal meal : mealList) {
            if (meal.getId() == id) {
                return meal;
            }
        }
        return null;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealList);
    }
}
