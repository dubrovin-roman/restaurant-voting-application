package ru.javaops.bootjava.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.javaops.bootjava.error.DataConflictException;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.repository.RestaurantRepository;
import ru.javaops.bootjava.repository.VoteRepository;
import ru.javaops.bootjava.util.VotesUtil;
import ru.javaops.bootjava.web.AuthUser;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    static final String REST_URL = "/api/votes";

    private VoteRepository voteRepository;
    private RestaurantRepository restaurantRepository;

    @PostMapping("/by-restaurant")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void toVote(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        LocalDate localDate = LocalDate.now();
        int userId = authUser.id();
        log.info("To vote for restaurant id={} date={}", restaurantId, localDate);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        Optional<Vote> optionalVote = voteRepository.getByDateVotingAndUserId(localDate, userId);
        if (optionalVote.isPresent()) {
            if (VotesUtil.isCanNotReVote()) {
                throw new DataConflictException("Time is after eleven o'clock, voting cannot be changed.");
            }
            Vote vote = optionalVote.get();
            vote.setRestaurant(restaurant);
            voteRepository.save(vote);
        } else {
            voteRepository.createVoteToday(userId, restaurantId);
        }
    }
}
