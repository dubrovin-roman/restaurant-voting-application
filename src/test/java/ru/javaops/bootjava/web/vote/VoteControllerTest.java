package ru.javaops.bootjava.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.repository.VoteRepository;
import ru.javaops.bootjava.util.VotesUtil;
import ru.javaops.bootjava.web.AbstractControllerTest;
import ru.javaops.bootjava.web.restaurant.RestaurantTestData;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.bootjava.web.user.UserTestData.*;
import static ru.javaops.bootjava.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL_BY_RESTAURANT = VoteController.REST_URL + "/by-restaurant";
    private static final String REST_URL_ON_TODAY = VoteController.REST_URL + "/on-today";

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void toVote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_BY_RESTAURANT)
                .param("restaurantId", String.valueOf(RestaurantTestData.ASTORIA_ID)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertTrue(voteRepository.getByDateVotingAndUserId(LocalDate.now(), USER_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void reVote() throws Exception {
        VotesUtil.prepareEndVoteTimeForPassTests();

        perform(MockMvcRequestBuilders.post(REST_URL_BY_RESTAURANT)
                .param("restaurantId", String.valueOf(RestaurantTestData.ASTORIA_ID)))
                .andExpect(status().isNoContent())
                .andDo(print());

        Optional<Vote> optionalVote = voteRepository.getByDateVotingAndUserId(LocalDate.now(), ADMIN_ID);
        assertTrue(optionalVote.isPresent());
        assertEquals(RestaurantTestData.ASTORIA_ID, optionalVote.get().getRestaurant().id());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void reVoteFail() throws Exception {
        VotesUtil.prepareEndVoteTimeForFailTests();

        perform(MockMvcRequestBuilders.post(REST_URL_BY_RESTAURANT)
                .param("restaurantId", String.valueOf(RestaurantTestData.ASTORIA_ID)))
                .andExpect(status().isConflict())
                .andDo(print());

        Optional<Vote> optionalVote = voteRepository.getByDateVotingAndUserId(LocalDate.now(), ADMIN_ID);
        assertTrue(optionalVote.isPresent());
        VOTE_MATCHER.assertMatch(optionalVote.get(), vote_admin_on_today);
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void getVoteOnToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_ON_TODAY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_MATCHER.contentJson(vote_to_guest_on_today));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVoteOnTodayNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_ON_TODAY))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(VoteController.REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_MATCHER.contentJson(voteToListByUser));
    }
}