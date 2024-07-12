package ru.javaops.bootjava.web.dish;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.repository.DishRepository;
import ru.javaops.bootjava.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractDishController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected DishRepository dishRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public List<Dish> getAllByRestaurantIdAndDateOfMenu(int restaurantId, @Nullable LocalDate dateOfMenu) {
        restaurantRepository.isPresentByIdOrElseThrow(restaurantId);
        log.info("getAll restaurantId = {} dateOfMenu = {}", restaurantId, dateOfMenu);
        if (dateOfMenu == null) {
            dateOfMenu = LocalDate.now();
        }
        return dishRepository.getAllByRestaurantIdAndDateOfMenu(restaurantId, dateOfMenu);
    }
}
