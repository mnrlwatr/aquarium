package org.fp.service.creation;

import org.fp.service.creation.fish.FishFactory;

public final class SeaCreatureFactoryProducer {
    private SeaCreatureFactoryProducer() {}
    public static SeaCreatureFactory getFactory(SeaCreatureFactoryType scft){
        if(scft == null){
            throw new NullPointerException("scft cannot be null");
        } else if (scft.equals(SeaCreatureFactoryType.Fish)) {
            return new FishFactory();
        } else {
            throw new IllegalArgumentException("Unsupported SeaCreatureFactoryType: " + scft);
        }
    }

    public enum SeaCreatureFactoryType {
        Fish
    }
}
