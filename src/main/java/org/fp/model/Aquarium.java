package org.fp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
@ToString
public class Aquarium {
    int height; // Position.y
    int length; // Position.x
    int capacity;
    @NonFinal
    @Setter
    volatile boolean working = false; // сеттер одновременно из разных потоков не вызывается
    ConcurrentMap<Position, SeaCreature> seaCreaturesMap = new ConcurrentHashMap<>();

}
