package gamePlayers.objects;

import gamePlayers.util.*;

public class Mine extends Weapon{

    public Mine(Position position,int damage, Player player) {
        super(EntityType.MINE, position,player, damage);
    }

    public void use(){

    }

    @Override
    public Mine clone(){
        Mine clone = null;
        try {
            clone = (Mine)super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        clone.position = (Position)this.position.clone();
        clone.damage = this.damage;
        clone.owner = (Player)this.owner.clone();
        clone.type = EntityType.MINE;
        return clone;
    }

}
