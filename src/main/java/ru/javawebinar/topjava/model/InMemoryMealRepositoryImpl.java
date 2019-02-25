package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepositoryImpl implements MealRepository{
    private static AtomicInteger ID = new AtomicInteger(0);

    private Map<Integer, Meal> mealDB = new ConcurrentHashMap<>();

    public InMemoryMealRepositoryImpl() {

        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

    }

    public void add(Meal meal) {
        meal.setId(ID.getAndIncrement());
        mealDB.put(meal.getId(), meal);
    }

    public void delete(int id) {
        mealDB.remove(id);
    }

    public List<Meal> getList() {
        return new ArrayList<>(mealDB.values());
    }

    public Meal getMealById(int id){
        return mealDB.get(id);
    }

    public void update(Meal meal){
        getMealById(meal.getId()).setDateTime(meal.getDateTime());
        getMealById(meal.getId()).setDescription(meal.getDescription());
        getMealById(meal.getId()).setCalories(meal.getCalories());
    }
}

