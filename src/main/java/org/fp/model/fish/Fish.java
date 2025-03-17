package org.fp.model.fish;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.fp.model.Position;
import org.fp.model.fish.enums.Gender;
import org.fp.model.fish.enums.Type;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Fish {
    @Setter // вызывается только из одного потока (FishLive Thread)
    volatile int lifetime; // в секундах
    final Gender gender;
    final Type type ;
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
