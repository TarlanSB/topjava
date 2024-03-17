package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealListStorage;
import ru.javawebinar.topjava.repository.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealTo.class);
    private static final Integer serialVersionUID = 1;
    private static final String insertOrEdit = "/mealForm.jsp";
    private static final String mealList = "/meals.jsp";

    private MealStorage listStorage;

    @Override
    public void init() throws ServletException {
        super.init();
        listStorage = new MealListStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("localDateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        int id = Integer.parseInt(request.getParameter("id"));
        Meal meal = new Meal(id, dateTime, description, calories);

        if (meal.getId() == 0) {
            listStorage.create(meal);
        } else {
            meal.setId(id);
            listStorage.update(meal);
        }

        RequestDispatcher view = request.getRequestDispatcher(mealList);
        request.setAttribute("meals", listStorage.getAll());
        view.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");
        String forward = "";

        if (action == null) {
            action = "mealList";
        }

        switch (action) {
            case "mealList":
                forward = mealList;
                mealList(request);
                break;
            case "delete":
                forward = mealList;
                deleteMeal(request);
                break;
            case "add":
            case "edit":
                forward = insertOrEdit;
                editMeal(request);
                break;
            default:
                forward = insertOrEdit;
        }
        int caloriesPerDay = 2000;
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(listStorage.getAll(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
        request.setAttribute("meals", mealsTo);
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    private void deleteMeal(HttpServletRequest request) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        listStorage.delete(id);
        request.setAttribute("meals", listStorage.getAll());
    }

    private void mealList(HttpServletRequest request) throws ServletException, IOException {
        request.setAttribute("meals", listStorage.getAll());
    }

    private void editMeal(HttpServletRequest request) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Meal meal = listStorage.get(id);
        request.setAttribute("meal", listStorage.getAll());
    }
}
