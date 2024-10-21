package gamePlayers.objects;

import gamePlayers.util.*;

public class Projectile extends Weapon{

    private int distance;

    public Projectile(Position position, int distance, int damage) {
        super(EntityType.PROJECTILE, position,damage);
        if(distance<=0){
            throw new IllegalArgumentException("Projectile distance or damage value must be positive");
        }
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public void attack() {
        //Creer un tir
    }

    
    


}
