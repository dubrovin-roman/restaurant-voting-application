package ru.javaops.bootjava.web.dish;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.repository.RestaurantRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.bootjava.web.RestValidation.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AdminDishController extends AbstractDishController {
    static final String REST_URL = "/api/admin";

    private final RestaurantRepository restaurantRepository;

    @PostMapping(value = "/restaurants/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("createWithLocation {}", dish);
        checkNew(dish);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        dish.setRestaurant(restaurant);
        Dish createdDish = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/dishes/{id}")
                .buildAndExpand(createdDish.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createdDish);
    }

    @Override
    @GetMapping(value = "/dishes/{id}")
    public Dish get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping(value = "/restaurants/{id}/dishes")
    public List<Dish> getAllByRestaurantIdAndDateOfMenu(@PathVariable int id,
                                                        @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfMenu) {
        return super.getAllByRestaurantIdAndDateOfMenu(id, dateOfMenu);
    }
}
