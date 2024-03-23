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
import java.time.temporal.ChronoUnit;

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
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        int id = newId(request);
        Meal meal = new Meal(id, dateTime, description, calories);

        if (meal.getId() == -1) {
            listStorage.create(meal);
        } else {
            meal.setId(id);
            listStorage.update(meal);
        }

        RequestDispatcher view = request.getRequestDispatcher(mealList);
        request.setAttribute("meals", MealsUtil.filteredByStreams(listStorage.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        view.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");

        if (action == null) {
            action = "mealList";
        }

        switch (action) {
            case "delete":
                deleteMeal(request, response);
                break;
            case "create":
                createMeal(request, response);
                break;
            case "update":
                updateMeal(request, response);
                break;
            case "mealList":
            default:
                mealList(request, response);
                break;
        }
    }

    private void deleteMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        listStorage.delete(id);
        response.sendRedirect("meals");
    }

    private void mealList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("meals", MealsUtil.filteredByStreams(listStorage.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        request.getRequestDispatcher(mealList).forward(request, response);
    }

    private void updateMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Meal meal = listStorage.get(id);
        request.setAttribute("meal", meal);
        request.getRequestDispatcher(insertOrEdit).forward(request, response);
    }

    private void createMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 2000);
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
    }

    private int newId(HttpServletRequest request) {
        return request.getParameter("id").isEmpty() ? -1 : Integer.parseInt(request.getParameter("id"));
    }
}
