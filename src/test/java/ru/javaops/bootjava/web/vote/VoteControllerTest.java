package ru.javaops.bootjava.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.repository.VoteRepository;
import ru.javaops.bootjava.util.VoteUtil;
import ru.javaops.bootjava.web.AbstractControllerTest;
import ru.javaops.bootjava.web.restaurant.RestaurantTestData;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.bootjava.web.user.UserTestData.*;
import static ru.javaops.bootjava.web.vote.VoteTestData.VOTE_MATCHER;
import static ru.javaops.bootjava.web.vote.VoteTestData.vote_admin;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL_FORMAT = VoteController.REST_URL + "/%d/votes";

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void toVote() throws Exception {
        perform(MockMvcRequestBuilders.post(String.format(REST_URL_FORMAT, RestaurantTestData.ASTORIA_ID)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertTrue(voteRepository.findByDateVotingAndUserId(LocalDate.now(), USER_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void reVote() throws Exception {
        VoteUtil.prepareEndVoteTimeForPassTests();

        perform(MockMvcRequestBuilders.post(String.format(REST_URL_FORMAT, RestaurantTestData.ASTORIA_ID)))
                .andExpect(status().isNoContent())
                .andDo(print());

        Optional<Vote> optionalVote = voteRepository.findByDateVotingAndUserId(LocalDate.now(), ADMIN_ID);
        assertTrue(optionalVote.isPresent());
        assertEquals(RestaurantTestData.ASTORIA_ID, optionalVote.get().getRestaurant().id());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void reVoteFail() throws Exception {
        VoteUtil.prepareEndVoteTimeForFailTests();

        perform(MockMvcRequestBuilders.post(String.format(REST_URL_FORMAT, RestaurantTestData.ASTORIA_ID)))
                .andExpect(status().isConflict())
                .andDo(print());

        Optional<Vote> optionalVote = voteRepository.findByDateVotingAndUserId(LocalDate.now(), ADMIN_ID);
        assertTrue(optionalVote.isPresent());
        VOTE_MATCHER.assertMatch(optionalVote.get(), vote_admin);
    }
}