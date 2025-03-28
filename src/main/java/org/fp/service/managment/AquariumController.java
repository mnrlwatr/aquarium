package org.fp.service.managment;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fp.exception.AquariumIsNotWorkingException;
import org.fp.model.Position;
import org.fp.model.Aquarium;
import org.fp.model.SeaCreature;
import java.util.concurrent.ConcurrentMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AquariumController {

    Aquarium currentAquarium;

    /*
    *  ConcurrentHashMap это временное решение.
    *  Есть идея с использованием TreeMap где можно быстро ( O(log n) ) определять является ли позиция свободным или занятым,
    *  в начале инициализации аквариума надо будет заполнить TreeMap<K,V> (может быть трудозатратной операцией)
    *  где K это сгенерированный номер на основе позиции, а V - сам объект Position.
    *  Алгоритм для генерации Key будет = (Position.y * Aquarium.height) + Position.x
    *
    * */
    ConcurrentMap<Position, SeaCreature> seaCreaturesMap;

    public AquariumController(Aquarium currentAquarium) {
        this.currentAquarium = currentAquarium;
        seaCreaturesMap = this.currentAquarium.getSeaCreaturesMap();
    }

    public boolean isPositionFree(Position position) {
        return !seaCreaturesMap.containsKey(position);
    }
    /**
     * @return  null if seaCreature moved to positionToMove, if positionToMove is not free then returns seaCreature which is holding that position.
     * @throws  AquariumIsNotWorkingException if there was attempt to move fish when Aquarium is stopped
     * */
    public SeaCreature moveIfPositionFree(Position positionToMove, SeaCreature seaCreature) throws AquariumIsNotWorkingException {
        if(currentAquarium.isWorking()){
            return seaCreaturesMap.putIfAbsent(positionToMove, seaCreature);
        } else {
            throw new AquariumIsNotWorkingException();
        }
    }

    public boolean placeSeaCreature(SeaCreature seaCreature){
        if(seaCreature.getPosition().getClass() == Position.class){
            return seaCreaturesMap.putIfAbsent((Position)seaCreature.getPosition(), seaCreature)==null;
        }
        throw new IllegalArgumentException("Invalid Position class");
    }

    public boolean isAquariumEmpty() {
        return seaCreaturesMap.isEmpty();
    }

    public boolean isAquariumFull() {
        return seaCreaturesMap.size() >= currentAquarium.getCapacity();
    }

    public SeaCreature releasePosition(Position position) {
        return seaCreaturesMap.remove(position);
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
