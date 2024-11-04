package gamePlayers.objects;

import gamePlayers.AbstractGameEntity;
import gamePlayers.fighters.Unit;
import gamePlayers.util.*;

public abstract class Weapon extends AbstractGameEntity {

    protected int damage;
    protected Unit unit;

    public Weapon(EntityType type, Position position, int damage) {
        super(type, position);
        if(damage<0){
            throw new IllegalArgumentException("Weapon damage value must be positive");
        }
        this.damage = damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void destroy() {
        this.position = null;
        this.unit = null;
    }
}
