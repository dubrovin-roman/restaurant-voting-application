package ru.javaops.bootjava.web.dish;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.repository.DishRepository;
import ru.javaops.bootjava.repository.RestaurantRepository;

import java.net.URI;

import static ru.javaops.bootjava.web.RestValidation.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminDishController {
    static final String REST_URL = "/api/admin";

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @PostMapping(value = "/restaurants/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("createWithLocation {}", dish);
        checkNew(dish);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        dish.setRestaurant(restaurant);
        Dish createdDish = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/restaurants/{id}/dishes/{id}")
                .buildAndExpand(restaurantId, createdDish.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createdDish);
    }
}
