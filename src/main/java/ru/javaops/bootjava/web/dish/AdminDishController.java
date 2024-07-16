package ru.javaops.bootjava.web.dish;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.error.IllegalRequestDataException;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.model.Restaurant;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.bootjava.web.RestValidation.assureIdConsistent;
import static ru.javaops.bootjava.web.RestValidation.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController extends AbstractDishController {
    static final String REST_URL = "/api/admin";

    @PostMapping(value = "/restaurants/{id}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("createWithLocation {}", dish);
        checkNew(dish);
        Restaurant restaurant = restaurantRepository.getExisted(id);
        dish.setRestaurant(restaurant);
        Dish createdDish = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/restaurants/{id}/dishes/{id}")
                .buildAndExpand(id, createdDish.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createdDish);
    }

    @GetMapping(value = "/restaurants/{restaurantId}/dishes/{dishId}")
    @Transactional(readOnly = true)
    public Dish get(@PathVariable int restaurantId, @PathVariable int dishId) {
        restaurantRepository.isPresentByIdOrElseThrow(restaurantId);
        checkDishBelongsToRestaurant(restaurantId, dishId);
        log.info("get {}", dishId);
        return dishRepository.getExisted(dishId);
    }

    @Override
    @GetMapping(value = "/restaurants/{id}/dishes/by-date")
    public List<Dish> getAllByRestaurantIdAndDateOfMenu(@PathVariable int id,
                                                        @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfMenu) {
        return super.getAllByRestaurantIdAndDateOfMenu(id, dateOfMenu);
    }

    @GetMapping(value = "/restaurants/{id}/dishes")
    @Transactional(readOnly = true)
    public List<Dish> getAllByRestaurantId(@PathVariable int id) {
        restaurantRepository.isPresentByIdOrElseThrow(id);
        log.info("getAllByRestaurantId {}", id);
        return dishRepository.getByRestaurantId(id);
    }

    @DeleteMapping(value = "/restaurants/{restaurantId}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int restaurantId, @PathVariable int dishId) {
        restaurantRepository.isPresentByIdOrElseThrow(restaurantId);
        checkDishBelongsToRestaurant(restaurantId, dishId);
        log.info("delete {}", dishId);
        dishRepository.deleteExisted(dishId);
    }

    @PutMapping(value = "/restaurants/{restaurantId}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@PathVariable int restaurantId,
                       @PathVariable int dishId,
                       @Valid @RequestBody Dish dish) {
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        checkDishBelongsToRestaurant(restaurantId, dishId);
        log.info("update {} with id = {}", dish, dishId);
        assureIdConsistent(dish, dishId);
        dish.setRestaurant(restaurant);
        dishRepository.save(dish);
    }

    private void checkDishBelongsToRestaurant(int restaurantId, int dishId) {
        dishRepository.getByRestaurantIdAndDishId(restaurantId, dishId)
                .orElseThrow(() -> new IllegalRequestDataException("dish with id = "
                        + dishId
                        + " does not belong to the restaurant with id = "
                        + restaurantId));
    }
}
