package fightGame.model.objects;

import gamePlayers.AbstractGameEntity;
import gamePlayers.util.EntityType;
import gamePlayers.util.Position;

public class Wall extends AbstractGameEntity {

    public Wall(Position position){
        super(EntityType.WALL, position, null);
    }

    /**
     * TODO: Définir destroy
     */
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'destroy'");
    }


    
    
}
