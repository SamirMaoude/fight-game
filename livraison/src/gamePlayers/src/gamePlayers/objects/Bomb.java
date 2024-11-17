package gamePlayers.objects;

import gamePlayers.util.*;
public class Bomb extends Weapon{

    private int timeBeforeExplosion;

    public Bomb(Position position, int damage, Player palyer, int timeBeforeExplosion) {
        super(EntityType.BOMB, position,palyer, damage);
        if(timeBeforeExplosion<0){
            throw new IllegalArgumentException("You can not provide a value under 0 as boom time");
        }
        this.timeBeforeExplosion = timeBeforeExplosion;
    }

    public int getTimeBeforetimeBeforeExplosion() {
        return timeBeforeExplosion;
    }

    public void detonate(){
        
    }

    
    
    @Override
    public Bomb clone() throws CloneNotSupportedException {

        Position clonedPosition = this.getPosition()!=null?new Position(this.getPosition()):null;
        Player clonedPlayer = this.getOwner().clone();

        return new Bomb(clonedPosition, this.getDamage(), clonedPlayer, this.timeBeforeExplosion);
    }

    public void descreasedTime(){
        this.timeBeforeExplosion--;
    }

    public void setTimeBeforetimeBeforeExplosion(int timeBeforeExplosion) {
        this.timeBeforeExplosion = timeBeforeExplosion;
    }

   

   
}
