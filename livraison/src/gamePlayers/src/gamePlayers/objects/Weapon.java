package gamePlayers.objects;

import gamePlayers.AbstractGameEntity;
import gamePlayers.util.*;

public abstract class Weapon extends AbstractGameEntity {

    private int damage;

    public Weapon(EntityType type, Position position, int damage) {
        super(type, position);
        if(damage<0){
            throw new IllegalArgumentException("Weapon damage value must be positive");
        }
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
    
}
