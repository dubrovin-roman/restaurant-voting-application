package ru.javaops.bootjava.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class DishTo extends NamedTo {
    @DecimalMin(value = "0.00")
    @NotNull
    private BigDecimal price;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int restaurantId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate dateOfMenu;

    public DishTo(Integer id, String name, BigDecimal price, int restaurantId, LocalDate dateOfMenu) {
        super(id, name);
        this.price = price;
        this.restaurantId = restaurantId;
        this.dateOfMenu = dateOfMenu;
    }
}
