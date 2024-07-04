package ru.javaops.bootjava.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Voice;

@Transactional(readOnly = true)
public interface VoiceRepository extends BaseRepository<Voice> {
}
