package gamePlayers.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import gamePlayers.util.Player;
import gamePlayers.util.Position;
import junit.framework.TestCase;

/**
 * Classe de test pour la classe Mine.
 */

public class MineTest extends TestCase {

    private Mine mine1;
    private Mine mine2;
    private Mine mine3;
    private Position position1;
    private Player player1;

    @Before
    public void setUp() {
        position1 = new Position(3, 4);
        player1 = null;

        mine1 = new Mine(position1, 100, player1);  
        mine2 = new Mine(position1, 50, null);       
        mine3 = new Mine(position1, 0, player1);    
    }

    @Test
public void testConstructor() {
    Assert.assertNotNull("La mine1 ne devrait pas être null.", mine1);
    Assert.assertNotNull("La mine2 ne devrait pas être null.", mine2);
    Assert.assertEquals("Les dégâts de la mine1 devraient être 100.", 100, mine1.getDamage());
    Assert.assertEquals("La position de la mine1 devrait être correcte.", position1, mine1.getPosition());
    Assert.assertEquals("Le propriétaire de la mine1 devrait être 'null'.", player1, mine1.getOwner());
    Assert.assertEquals("Les dégâts de la mine2 devraient être 50.", 50, mine2.getDamage());
    Assert.assertEquals("La position de la mine2 devrait être correcte.", position1, mine2.getPosition());
   
}


    @Test
    public void testGetDamage() {
        Assert.assertEquals("Les dégâts de la mine1 devraient être 100.", 100, mine1.getDamage());
        Assert.assertEquals("Les dégâts de la mine2 devraient être 50.", 50, mine2.getDamage());
    }

    @Test
    public void testSetDamage() {
        mine1.setDamage(120);
        Assert.assertEquals("Les dégâts de la mine1 devraient être 120 après modification.", 120, mine1.getDamage());
    }

    @Test
    public void testUse() {
        try {
            mine1.use();
        } catch (Exception e) {
            Assert.fail("La méthode 'use' ne devrait pas lever d'exception. Exception: " + e.getMessage());
        }
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        Mine clonedMine = mine1.clone();
        Assert.assertEquals("Les dégâts de la mine clonée devraient être identiques à ceux de l'originale.",
                mine1.getDamage(), clonedMine.getDamage());
        Assert.assertEquals("La position de la mine clonée devrait être identique à celle de l'originale.",
                mine1.getPosition(), clonedMine.getPosition());
        Assert.assertEquals("Le propriétaire de la mine clonée devrait être identique à celui de l'originale.",
                mine1.getOwner(), clonedMine.getOwner());

        Assert.assertNotSame("La mine clonée devrait être une instance différente de l'originale.", mine1, clonedMine);
    }
}
