package gamePlayers.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gamePlayers.util.Player;
import gamePlayers.util.Position;
import junit.framework.TestCase;

/**
 * Classe de test pour la classe Projectile.
 */
public class ProjectileTest extends TestCase {

    private Projectile projectile1;
    private Projectile projectile2;
    private Position position1;
    private Player player1;

    @Before
    public void setUp() {
        position1 = new Position(1, 2);
        player1 = null;

        projectile1 = new Projectile(position1, 15, 100, player1); // portée 15, dégâts 100
        projectile2 = new Projectile(position1, 10, 80, null); // portée 10, pas de propriétaire
    }

    @Test
    public void testConstructor() {
        Assert.assertNotNull("Le projectile ne devrait pas être null.", projectile1);
        Assert.assertEquals("Les dégâts initiaux du projectile1 devraient être de 100.", 100, projectile1.getDamage());
        Assert.assertEquals("La portée du projectile1 devrait être de 15.", 15, projectile1.getScope());
        Assert.assertEquals("La position du projectile1 devrait être correcte.", position1, projectile1.getPosition());
        Assert.assertEquals("Le propriétaire du projectile1 devrait être 'TestPlayer'.", player1, projectile1.getOwner());

        IllegalArgumentException exception = Assert.assertThrows(IllegalArgumentException.class, () -> {
            new Projectile(position1, -5, 50, player1); // portée invalide
        });
        Assert.assertEquals("Le message d'exception est incorrect.", 
                            "Projectile distance or damage value must be positive", exception.getMessage());
    }

    @Test
    public void testGetScope() {
        Assert.assertEquals("La portée du projectile1 devrait être de 15.", 15, projectile1.getScope());
        Assert.assertEquals("La portée du projectile2 devrait être de 10.", 10, projectile2.getScope());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        Projectile clonedProjectile = projectile1.clone();

        Assert.assertNotSame("Le projectile cloné devrait être une instance différente de l'original.", projectile1, clonedProjectile);
        Assert.assertEquals("Les dégâts du projectile cloné devraient être identiques à l'original.", projectile1.getDamage(), clonedProjectile.getDamage());
        Assert.assertEquals("La portée du projectile cloné devrait être identique à l'originale.", projectile1.getScope(), clonedProjectile.getScope());
        Assert.assertEquals("La position du projectile cloné devrait être identique à l'originale.", projectile1.getPosition(), clonedProjectile.getPosition());
        Assert.assertEquals("Le propriétaire du projectile cloné devrait être identique à l'original.", projectile1.getOwner(), clonedProjectile.getOwner());
    }

    @Test
    public void testSetDamage() {
        projectile1.setDamage(120);
        Assert.assertEquals("Les dégâts du projectile1 devraient être de 120 après modification.", 120, projectile1.getDamage());
    }

    @Test
    public void testOwner() {
        Assert.assertEquals("Le propriétaire du projectile1 devrait être 'TestPlayer'.", player1, projectile1.getOwner());
        Assert.assertNull("Le projectile2 ne devrait pas avoir de propriétaire.", projectile2.getOwner());
    }
}
