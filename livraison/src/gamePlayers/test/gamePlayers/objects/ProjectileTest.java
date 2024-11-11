package gamePlayers.objects;

import gamePlayers.util.Position;
import gamePlayers.util.EntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectileTest {

    private Projectile projectile;
    private Position position;

    @BeforeEach
    public void setUp() {
        position = new Position(10, 20); 
        projectile = new Projectile(position, 100, 50); 
    }
    @Test
    public void testProjectileAndPosition() {
        assertNotNull(projectile, "Le projectile ne doit pas être nul après la création.");

        assertEquals(EntityType.PROJECTILE, projectile.getType(),
                "Erreur : Le type d'entité du projectile est incorrect. Résultat attendu : PROJECTILE, obtenu : " + projectile.getType());

        assertEquals(position, projectile.getPosition(),
                "Erreur : La position du projectile est incorrecte. Résultat attendu : " + position + ", obtenu : " + projectile.getPosition());

        assertEquals(50, projectile.getDamage(),
                "Erreur : Les dégâts du projectile sont incorrects. Résultat attendu : 50, obtenu : " + projectile.getDamage());

        assertEquals(100, projectile.getScope(),
                "Erreur : La distance du projectile est incorrecte. Résultat attendu : 100, obtenu : " + projectile.getScope());

        Exception negativeDistanceException = assertThrows(IllegalArgumentException.class, () -> {
            new Projectile(position, -10, 50); 
        });
        assertEquals("Projectile distance or damage value must be positive", negativeDistanceException.getMessage());

        Exception zeroDistanceException = assertThrows(IllegalArgumentException.class, () -> {
            new Projectile(position, 0, 50); 
        });
        assertEquals("Projectile distance or damage value must be positive", zeroDistanceException.getMessage());


        Exception negativeDamageException = assertThrows(IllegalArgumentException.class, () -> {
            new Projectile(position, 100, -5);
        });
        assertEquals("Projectile distance or damage value must be positive", negativeDamageException.getMessage());
    }

    @Test
    public void testGetDamage() {
        assertEquals(50, projectile.getDamage(),
                "Erreur : Les dégâts du projectile ne correspondent pas. Résultat attendu : 50, obtenu : " + projectile.getDamage());
    }

    @Test
    public void testGetPosition() {
        assertEquals(position, projectile.getPosition(),
                "Erreur : La position du projectile ne correspond pas. Résultat attendu : " + position + ", obtenu : " + projectile.getPosition());
    }

    @Test
    public void testGetDistance() {
        assertEquals(100, projectile.getScope(),
                "Erreur : La distance du projectile ne correspond pas. Résultat attendu : 100, obtenu : " + projectile.getScope());
    }

    @Test
    public void testGetEntityType() {
        assertEquals(EntityType.PROJECTILE, projectile.getType(),
                "Erreur : Le type d'entité du projectile ne correspond pas. Résultat attendu : PROJECTILE, obtenu : " + projectile.getType());
    }

}
