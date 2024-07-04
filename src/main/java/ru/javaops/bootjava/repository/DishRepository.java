package ru.javaops.bootjava.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Dish;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
}
