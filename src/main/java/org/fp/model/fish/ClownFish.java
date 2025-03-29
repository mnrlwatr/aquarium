package org.fp.model.fish;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.fp.model.Position;
import org.fp.model.fish.enums.Gender;
import org.fp.service.util.RandomDirection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class ClownFish extends AbstractFish {
    volatile Position position;

    public ClownFish(int lifetime, Position position, Gender gender) {
        super(lifetime, gender);
        this.position = position;
    }

    public synchronized void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(lifetime=" + getLifetime() + ", gender=" + getGender() + ")";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Position calculateRandomPositionToMove(int aquariumLength, int aquariumHeight) {
        while (true) {
            Position direction = RandomDirection.getDirection();
            int newX = getPosition().getX() + direction.getX();
            int newY = getPosition().getY() + direction.getY();
            // новое место для перемещения не должно выходит за рамки аквариума
            boolean x = (newX >= 0) && (newX <= aquariumLength);
            boolean y = (newY >= 0) && (newY <= aquariumHeight);
            if (x && y) {
                return new Position(newX, newY);
            }
        }
    }
}
