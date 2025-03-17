package org.fp.service.managment;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.DependencyContainer;
import org.fp.model.Aquarium;
import org.fp.model.fish.Fish;
import org.fp.service.creation.RandomFishReproduce;
import org.fp.service.creation.RandomPosition;
import java.util.concurrent.ThreadLocalRandom;
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class AquariumFill {
    static ThreadLocalRandom random=ThreadLocalRandom.current();
    static AquariumController aquariumController = (AquariumController) DependencyContainer.getDependency("aquariumController1");
    private AquariumFill() {
    }

    public static void fillAquarium(Aquarium aquarium) {
        // в аквариуме всегда будет минимум 200 рыб и максимум (aquarium.сapacity - 200)
        // чтобы аквариум быстро не выключалось (из-за опустошения или из-за переполнения)
        int randomFishesAmount = random.nextInt(200, (aquarium.getCapacity() - 201));
        // На момент написания в аквариуме было N самцов и M самок.
        // Значения N и M также определяются методом Random.
        int maleCount = 0;
        int femaleCount = 0;
        for (int i = 0; i < randomFishesAmount; i++) {
            Fish fish = RandomFishReproduce.reproduce();
            if (fish.isMale()) {
                maleCount++;
            } else if (fish.isFemale()) {
                femaleCount++;
            }

            while (!aquariumController.placeFish(fish)) {
                fish.setPosition(RandomPosition.getPosition());
            }
        }

        System.out.println("Aquarium filled with fishes, total amount: " + aquarium.getFishes().size());
        System.out.println("Male count = " + maleCount);
        System.out.println("Female count = " + femaleCount);
    }
}
