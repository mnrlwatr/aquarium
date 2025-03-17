package org.fp.service.managment;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.exception.AquariumIsNotWorkingException;
import org.fp.model.Position;
import org.fp.model.Aquarium;
import org.fp.model.fish.Fish;
import java.util.concurrent.ConcurrentMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AquariumController {

    Aquarium currentAquarium;
    ConcurrentMap<Position, Fish> fishesMap;

    public AquariumController(Aquarium currentAquarium) {
        this.currentAquarium = currentAquarium;
        fishesMap = this.currentAquarium.getFishes();
    }

    public boolean isPositionFree(Position position) {
        return !fishesMap.containsKey(position);
    }
    /**
     * @return  null if fish moved to positionToMove, if positionToMove is not free then returns fish which is holding that position.
     * @throws  AquariumIsNotWorkingException if there was attempt to move fish when Aquarium is stopped
     * */
    public Fish moveIfPositionFree(Position positionToMove, Fish fish) throws AquariumIsNotWorkingException {
        if(currentAquarium.isWorking()){
            return fishesMap.putIfAbsent(positionToMove, fish);
        } else {
            throw new AquariumIsNotWorkingException();
        }
    }

    public boolean placeFish (Fish fish){
        return fishesMap.putIfAbsent(fish.getPosition(), fish)==null;
    }

    public boolean isAquariumEmpty() {
        return fishesMap.isEmpty();
    }

    public boolean isAquariumFull() {
        return fishesMap.size() >= currentAquarium.getCapacity();
    }

    public Fish releasePosition(Position position) {
        return fishesMap.remove(position);
    }

    public int getAquariumLength(){
        return currentAquarium.getLength();
    }

    public int getAquariumHeight(){
        return currentAquarium.getHeight();
    }
    public boolean isAquariumWorking(){
        return currentAquarium.isWorking();
    }
}
