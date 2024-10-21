package gamePlayers;

import gamePlayers.util.*;

public abstract class AbstractGameEntity extends AbtractListenableModel implements GameEntity {

    protected EntityType type;
    protected Position position;
    
    public AbstractGameEntity( EntityType type, Position position) {
        this.type = type;
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public EntityType getType() {
        return this.type;
    }

    public abstract void attack();

    
}
