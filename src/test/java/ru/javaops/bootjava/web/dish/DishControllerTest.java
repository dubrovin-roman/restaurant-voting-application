package ru.javaops.bootjava.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.bootjava.web.AbstractControllerTest;
import ru.javaops.bootjava.web.restaurant.RestaurantTestData;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.bootjava.web.dish.DishTestData.*;
import static ru.javaops.bootjava.web.user.UserTestData.USER_MAIL;

class DishControllerTest extends AbstractControllerTest {
    private static final String REST_URL_FORMAT = DishController.REST_URL + "/restaurants/%d/dishes/by-date";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurantIdAndDateOfMenuNull() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT, RestaurantTestData.ASTORIA_ID)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dishesByDateNowAstoria));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurantIdAndDateOfMenuNow() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT, RestaurantTestData.ASTORIA_ID))
                .param("dateOfMenu", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dishesByDateNowAstoria));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurantIdAndDateOfMenuOld() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT, RestaurantTestData.ASTORIA_ID))
                .param("dateOfMenu", DATE_OLD_MENU))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dishesByOldDate));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurantIdAndDateOfMenuNoDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT, RestaurantTestData.ASTORIA_ID))
                .param("dateOfMenu", DATE_NO_DISHES))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(List.of()));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurantIdNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT, RestaurantTestData.NOT_FOUND_ID)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllOnToday() throws Exception {
        perform(MockMvcRequestBuilders.get(DishController.REST_URL + "/dishes/on-today"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(DishTestData.getToOnToday()));
    }
}