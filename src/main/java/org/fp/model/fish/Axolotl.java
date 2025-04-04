package org.fp.model.fish;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.model.Position;
import org.fp.model.fish.enums.Gender;
import java.util.Queue;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Axolotl extends AbstractFish {
    Queue<Position> positions; // длинная рыба

    public Axolotl(int lifetime, Gender gender, Queue<Position> positions) {
        super(lifetime, gender);
        this.positions = positions;
    }

    @Override
    public Object getPosition() {
        return positions;
    }

    @Override
    public Queue<Position> calculateRandomPositionToMove(int aquariumLength, int aquariumHeight) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
