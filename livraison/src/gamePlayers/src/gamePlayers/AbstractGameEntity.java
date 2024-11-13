package gamePlayers;

import gamePlayers.util.*;

public abstract class AbstractGameEntity extends AbtractListenableModel implements GameEntity, Cloneable {

    protected EntityType type;
    protected Position position;
    protected Player owner;
    
    public AbstractGameEntity( EntityType type, Position position, Player owner) {
        this.type = type;
        this.position = position;
        this.owner = owner;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Position position){
        this.position = position;
    }

    @Override
    public EntityType getType() {
        return this.type;
    }

    @Override
    public void setOwner(Player palyer){
        this.owner = palyer;
    }

    @Override
    public Player getOwner(){
        return this.owner;
    }

    @Override
<<<<<<< HEAD
    public AbstractGameEntity clone() throws CloneNotSupportedException {
        return (AbstractGameEntity) super.clone();
    }

=======
    public void destroy(){
        
    }



>>>>>>> 59fa2d6 (Debut demo affichage graphique)
    
}
