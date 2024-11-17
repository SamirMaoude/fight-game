package gamePlayers.objects;

import gamePlayers.AbstractGameEntity;
import gamePlayers.util.EntityType;
import gamePlayers.util.Player;
import gamePlayers.util.Position;

public class Pellet extends AbstractGameEntity{
    private int energy;

    public Pellet(Position position, int energy) {
        super(EntityType.PELLET, position,null);
        this.energy = energy;
    }

    public int getEnergy(){
        return this.energy;
    }

    @Override
    public void destroy() {
       this.position = null;
    }

    @Override
    public Pellet clone()  throws CloneNotSupportedException{
        Position clonedPosition = this.getPosition()!=null?new Position(this.getPosition()):null;

        return new Pellet(clonedPosition, this.getEnergy());
    } 
}
