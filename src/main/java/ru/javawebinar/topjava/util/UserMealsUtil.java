package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

        List<UserMealWithExceed> filteredMealList = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        for (UserMealWithExceed meal: filteredMealList) {
            System.out.println(meal);
        }
    }

    /*
    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> map = mealList.stream().collect(Collectors.groupingBy(meal->TimeUtil.toLocalDate(meal.getDateTime()),Collectors.summingInt(UserMeal::getCalories)));

        List<UserMealWithExceed> filteredMealList = new ArrayList<>();
        for (UserMeal meal: mealList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime,endTime)){
                boolean exceed=false;
                if (map.get(TimeUtil.toLocalDate(meal.getDateTime()))>caloriesPerDay) {
                    exceed=true;
                }
                filteredMealList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed));
            }
        }

        return filteredMealList;
    }
*/


    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        return mealList.stream()
                .filter(meal->TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime,endTime))
                .map(meal->new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        (mealList.stream().collect(Collectors.groupingBy(m->TimeUtil.toLocalDate(m.getDateTime()),
                                Collectors.summingInt(UserMeal::getCalories))))
                                .get(TimeUtil.toLocalDate(meal.getDateTime()))>caloriesPerDay))
                .collect(Collectors.toList());
    }

}