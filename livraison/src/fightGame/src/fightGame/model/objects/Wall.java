package fightGame.model.objects;

import gamePlayers.AbstractGameEntity;
import gamePlayers.util.EntityType;
import gamePlayers.util.Position;

public class Wall extends AbstractGameEntity {

    public Wall(Position position){
        super(EntityType.WALL, position, null);
    }

    public Wall clone() throws CloneNotSupportedException {
        
        Wall clonedWall = new Wall(new Position(position));

        return clonedWall;
    }

    
    
}
