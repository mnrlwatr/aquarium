package org.fp.model.fish;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.fp.exception.AquariumIsNotWorkingException;
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
    public void randomMove() throws AquariumIsNotWorkingException {
        Position positionToMove = getRandomPositionToMove();
        ClownFish f2 = (ClownFish) aquariumController.moveIfPositionFree(positionToMove, fish);
        if (f2 == null) {
            aquariumController.releasePosition(fish.getPosition());
            fish.setPosition(positionToMove);
            fishStatistics.incrementTotalMovements();
        } else if (!checkGenderEquals(f2)) { //Если самцы и самки встречаются, они должны размножаться.
            bornRandomFish();
        }
    }

    @Override
    public Position getRandomPositionToMove() {
        while (true) {
            Position direction = RandomDirection.getDirection();
            int newX = abstractFish.getPosition().getX() + direction.getX();
            int newY = abstractFish.getPosition().getY() + direction.getY();
            // новое позиция для перемещения не должно выходит за рамки аквариума
            boolean x = (newX >= 0) && (newX <= aquariumController.getAquariumLength());
            boolean y = (newY >= 0) && (newY <= aquariumController.getAquariumHeight());
            if (x && y) {
                return new Position(newX, newY);
            }
        }
    }
}
