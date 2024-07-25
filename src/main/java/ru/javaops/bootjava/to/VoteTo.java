package ru.javaops.bootjava.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.javaops.bootjava.HasId;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo implements HasId {
    LocalDate dateVoting;
    int restaurantId;
    int userId;

    public VoteTo(Integer id, LocalDate dateVoting, int restaurantId, int userId) {
        super(id);
        this.dateVoting = dateVoting;
        this.restaurantId = restaurantId;
        this.userId = userId;
    }
}
