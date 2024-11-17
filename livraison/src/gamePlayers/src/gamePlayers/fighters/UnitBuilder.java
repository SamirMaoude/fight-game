package gamePlayers.fighters;

import java.util.LinkedList;

import gamePlayers.objects.Bomb;
import gamePlayers.objects.Mine;
import gamePlayers.objects.Projectile;
import gamePlayers.util.*;

public class UnitBuilder {
    private Position position;
    private Player owner;
    private String name;
    private int energy;
    private LinkedList<Bomb> bombs;
    private LinkedList<Mine> mines;
    private LinkedList<Projectile> projectiles;
    private int shieldRetention;

    public UnitBuilder(){
        this.position = null;
        this.name = null;
        this.energy = 0;
        this.bombs = null;
        this.mines = null;
        this.projectiles = null;
        this.owner = null;
        this.shieldRetention = 0;
    }

    public UnitBuilder withOwner(Player player){
        this.owner = player;
        return this;
    }
    public UnitBuilder withPosition(Position position){
        this.position = position;
        return this;
    }

    public UnitBuilder withName(String name){
        this.name = name;
        return this;
    }

    public UnitBuilder withShieldRetention(int shieldRetention){
        this.shieldRetention = shieldRetention;
        return this;
    }

    public UnitBuilder withEnergy(int energy){
        this.energy = energy;
        return this;
    }

    public UnitBuilder withBombs(int nbBombs, int damage,int timeBeforeExplosion){
        LinkedList<Bomb> bombs = new LinkedList<>();
        for(int i=0; i<nbBombs; i++){
            bombs.add(new Bomb(null, damage,this.owner, timeBeforeExplosion));
        }
        this.bombs = bombs;
        return this;
    }

    public UnitBuilder withMines(int nbMines, int damage){
        LinkedList<Mine> mines = new LinkedList<>();
        for(int i=0; i<nbMines; i++){
            mines.add(new Mine(null, damage,this.owner));
        }
        this.mines = mines;
        return this;
    }

    public UnitBuilder withProjectiles(int nbProjectiles, int scope, int damage ){
        LinkedList<Projectile> projectiles = new LinkedList<>();
        for(int i=0; i<nbProjectiles; i++){
            projectiles.add(new Projectile(null, scope, damage,this.owner));
        }
        this.projectiles = projectiles;
        return this;
    }

    public Unit build(){
        return new Unit(this.position, this.name, this.owner, this.energy, this.bombs, this.mines, this.projectiles, this.shieldRetention);
    }
}
