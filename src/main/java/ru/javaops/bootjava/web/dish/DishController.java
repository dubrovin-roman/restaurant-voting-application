package ru.javaops.bootjava.web.dish;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.repository.DishRepository;
import ru.javaops.bootjava.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class DishController {
    static final String REST_URL = "/api";

    protected DishRepository dishRepository;
    protected RestaurantRepository restaurantRepository;

    @GetMapping(value = "/restaurants/{id}/dishes/by-date")
    @Transactional(readOnly = true)
    public List<Dish> getAllByRestaurantIdAndDateOfMenu(@PathVariable int id,
                                                        @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfMenu) {
        log.info("getAll restaurantId = {} dateOfMenu = {}", id, dateOfMenu);
        restaurantRepository.isPresentByIdOrElseThrowNotFound(id);
        if (dateOfMenu == null) {
            dateOfMenu = LocalDate.now();
        }
        return dishRepository.getAllByRestaurantIdAndDateOfMenu(id, dateOfMenu);
    }
}
