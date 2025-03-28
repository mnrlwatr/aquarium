package org.fp.model.fish;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.exception.AquariumIsNotWorkingException;
import org.fp.model.Position;
import org.fp.model.fish.enums.Gender;

import java.util.concurrent.ConcurrentLinkedQueue;
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Axolotl extends AbstractFish {
    ConcurrentLinkedQueue<Position> positions;

    public Axolotl(int lifetime, Gender gender, ConcurrentLinkedQueue<Position> positions) {
        super(lifetime, gender);
        this.positions = positions;
    }

    @Override
    public ConcurrentLinkedQueue<Position> getPosition() {
        // Not implemented yet
        return positions;
    }

    @Override
    public void randomMove() throws AquariumIsNotWorkingException {
        // Not implemented yet
    }

    @Override
    public Object getRandomPositionToMove() {
        // Not implemented yet
        return null;
    }
}
