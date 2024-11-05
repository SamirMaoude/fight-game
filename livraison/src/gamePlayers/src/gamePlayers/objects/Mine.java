package gamePlayers.objects;

import gamePlayers.util.*;

public class Mine extends Weapon{

    public Mine(Position position,int damage, Player player) {
        super(EntityType.MINE, position,player, damage);
    }

    public void use(){

    }

}
