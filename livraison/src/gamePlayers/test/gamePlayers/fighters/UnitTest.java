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
import junit.framework.TestCase;

public class UnitTest extends TestCase {

    private Unit unit;
    private Position initialPosition;
    private LinkedList<Bomb> bombs;
    private LinkedList<Mine> mines;
    private LinkedList<Projectile> projectiles;

    @Before
    public void setUp() {
        initialPosition = new Position(0, 0);
        bombs = new LinkedList<>();
        mines = new LinkedList<>();
        projectiles = new LinkedList<>();
        unit = new Unit(initialPosition, "warrior", null, 100, bombs, mines, projectiles, 50);
    }

    @Test
    public void testConstructor() {
        Assert.assertEquals("Warrior", unit.getName());
        Assert.assertEquals(100, unit.getEnergy());
        Assert.assertEquals(initialPosition, unit.getPosition());
        Assert.assertEquals(projectiles, unit.getProjectiles());
        Assert.assertEquals(mines, unit.getMines());
        Assert.assertEquals(bombs, unit.getBombs());
        Assert.assertEquals(50, unit.getShieldRetention());

        Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> {
            new Unit(initialPosition, "", null, 0, bombs, mines, projectiles, 0);

        });
        Assert.assertEquals("You cannot provide an empty string as unit name!", exception.getMessage());

    }

    @Test
    public void testReceiveDamage() {
        unit.receiveDamage(20);
        Assert.assertFalse(unit.getEnergy() == 100);
        Assert.assertEquals("La méthode ne fait pas dimunier l'énergie du joueurs", 80, unit.getEnergy());
        unit.receiveDamage(-45);
        Assert.assertTrue(unit.getEnergy() == 80);
        Assert.assertEquals("La méthode ne devrait pas modifier l'energie quant damage est négatif", 80,
                unit.getEnergy());

    }

    @Test
    public void testSetShieldActivated(){
        Assert.assertEquals(unit.isShieldActivated(),false);
        unit.setShieldActivated(true);
        Assert.assertEquals(unit.isShieldActivated(),true);
        unit.setShieldActivated(false);
        Assert.assertEquals(unit.isShieldActivated(),false);

    }

    @Test
    public void testSetName() {
        unit.setName("name");
        Assert.assertEquals("Name", unit.getName());

        Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> {
            unit.setName("");
        });
        Assert.assertEquals("You cannot provide an empty string as unit name!", exception.getMessage());

        unit.setName("uunit");
        Assert.assertEquals("Uunit", unit.getName());
    }

    @Test

    public void testIsShieldActivated() {
        Assert.assertEquals("L'armure de unit ne doit pas être activé par défaut à sa création", false,
                unit.isShieldActivated());
    }


    @Test
    public void testMove() {
        unit.move(Direction.RIGHT);
        Assert.assertEquals(1, unit.getPosition().getCol());
    
        unit.move(Direction.LEFT);
        Assert.assertEquals(0, unit.getPosition().getCol());
  
        unit.move(Direction.TOP);
        Assert.assertEquals(-1, unit.getPosition().getRow());
   
        unit.move(Direction.BOTTOM);
        Assert.assertEquals(0, unit.getPosition().getRow());
    }

    @Test
    public void testHasBombs() {
        Assert.assertFalse(unit.hasBombs());
        unit.getBombs().add(new Bomb(initialPosition, 10, null, 4));
        Assert.assertTrue(unit.hasBombs());
        Assert.assertEquals(1, unit.getBombs().size());
        unit.getBombs().add(new Bomb(initialPosition, 20, null, 10));
        unit.getBombs().add(new Bomb(initialPosition, 30, null, 5));
        Assert.assertTrue(unit.hasBombs());
        Assert.assertEquals(3, unit.getBombs().size());
        bombs.clear();
        Assert.assertFalse("Le unit ne doit plus disposer de bombs quand ça liste de bombs est vide", unit.hasBombs()==true);
        Assert.assertEquals(0, unit.getBombs().size());


    }

    @Test
    public void testHasMines() {
        Assert.assertFalse(unit.hasMines());
        mines.add(new Mine(initialPosition, 10, null));
        Assert.assertTrue(unit.hasMines());
        Assert.assertEquals(1, unit.getMines().size());
        mines.add(new Mine(null, 10, null));
        mines.add(new Mine(null, 10, null));
        mines.add(new Mine(null, 10, null));
        Assert.assertTrue(unit.hasMines());
        Assert.assertEquals(4, unit.getMines().size());
        mines.clear();
        Assert.assertFalse("Le unit ne doit plus disposer de mines quand ça liste de mines est vide", unit.hasMines()==true);
        Assert.assertEquals(0, unit.getMines().size());

    }

    @Test
    public void testHasProjectiles() {
        Assert.assertFalse(unit.hasProjectiles());
        projectiles.add(new Projectile(initialPosition, 5, 15, null));
        Assert.assertTrue(unit.hasProjectiles());
        Assert.assertEquals(1, unit.getProjectiles().size());
        projectiles.add(new Projectile(initialPosition, 15, 12, null));
        projectiles.add(new Projectile(initialPosition, 15, 12, null));
        Assert.assertTrue(unit.hasProjectiles());
        Assert.assertEquals(3, unit.getProjectiles().size());
        projectiles.clear();
        Assert.assertTrue("Le unit ne doit plus disposer de projectiles quand ça liste de projectile est vide", unit.hasProjectiles()==false);
        Assert.assertEquals(0, unit.getProjectiles().size());

    }

}
