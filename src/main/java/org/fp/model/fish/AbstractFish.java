package org.fp.model.fish;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.fp.model.SeaCreature;
import org.fp.model.fish.enums.Gender;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class AbstractFish extends SeaCreature implements Moveable {
    Gender gender;

    protected AbstractFish(int lifetime, Gender gender) {
        super(lifetime);
        this.gender = gender;
    }

    public boolean isMale() {
        return getGender().equals(Gender.MALE);
    }

    public boolean isFemale() {
        return getGender().equals(Gender.FEMALE);
    }
}
