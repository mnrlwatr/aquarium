package org.fp.model.fish;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.fp.exception.AquariumIsNotWorkingException;
import org.fp.model.Position;
import org.fp.model.fish.enums.Gender;
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class ClownFish extends AbstractFish {
    volatile Position position;

    public ClownFish(int lifetime,Position position, Gender gender) {
        super(lifetime, gender);
        this.position = position;
    }
    public synchronized void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"(lifetime="+getLifetime()+", gender="+getGender()+")";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void randomMove() {
        Position positionToMove = getPositionToMove();
        ClownFish f2 = (ClownFish) aquariumController.moveIfPositionFree(positionToMove, fish);
        if (f2 == null) {
            aquariumController.releasePosition(fish.getPosition());
            fish.setPosition(positionToMove);
            fishStatistics.incrementTotalMovements();
        } else if (!checkGenderEquals(f2)) { //Если самцы и самки встречаются, они должны размножаться.
            bornRandomFish();
        }
    }
}
