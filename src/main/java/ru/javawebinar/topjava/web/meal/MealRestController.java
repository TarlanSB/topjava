package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        log.info("create meal {}", meal);
        checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete meal {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        log.info("get meal id {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public List<Meal> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll meal");
        return service.getAll(userId);
    }

    public List<MealTo> getFilteredMealsToByStartEndDateTime(LocalDate startDate, LocalDate endDate,
                                                             LocalTime startTime, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getFilterMealsToByDateTime");
        List<Meal> mealsFilteredByDate = service.getFilteredMealByDate(startDate, endDate, userId);
        return MealsUtil.getFilteredTos(mealsFilteredByDate, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }
}