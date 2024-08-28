package ru.javaops.bootjava.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {
    private LocalDate dateVoting;
    private int restaurantId;
    private int userId;

    public VoteTo(Integer id, LocalDate dateVoting, int restaurantId, int userId) {
        super(id);
        this.dateVoting = dateVoting;
        this.restaurantId = restaurantId;
        this.userId = userId;
    }
}
