package org.fp.service.creation;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.DependencyContainer;
import org.fp.model.Position;
import org.fp.service.managment.AquariumController;
import java.util.concurrent.ThreadLocalRandom;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class RandomPosition {
    static AquariumController aquariumController = (AquariumController) DependencyContainer.getDependency("aquariumController1");
    static ThreadLocalRandom random=ThreadLocalRandom.current();

    private RandomPosition() {
    }
    public static synchronized Position getPosition() {
        int x = random.nextInt(0, (aquariumController.getAquariumLength() + 1));
        int y = random.nextInt(0, (aquariumController.getAquariumHeight() + 1));
        return new Position(x, y);
    }
}
