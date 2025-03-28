package org.fp.service.managment;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.DependencyContainer;
import org.fp.model.Aquarium;
import org.fp.model.fish.AbstractFish;
import org.fp.service.creation.fish.FishFactory;
import org.fp.service.util.RandomPosition;
import java.util.concurrent.ThreadLocalRandom;
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class AquariumFill {
    static ThreadLocalRandom random=ThreadLocalRandom.current();
    static AquariumController aquariumController = (AquariumController) DependencyContainer.getDependency("aquariumController1");
    static FishFactory fishFactory = (FishFactory) DependencyContainer.getDependency("FishFactory1");
    private AquariumFill() {
    }

    public static void fillFish(Aquarium aquarium, String type) {
        // в аквариуме всегда будет минимум 200 рыб и максимум (aquarium.сapacity - 200)
        // чтобы аквариум быстро не выключалось (из-за опустошения или из-за переполнения)
        int randomFishesAmount = random.nextInt(200, (aquarium.getCapacity() - 201));
        // На момент написания в аквариуме было N самцов и M самок.
        // Значения N и M также определяются методом Random.
        int maleCount = 0;
        int femaleCount = 0;
        for (int i = 0; i < randomFishesAmount; i++) {
            AbstractFish fish = fishFactory.create(type);
            if (fish.isMale()) {
                maleCount++;
            } else if (fish.isFemale()) {
                femaleCount++;
            }

            while (!aquariumController.placeSeaCreature(fish)) {
                fish.setPosition(RandomPosition.getPosition());
            }
        }

        System.out.println("Aquarium filled with fishes, total amount: " + aquarium.getSeaCreaturesMap().size());
        System.out.println("Male count = " + maleCount);
        System.out.println("Female count = " + femaleCount);
    }
}
