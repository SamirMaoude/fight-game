package gamePlayers.fighters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;
import java.util.LinkedList;

import gamePlayers.objects.Bomb;
import gamePlayers.objects.Mine;
import gamePlayers.objects.Projectile;
import gamePlayers.util.Position;
import gamePlayers.util.Move;

public class TestUnit {

    private Unit unit;
    private Position initialPosition;
    private LinkedList<Bomb> bombs;
    private LinkedList<Mine> mines;
    private LinkedList<Projectile> projectiles;

    @BeforeEach
    void setUp(){
        initialPosition = new Position(0,0);
        bombs = new LinkedList<>();
        mines = new LinkedList<>();
        projectiles = new LinkedList<>();
        unit = new Unit(initialPosition,"Warrior",100,bombs,mines,projectiles);
    }

    @Test
    void testInitialization(){
        assertEquals("Warrior", unit.getName());
        assertEquals(100,unit.getEnergy());
        assertEquals(initialPosition,unit.getPosition());

    }

    @Test
    void testInvalidEnergy(){
        Exception exception = assertThrows(IllegalArgumentException.class,()->{
            new Unit(initialPosition,"warrior",0,bombs,mines,projectiles);
        }); 
        assertEquals("The energy value must be above 0 during creation",exception.getMessage());

    }

    @Test
    void testEmptyName(){
        Exception exception = assertThrows(IllegalArgumentException.class,()->{
            new Unit(initialPosition,"",0,bombs,mines,projectiles);

        });
        assertEquals("The name must not be empty",exception.getMessage());

    }
    @Test 
    void testSetName(){
        unit.setName("name");
        assertEquals("Name",unit.getName());
    }

    @Test 
    void testSetNameEmpty(){
        Exception exception = assertThrows(IllegalArgumentException.class,()->{
            unit.setName("");
        });
        assertEquals("the name provided must not be empty",exception.getMessage());

    }

    @Test 
    void testMoveRight(){
        unit.move(Move.RIGHT);
        assertEquals(1,unit.getPosition().getCol());
    }

    @Test 
    void testMoveLeft(){
        unit.move(Move.LEFT);
        assertEquals(-1,unit.getPosition().getCol());
    }

    @Test 
    void testMoveTop(){
        unit.move(Move.TOP);
        assertEquals(-1,unit.getPosition().getRow());
    }

    @Test 
    void testMoveBottom(){
        unit.move(Move.BOTTOM);
        assertEquals(1,unit.getPosition().getRow());
    }

    @Test 
    void testHasBombs(){
        assertFalse(unit.hasBombs());
        mines.add(new Bomb());
        assertTrue(unit.hasBombs());
    }

    @Test 
    void testHasMines(){
        assertFalse(unit.hasMines());
        mines.add(new Mine());
        assertTrue(unit.hasMines());
    }

    @Test 
    void testHasProjectiles(){
        assertFalse(unit.hasProjectiles());
        mines.add(new Projectile());
        assertTrue(unit.hasProjectiles());
    }
    
}
