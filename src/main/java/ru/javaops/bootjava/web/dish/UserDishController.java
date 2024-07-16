package ru.javaops.bootjava.web.dish;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javaops.bootjava.model.Dish;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserDishController extends AbstractDishController {
    static final String REST_URL = "/api/user";

    @Override
    @GetMapping(value = "/restaurants/{id}/dishes/by-date")
    public List<Dish> getAllByRestaurantIdAndDateOfMenu(@PathVariable int id,
                                                        @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfMenu) {
        return super.getAllByRestaurantIdAndDateOfMenu(id, dateOfMenu);
    }
}
