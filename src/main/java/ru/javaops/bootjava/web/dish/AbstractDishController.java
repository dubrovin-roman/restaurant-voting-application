package ru.javaops.bootjava.web.dish;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractDishController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected DishRepository dishRepository;

    public Dish get(int id) {
        log.info("get {}", id);
        return dishRepository.getExisted(id);
    }

    public List<Dish> getAllByRestaurantIdAndDateOfMenu(int restaurantId, LocalDate dateOfMenu) {
        log.info("getAll restaurantId = {} dateOfMenu = {}", restaurantId, dateOfMenu);
        return dishRepository.getAllByRestaurantIdAndDateOfMenu(restaurantId, dateOfMenu);
    }
}
