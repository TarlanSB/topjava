package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealTo.class);
    private List<Meal> meals;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        meals = MealsUtil.getMeals();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 2000);
        request.setAttribute("list_mealsTo", mealsTo);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
