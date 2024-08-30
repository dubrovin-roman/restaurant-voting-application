package ru.javaops.bootjava.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.error.DataConflictException;
import ru.javaops.bootjava.error.IllegalRequestDataException;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.repository.RestaurantRepository;
import ru.javaops.bootjava.repository.VoteRepository;
import ru.javaops.bootjava.to.VoteTo;
import ru.javaops.bootjava.util.VotesUtil;
import ru.javaops.bootjava.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.javaops.bootjava.web.RestValidation.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    static final String REST_URL = "/api/votes";

    private VoteRepository voteRepository;
    private RestaurantRepository restaurantRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<VoteTo> createWithLocation(@RequestBody VoteTo voteTo, @AuthenticationPrincipal AuthUser authUser) {
        log.info("createWithLocation with restaurantId={}", voteTo.getRestaurantId());
        checkNew(voteTo);
        LocalDate localDate = LocalDate.now();
        int userId = authUser.id();
        Restaurant restaurant = restaurantRepository.getExisted(voteTo.getRestaurantId());
        Optional<Vote> optionalVote = voteRepository.getByDateVotingAndUserId(localDate, userId);
        if (optionalVote.isPresent()) {
            throw new DataConflictException("The vote on today already exists, it is impossible to create a new one.");
        } else {
            Vote created = voteRepository.save(new Vote(null, localDate, restaurant, AuthUser.authUser()));
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(VotesUtil.createTo(created));
        }

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void reVote(@RequestBody VoteTo voteTo, @AuthenticationPrincipal AuthUser authUser) {
        log.info("reVote with restaurantId={}", voteTo.getRestaurantId());
        LocalDate localDate = LocalDate.now();
        int userId = authUser.id();
        Optional<Vote> optionalVote = voteRepository.getByDateVotingAndUserId(localDate, userId);
        if (optionalVote.isEmpty()) {
            throw new DataConflictException("The vote on today not exists, it is impossible re vote.");
        } else {
            if (VotesUtil.isCanNotReVote()) {
                throw new DataConflictException("Time is after eleven o'clock, voting cannot be changed.");
            }
            Restaurant restaurant = restaurantRepository.getExisted(voteTo.getRestaurantId());
            Vote vote = optionalVote.get();
            vote.setRestaurant(restaurant);
            voteRepository.save(vote);
        }
    }

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("get with voteId={}", id);
        int userId = authUser.id();
        VoteTo voteTo = VotesUtil.createTo(voteRepository.getByIdWithRestaurantAndUserExisted(id));
        if (userId != voteTo.getUserId()) {
            throw new IllegalRequestDataException(String.format("Vote with id=%d, not belong User with id=%d", id, userId));
        }
        return voteTo;
    }

    @GetMapping("/on-today")
    @Cacheable(value = "votes", key = "#authUser.id()")
    public VoteTo getOnToday(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        LocalDate date = LocalDate.now();
        log.info("getVoteOnToday for user id={}", userId);
        return VotesUtil.createTo(voteRepository.getByUserIdAndDateVotingExisted(userId, date));
    }

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("getAll for user id={}", userId);
        return VotesUtil.createListTo(voteRepository.getByUserId(userId));
    }
}
