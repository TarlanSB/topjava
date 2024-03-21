package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.MealsUtil.MEALS;

public class MealListStorage implements MealStorage {
    public final AtomicInteger counter = new AtomicInteger(0);
    private final List<Meal> mealList = new CopyOnWriteArrayList<>();

    {
        MEALS.forEach(this::create);
    }

    @Override
    public Meal create(Meal meal) {
        meal.setId(counter.getAndIncrement());
        mealList.add(meal);
        return meal;
    }

    @Override
    public void update(Meal meal) {
        if (isExist(meal.getId())) {
            mealList.set(meal.getId(), meal);
        }
    }

    @Override
    public void delete(int id) {
        mealList.remove(id);
    }

    @Override
    public Meal get(int id) {
        return mealList.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealList);
    }

    public boolean isExist(int id) {
        return id >= 0;
    }
}
