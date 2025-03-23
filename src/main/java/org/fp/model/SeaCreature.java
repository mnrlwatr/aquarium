package org.fp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class SeaCreature {
    @Setter // пока что вызывается только из одного потока
    volatile int lifetime; // время в секундах
    volatile Position position;

    public synchronized void setPosition(Position position) {
        this.position = position;
    }
}
