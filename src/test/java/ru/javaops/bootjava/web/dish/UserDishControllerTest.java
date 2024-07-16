package ru.javaops.bootjava.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.bootjava.web.AbstractControllerTest;
import ru.javaops.bootjava.web.restaurant.RestaurantTestData;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.bootjava.web.user.UserTestData.USER_MAIL;

class UserDishControllerTest extends AbstractControllerTest {
    private static final String REST_URL_FORMAT = UserDishController.REST_URL + "/restaurants/%d/dishes/by-date";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurantIdAndDateOfMenuNull() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT, 1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.DISH_MATCHER.contentJson(DishTestData.dishesByDateNow));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurantIdAndDateOfMenuNow() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT, 1))
                .param("dateOfMenu", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.DISH_MATCHER.contentJson(DishTestData.dishesByDateNow));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurantIdAndDateOfMenuOld() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT, 1))
                .param("dateOfMenu", DishTestData.DATE_OLD_MENU))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.DISH_MATCHER.contentJson(DishTestData.dishesByOldDate));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurantIdAndDateOfMenuNoDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT, 1))
                .param("dateOfMenu", DishTestData.DATE_NO_DISHES))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.DISH_MATCHER.contentJson(List.of()));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurantIdNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT, RestaurantTestData.NOT_FOUND_ID)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}