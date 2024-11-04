package gamePlayers.objects;

import gamePlayers.util.*;

public class Projectile extends Weapon{

    private int scope;

    public Projectile(Position position, int scope, int damage) {
        super(EntityType.PROJECTILE, position, damage);
        if(scope<=0){
            throw new IllegalArgumentException("Projectile distance or damage value must be positive");
        }
        this.scope = scope;
    }

    public int getScope() {
        return scope;
    }

    public void use (Direction direction){

    }


}
