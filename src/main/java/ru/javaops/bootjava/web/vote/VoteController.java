package ru.javaops.bootjava.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.bootjava.error.DataConflictException;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.repository.RestaurantRepository;
import ru.javaops.bootjava.repository.VoteRepository;
import ru.javaops.bootjava.web.AuthUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    static final String REST_URL = "/api/restaurants";

    private VoteRepository voteRepository;
    private RestaurantRepository restaurantRepository;

    @PostMapping("/{id}/votes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toVote(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        LocalDate localDate = LocalDate.now();
        int userId = authUser.id();
        log.info("To vote for restaurant id={} date={}", id, localDate);
        restaurantRepository.isPresentByIdOrElseThrowNotFound(id);
        Optional<Vote> optionalVote = voteRepository.findByDateVotingAndUserId(localDate, userId);
        if (optionalVote.isPresent()) {
            if (LocalTime.now().isAfter(LocalTime.of(11, 0))) {
                throw new DataConflictException("Time is after eleven o'clock, voting cannot be changed.");
            }
            voteRepository.updateVote(userId, id, localDate);
        } else {
            voteRepository.createVote(userId, id);
        }
    }
}
