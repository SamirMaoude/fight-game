package gamePlayers.fighters;

import java.util.LinkedList;

import gamePlayers.objects.Bomb;
import gamePlayers.objects.Mine;
import gamePlayers.objects.Projectile;
import gamePlayers.util.*;

public class UnitBuilder {
    private Position position;
    private String name;
    private int energy;
    private LinkedList<Bomb> bombs;
    private LinkedList<Mine> mines;
    private LinkedList<Projectile> projectiles;

    public UnitBuilder(){
        this.position = null;
        this.name = null;
        this.energy = 0;
        this.bombs = null;
        this.mines = null;
        this.projectiles = null;
    }

    public UnitBuilder withPosition(Position position){
        this.position = position;
        return this;
    }

    public UnitBuilder withName(String name){
        this.name = name;
        return this;
    }

    public UnitBuilder withEnergy(int energy){
        this.energy = energy;
        return this;
    }

    public UnitBuilder withBombs(){
        LinkedList<Bomb> bombs = new LinkedList<>();
        for(int i=0; i<10; i++){
            bombs.add(new Bomb(null, 50, 3));
        }
        this.bombs = bombs;
        return this;
    }

    public UnitBuilder withMines(){
        LinkedList<Mine> mines = new LinkedList<>();
        for(int i=0; i<10; i++){
            mines.add(new Mine(null, 50));
        }
        this.mines = mines;
        return this;
    }

    public UnitBuilder withProjectiles(){
        LinkedList<Projectile> projectiles = new LinkedList<>();
        for(int i=0; i<10; i++){
            projectiles.add(new Projectile(null, 10, 20));
        }
        this.projectiles = projectiles;
        return this;
    }

    public Unit build(){
        return new Unit(this.position, this.name, this.energy, this.bombs, this.mines, this.projectiles);
    }
}
