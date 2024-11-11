package gamePlayers;

import gamePlayers.util.EntityType;
import gamePlayers.util.Player;
import gamePlayers.util.Position;

public interface GameEntity {
    public Position getPosition();
    public void setPosition(Position position);
    public EntityType getType();
    public void destroy();
    public Player getOwner();
    public void setOwner(Player player);
}
