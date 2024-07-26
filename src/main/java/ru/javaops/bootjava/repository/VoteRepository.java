package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
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

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO vote (restaurant_id, user_id) VALUES (:restaurantId, :userId)")
    void createVoteToday(@Param("userId") int userId, @Param("restaurantId") int restaurantId);

    @Query("SELECT v FROM Vote v WHERE v.dateVoting = :date AND v.user.id = :userId")
    Optional<Vote> getByDateVotingAndUserId(@Param("date") LocalDate date, @Param("userId") int userId);

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT v FROM Vote v WHERE v.id = :id")
    Optional<Vote> getByVoteId(@Param("id") int id);

    default Vote getByVoteIdOrElseThrowNotFound(int id) {
        Optional<Vote> optionalVote = getByVoteId(id);
        if (optionalVote.isEmpty()) {
            throw new NotFoundException("Vote with id=" + id + " not found");
        }
        return optionalVote.get();
    }

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.dateVoting = :date")
    Optional<Vote> getByUserIdAndDateVoting(@Param("userId") int userId, @Param("date") LocalDate date);

    default Vote getByUserIdAndDateVotingOrElseThrowNotFound(int userId, LocalDate date) {
        Optional<Vote> optionalVote = getByUserIdAndDateVoting(userId, date);
        if (optionalVote.isEmpty()) {
            throw new NotFoundException("Vote for User with id=" + userId + " on date=" + date + " not found");
        }
        return optionalVote.get();
    }

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId ORDER BY v.dateVoting DESC ")
    List<Vote> getByUserId(@Param("userId") int userId);

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT v FROM Vote v ORDER BY v.dateVoting DESC ")
    List<Vote> getAllWithEntityGraphAndOrderByDateVotingDesc();
}
