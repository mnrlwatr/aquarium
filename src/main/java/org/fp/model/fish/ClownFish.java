package org.fp.model.fish;
import lombok.EqualsAndHashCode;
import org.fp.model.Position;
import org.fp.model.fish.enums.Gender;
@EqualsAndHashCode
public class ClownFish extends AbstractFish {
    public ClownFish(int lifetime, Position position, Gender gender) {
        super(lifetime, position, gender);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"(lifetime="+getLifetime()+", gender="+getGender()+")";
    }
}
