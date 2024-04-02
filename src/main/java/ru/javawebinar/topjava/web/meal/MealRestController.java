package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public class MealRestController {
    private MealService service;
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    public Meal create(Meal meal) {
        log.info("create meal {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void update(Meal meal, int authUserId) {
        log.info("update {} with id={}", meal, authUserId);
        assureIdConsistent(meal, authUserId);
        service.update(meal);
    }

    public void delete(int id) {
        log.info("delete meal {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        log.info("get meal id {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Collection<Meal> getAll() {
        log.info("getAll meal");
        return service.getAll();
    }


}