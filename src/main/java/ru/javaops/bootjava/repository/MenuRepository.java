package ru.javaops.bootjava.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Menu;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
}
