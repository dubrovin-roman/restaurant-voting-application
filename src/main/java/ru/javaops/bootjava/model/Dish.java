package ru.javaops.bootjava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javaops.bootjava.HasId;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends NamedEntity implements HasId {
    @Column(name = "price", nullable = false)
    @DecimalMin(value = "0.00")
    @NotNull
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "date_of_menu", nullable = false, columnDefinition = "date default now()", updatable = false)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate dateOfMenu = LocalDate.now();

    public Dish(Integer id, String name, BigDecimal price, Restaurant restaurant, LocalDate dateOfMenu) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
        this.dateOfMenu = dateOfMenu;
    }
}
