package gamePlayers;

import gamePlayers.util.EntityType;
import gamePlayers.util.Position;

public interface GameEntity {
    public Position getPosition();
    public EntityType getType();
    public void destroy();
}
