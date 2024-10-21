package gamePlayers.objects;

import gamePlayers.util.*;
public class Bomb extends Weapon{

    private int time;
    public Bomb(Position position, int damage, int time) {
        super(EntityType.BOMB, position, damage);
        if(time<1){
            throw new IllegalArgumentException("You can not provide a value under 1 as boom time");
        }
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    @Override
    public void attack() {
        //Creer une explosion
    }

}
