package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.ListStorage;
import ru.javawebinar.topjava.repository.MemoryStorage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealTo.class);
    private static final Integer serialVersionUID = 1;
    private static final String insertOrEdit = "/mealForm.jsp";
    private static final String mealList = "/meals.jsp";

    MemoryStorage listStorage = new ListStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        listStorage.mealStorage();
    }

//    public MealServlet() {
//        listStorage = new ListStorage();
//    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("localDateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Integer id = Integer.parseInt(request.getParameter("id"));
        Meal meal = new Meal(dateTime, description, calories);

        if (meal.getId() == null) {
            listStorage.save(meal);
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

        switch (action) {
            case "List":
                forward = mealList;
                mealList(request);
                break;
            case "delete":
                forward = insertOrEdit;
                deleteMeal(request);
                break;
            case "edit":
                forward = mealList;
                editMeal(request);
                break;
            default:
                forward = insertOrEdit;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    private void deleteMeal(HttpServletRequest request) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        listStorage.delete(id);
        request.setAttribute("meals", listStorage.getAll());
    }

    private void editMeal(HttpServletRequest request) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Meal meal = listStorage.get(id);
        request.setAttribute("meal", listStorage.getAll());
    }

    private void mealList(HttpServletRequest request) throws ServletException, IOException {
        request.setAttribute("meals", listStorage.getAll());
    }
}
