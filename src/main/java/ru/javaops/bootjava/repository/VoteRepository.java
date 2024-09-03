package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.error.NotFoundException;
import ru.javaops.bootjava.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.dateVoting = :date AND v.user.id = :userId")
    Optional<Vote> getByDateVotingAndUserId(@Param("date") LocalDate date, @Param("userId") int userId);

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT v FROM Vote v WHERE v.id = :id")
    Optional<Vote> getByIdWithRestaurantAndUser(@Param("id") int id);

    default Vote getByIdWithRestaurantAndUserExisted(int id) {
        Optional<Vote> optionalVote = getByIdWithRestaurantAndUser(id);
        if (optionalVote.isEmpty()) {
            throw new NotFoundException("Vote with id=" + id + " not found");
        }
        return optionalVote.get();
    }

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.dateVoting = :date")
    Optional<Vote> getByUserIdAndDateVotingWithRestaurantAndUser(@Param("userId") int userId, @Param("date") LocalDate date);

    default Vote getByUserIdAndDateVotingExisted(int userId, LocalDate date) {
        Optional<Vote> optionalVote = getByUserIdAndDateVotingWithRestaurantAndUser(userId, date);
        if (optionalVote.isEmpty()) {
            throw new NotFoundException("Vote for User with id=" + userId + " on date=" + date + " not found");
        }
        return optionalVote.get();
    }

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId ORDER BY v.dateVoting DESC ")
    List<Vote> getAllByUserIdWithRestaurant(@Param("userId") int userId);
}
