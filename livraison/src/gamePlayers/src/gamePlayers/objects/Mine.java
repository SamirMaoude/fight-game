package gamePlayers.objects;

import gamePlayers.util.*;

public class Mine extends Weapon{

    public Mine(Position position,int damage, Player player) {
        super(EntityType.MINE, position,player, damage);
    }

    public void use(){

    }

    @Override
    public Mine clone() throws CloneNotSupportedException {

        Position clonedPosition = this.getPosition()!=null?new Position(this.getPosition()):null;
        Player clonedPlayer = this.getOwner().clone();

        return new Mine(clonedPosition, this.getDamage(), clonedPlayer);
    }

}
