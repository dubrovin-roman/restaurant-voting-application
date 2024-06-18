package ru.javaops.bootjava.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javaops.bootjava.HasId;

@Entity
@Table(name = "voice")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Voice extends BaseEntity implements HasId {
}
