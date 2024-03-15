package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ListStorage implements MemoryStorage {
    public static final AtomicInteger COUNTER = new AtomicInteger(1);
    private final List<Meal> mealList = new CopyOnWriteArrayList<>();

    @Override
    public void mealStorage() {
        List<Meal> meals =  Arrays.asList(
                new Meal( LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (!isExist(meal.getId())) {
            doSave(meal);
        }
        return meal;
    }

    @Override
    public void update(Meal meal) {
        if (isExist(meal.getId())) {
            mealList.set(meal.getId(), meal);
        }
    }

    @Override
    public void delete(Integer id) {
        if (isExist(id)) {
            mealList.remove(get(id));
        }
    }

    @Override
    public Meal get(Integer id) {
        return mealList.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealList);
    }

    public boolean isExist(Integer id) {
        return id != null;
    }

    private void doSave(Meal m) {
        m.setId(COUNTER.incrementAndGet());
        mealList.add(m);
    }
}
