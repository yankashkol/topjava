package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealRepository;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

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
    private static final Logger log = getLogger(MealServlet.class);

    private MealRepository mealRepository = new InMemoryMealRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = request.getParameter("action");
        switch (action == null?"list":action) {
            case "list":
            {List<MealTo> meals = MealsUtil.getFilteredWithExcess(mealRepository.getList(),
                        LocalTime.MIN,
                        LocalTime.MAX,
                        2000);
                request.setAttribute("meals", meals);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);}
            case "delete": {
                int id = Integer.parseInt(request.getParameter("id"));
                mealRepository.delete(id);
                List<MealTo> meals = MealsUtil.getFilteredWithExcess(mealRepository.getList(),
                        LocalTime.MIN,
                        LocalTime.MAX,
                        2000);
                request.setAttribute("meals", meals);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;}
            case "update": {
                int id = Integer.parseInt(request.getParameter("id"));
                Meal meal = mealRepository.getMealById(id);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                break;}
            case "insert": {
                request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                break;}
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        meal.setDateTime(dateTime);
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));

        String id = request.getParameter("id");
        if(id == null || id.isEmpty())
        {
            mealRepository.add(meal);
        }
        else
        {
            meal.setId(Integer.parseInt(id));
            mealRepository.update(meal);
        }

        List<MealTo> meals = MealsUtil.getFilteredWithExcess(mealRepository.getList(),
                LocalTime.MIN,
                LocalTime.MAX,
                2000);
        request.setAttribute("meals", meals);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }
}

