package org.fp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.fp.model.enums.Gender;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Fish {
    @Setter // вызывается только из одного потока (FishLive Thread)
    volatile int lifetime; // в секундах
    final Gender gender;
    volatile Position position;

    public synchronized void setPosition(Position position) {
        this.position = position;
    }

    public boolean isMale (){
        return getGender().equals(Gender.MALE);
    }
    public boolean isFemale (){
        return getGender().equals(Gender.FEMALE);
    }
}
