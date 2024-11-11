package gamePlayers.objects;

import gamePlayers.util.*;
public class Bomb extends Weapon{

    private int timeBeforeExplosition;
    public Bomb(Position position, int damage, Player palyer, int timeBeforeExplosition) {
        super(EntityType.BOMB, position,palyer, damage);
        if(timeBeforeExplosition<1){
            throw new IllegalArgumentException("You can not provide a value under 1 as boom time");
        }
        this.timeBeforeExplosition = timeBeforeExplosition;
    }

    public int getTimeBeforeExplosition() {
        return timeBeforeExplosition;
    }

    public void detonate(){
        
    }

   

   
}
