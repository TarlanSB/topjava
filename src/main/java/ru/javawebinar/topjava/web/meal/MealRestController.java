package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

public class MealRestController {
    private MealService service;
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    public Meal save(Meal meal, int userId) {
        log.info("save meal {}", meal);
        return service.save(meal, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete meal {}", id);
        service.delete(id, userId);
    }

    public Meal get(int id, int userId) {
        log.info("get meal id {}", id);
        return service.get(id, userId);
    }

    public Collection<Meal> getAll() {
        log.info("getAll meal");
        return service.getAll();
    }
}