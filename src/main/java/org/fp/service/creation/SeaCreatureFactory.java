package org.fp.service.creation;

import org.fp.model.SeaCreature;

public interface SeaCreatureFactory {
    SeaCreature create(String type);
}
