package ru.javaops.bootjava.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javaops.bootjava.HasId;

import java.util.Date;

@Entity
@Table(name = "voice")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Voice extends BaseEntity implements HasId {
    @Column(name = "date_time_voting", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date dateTimeVoting = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
