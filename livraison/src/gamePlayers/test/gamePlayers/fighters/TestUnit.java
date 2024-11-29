package gamePlayers.fighters;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gamePlayers.objects.Bomb;
import gamePlayers.objects.Mine;
import gamePlayers.objects.Projectile;
import gamePlayers.util.Direction;
import gamePlayers.util.Position;

public class TestUnit {

    private Unit unit;
    private Position initialPosition;
    private LinkedList<Bomb> bombs;
    private LinkedList<Mine> mines;
    private LinkedList<Projectile> projectiles;

    @Before
    void setUp(){
        initialPosition = new Position(0,0);
        bombs = new LinkedList<>();
        mines = new LinkedList<>();
        projectiles = new LinkedList<>();
        unit = new Unit(initialPosition, null, null, 0, bombs, mines, projectiles, 0);
    }

    @Test
    void testInitialization(){
        Assert.assertEquals("Warrior", unit.getName());
        Assert.assertEquals(100,unit.getEnergy());
        Assert.assertEquals(initialPosition,unit.getPosition());

    }

    @Test
    void testInvalidEnergy(){
        Exception exception = Assert.assertThrows(IllegalArgumentException.class,()->{
            new Unit(initialPosition,"warrior",null, 0,bombs,mines,projectiles, 0);
        }); 
        Assert.assertEquals("The energy value must be above 0 during creation",exception.getMessage());

    }

    @Test
    void testEmptyName(){
        Exception exception = Assert.assertThrows(IllegalArgumentException.class,()->{
            new Unit(initialPosition,"",null, 0,bombs,mines,projectiles, 0);

        });
        Assert.assertEquals("The name must not be empty",exception.getMessage());

    }
    @Test 
    void testSetName(){
        unit.setName("name");
        Assert.assertEquals("Name",unit.getName());
    }

    @Test 
    void testSetNameEmpty(){
        Exception exception = Assert.assertThrows(IllegalArgumentException.class,()->{
            unit.setName("");
        });
        Assert.assertEquals("the name provided must not be empty",exception.getMessage());

    }

    @Test 
    void testMoveRight(){
        unit.move(Direction.RIGHT);
        Assert.assertEquals(1,unit.getPosition().getCol());
    }

    @Test 
    void testMoveLeft(){
        unit.move(Direction.LEFT);
        Assert.assertEquals(-1,unit.getPosition().getCol());
    }

    @Test 
    void testMoveTop(){
        unit.move(Direction.TOP);
        Assert.assertEquals(-1,unit.getPosition().getRow());
    }

    @Test 
    void testMoveBottom(){
        unit.move(Direction.BOTTOM);
        Assert.assertEquals(1,unit.getPosition().getRow());
    }

    @Test 
    void testHasBombs(){
        Assert.assertFalse(unit.hasBombs());
       unit.getBombs().add(new Bomb(initialPosition, 0, null, 0));
        Assert.assertTrue(unit.hasBombs());
    }

    @Test 
    void testHasMines(){
        Assert.assertFalse(unit.hasMines());
        mines.add(new Mine(initialPosition, 0, null));
        Assert.assertTrue(unit.hasMines());
    }

    @Test 
    void testHasProjectiles(){
        Assert.assertFalse(unit.hasProjectiles());
        unit.getProjectiles().add(new Projectile(initialPosition, 0, 0, null));
        Assert.assertTrue(unit.hasProjectiles());
    }
    
}
