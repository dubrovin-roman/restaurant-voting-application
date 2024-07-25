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
    Optional<Vote> findByDateVotingAndUserId(@Param("date") LocalDate date, @Param("userId") int userId);

    @Query("SELECT v.restaurant.id FROM Vote v WHERE v.id = :id")
    Integer getRestaurantIdByVoteId(@Param("id") int id);

    default Integer getRestaurantIdByVoteIdOrElseThrowNotFound(int id) {
        Integer restaurantId = getRestaurantIdByVoteId(id);
        if (restaurantId == null) {
            throw new NotFoundException("Vote with id=" + id + " not found");
        }
        return restaurantId;
    }

    @Query("SELECT v.restaurant.id FROM Vote v WHERE v.user.id = :userId AND v.dateVoting = :date")
    Integer getRestaurantIdByUserIdAndDateVoting(@Param("userId") int userId, @Param("date") LocalDate date);

    default Integer getRestaurantIdByUserIdAndDateVotingOrElseThrowNotFound(int userId, LocalDate date) {
        Integer restaurantId = getRestaurantIdByUserIdAndDateVoting(userId, date);
        if (restaurantId == null) {
            throw new NotFoundException("Vote for User with id=" + userId + " on date=" + date + " not found");
        }
        return restaurantId;
    }

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId ORDER BY v.dateVoting DESC ")
    List<Vote> findByUserId(@Param("userId") int userId);
}
