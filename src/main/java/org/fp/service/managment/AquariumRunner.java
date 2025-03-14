package org.fp.service.managment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.fp.DependencyContainer;
import org.fp.model.Aquarium;
import org.fp.model.Fish;
import org.fp.service.FishLive;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AquariumRunner {
    AquariumController aquariumController = (AquariumController) DependencyContainer.getDependency("aquariumController1");
    Aquarium aquarium;

    /**
     *
     * @throws IllegalStateException if the simulation is already going
     */
    public void run() {
        if (!aquarium.isWorking()) {
            aquarium.setWorking(true);
            new Observer();
            for (Fish fish : aquarium.getFishes().values()) {
                new FishLive(fish);
            }
        } else {
            throw new IllegalStateException("Current Aquarium is already running ");
        }
    }

    private class Observer implements Runnable {

        private Observer() {
            new Thread(this).start();
        }

        @Override
        public void run() {
            while (!(aquariumController.isAquariumFull()) && !(aquariumController.isAquariumEmpty())) {
            }
            // happens before: вызов сеттера происходит только после первого вызова из внешнего класса (сперва вызовет поток main и только после этого тут)
            aquarium.setWorking(false);
        }
    }
}
