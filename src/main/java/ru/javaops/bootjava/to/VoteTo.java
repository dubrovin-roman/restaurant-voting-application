package ru.javaops.bootjava.to;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate dateVoting;
    private int restaurantId;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private int userId;

    public VoteTo(Integer id, LocalDate dateVoting, int restaurantId, int userId) {
        super(id);
        this.dateVoting = dateVoting;
        this.restaurantId = restaurantId;
        this.userId = userId;
    }
}
