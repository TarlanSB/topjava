package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Meal> getFilteredMealByDate(LocalDate startDate, LocalDate endDate, int userId) {
        LocalDateTime ltd1 = getStartLocalDateTime(startDate);
        LocalDateTime ltd2 = getEndLocalDateTime(endDate);
        return repository.getFilteredMealsByDateTime(userId, ltd1, ltd2);
    }

    private LocalDateTime getStartLocalDateTime(LocalDate startDate) {
        return startDate == null ? LocalDateTime.of(-999999999, 1, 1, 0, 0)
                : startDate.atStartOfDay();
    }

    private LocalDateTime getEndLocalDateTime(LocalDate endDate) {
        return endDate == null ? LocalDateTime.of(+999999999, 12, 31, 0, 0)
                : endDate.plus(1, ChronoUnit.DAYS).atStartOfDay();
    }
}