package gamePlayers.objects;

import gamePlayers.AbstractGameEntity;
import gamePlayers.util.EntityType;
import gamePlayers.util.Position;

public class Pellet extends AbstractGameEntity{
    private int energy;

    public Pellet(EntityType type, Position position, int energy) {
        super(type, position);
        this.energy = energy;
        
    }

    public int getEnergy(){
        return this.energy;
    }

    @Override
    public void destroy() {
       this.position = null;
    }
    
}
