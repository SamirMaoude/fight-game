package gamePlayers.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gamePlayers.util.Position;
import junit.framework.TestCase;


/**
 * Classe de test pour la classe Pallet.
 */
public class PelletTest extends TestCase {

    private Pellet pellet1;
    private Pellet pellet2;
    private Position position1;

    @Before
    public void setUp() {
        position1 = new Position(1, 2);
        pellet1 = new Pellet(position1, 50);
        pellet2 = new Pellet(position1, 30);
    }

    @Test
    public void testConstructor() {
        Assert.assertNotNull("Le pellet ne devrait pas être null. Résultat attendu: un objet Pellet.", pellet1);
        Assert.assertEquals(50, pellet1.getEnergy());
    }

    @Test
    public void testGetEnergy() {
        Assert.assertEquals(50, pellet1.getEnergy());
        Assert.assertEquals(30, pellet2.getEnergy());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        Pellet clonedPellet = pellet1.clone();
        Assert.assertEquals(pellet1.getEnergy(), clonedPellet.getEnergy());
        Assert.assertEquals(pellet1.getPosition(), clonedPellet.getPosition());
    }

    @Test
    public void testDestroy() {
        pellet1.destroy();
        Assert.assertNull("La position du pellet devrait être null après la destruction.", pellet1.getPosition());
    }
}
