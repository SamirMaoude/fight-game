package gamePlayers.objects;

import gamePlayers.util.*;

public class Mine extends Weapon{

    public Mine(Position position, int damage) {
        super(EntityType.MINE, position,damage);
    }

    @Override
    public void attack() {
        //Creer une explosion
    }

}
