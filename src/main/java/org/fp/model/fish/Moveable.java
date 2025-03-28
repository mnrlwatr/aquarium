package org.fp.model.fish;

import org.fp.exception.AquariumIsNotWorkingException;

public interface Moveable {
    void randomMove() throws AquariumIsNotWorkingException;
    Object getRandomPositionToMove();
}
