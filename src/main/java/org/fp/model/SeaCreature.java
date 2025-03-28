package org.fp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class SeaCreature {
    volatile int lifetime; // Setter пока что вызывается только из одного потока, время в секундах.
    public abstract Object getPosition();
}
