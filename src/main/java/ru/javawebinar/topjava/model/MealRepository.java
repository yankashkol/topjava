package ru.javawebinar.topjava.model;

import java.util.List;

public interface MealRepository {
    void add(Meal meal);

    void delete(int id);

    List<Meal> getList();

    Meal getMealById(int id);

    void update(Meal meal);
}
