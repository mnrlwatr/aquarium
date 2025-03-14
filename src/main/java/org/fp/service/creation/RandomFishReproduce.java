package org.fp.service.creation;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.model.Position;
import org.fp.model.Fish;
import org.fp.model.enums.Gender;

import java.util.concurrent.ThreadLocalRandom;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public final class RandomFishReproduce {
    static ThreadLocalRandom random=ThreadLocalRandom.current();
    private RandomFishReproduce(){}

    public static synchronized Fish reproduce (){
        Position randomPosition = RandomPosition.getPosition();

        // Пол каждой новорожденной рыбы определяется методом Random
        boolean gender = random.nextBoolean();
        Gender fishGender = gender?Gender.MALE:Gender.FEMALE;

        // У каждой рыбы своя продолжительность жизни, и ее длина определяется методом Random.
        int lifeTime = random.nextInt(8,14); // можно калибровать, долгая жизнь=большая рождаемость / короткая жизнь=больше смертей

        return new Fish(lifeTime,fishGender,randomPosition);
    }
}
