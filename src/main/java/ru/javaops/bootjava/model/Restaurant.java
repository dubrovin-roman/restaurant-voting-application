package ru.javaops.bootjava.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javaops.bootjava.HasId;
import ru.javaops.bootjava.validation.NoHtml;

import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity implements HasId {
    @Column(name = "address", unique = true, nullable = false)
    @NotBlank
    @Size(min = 5, max = 512)
    @NoHtml
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Dish> dishList;

    public Restaurant(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
    }
}
