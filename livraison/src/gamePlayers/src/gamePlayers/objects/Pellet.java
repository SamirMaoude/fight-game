package gamePlayers.objects;

import gamePlayers.AbstractGameEntity;
import gamePlayers.util.EntityType;
import gamePlayers.util.Player;
import gamePlayers.util.Position;

public class Pellet extends AbstractGameEntity{
    private int energy;

    public Pellet(Position position,Player player, int energy) {
        super(EntityType.PELLET, position,player);
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
    public Object clone(){
        Pellet clone = null;
        try {
            clone = (Pellet)super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        clone.energy = this.energy;
        clone.position = (Position)this.position.clone();
        clone.owner = (Player)this.owner.clone();
        clone.type = EntityType.PELLET;
        return clone;
    }

    
}
