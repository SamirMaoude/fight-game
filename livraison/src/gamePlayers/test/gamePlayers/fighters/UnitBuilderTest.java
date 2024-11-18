package gamePlayers.fighters;

import gamePlayers.fighters.Unit;
import gamePlayers.fighters.UnitBuilder;
import gamePlayers.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class UnitBuilderTest {

    private UnitBuilder unitBuilder;

    @BeforeEach
    public void setUp() {
        unitBuilder = new UnitBuilder();
    }

    @Test
    public void testWithPosition() {
        Position position = new Position(10, 15);
        Unit unit = unitBuilder.withPosition(position).build();
        
        assertEquals(position, unit.getPosition(), 
            "Erreur : La position de l'unité ne correspond pas. Résultat attendu : " + position + ", obtenu : " + unit.getPosition());
    }

    @Test
    public void testWithName() {
        String name = "Warrior";
        Unit unit = unitBuilder.withName(name).build();
        
        assertEquals(name, unit.getName(), 
            "Erreur : Le nom de l'unité ne correspond pas. Résultat attendu : " + name + ", obtenu : " + unit.getName());
    }

    @Test
    public void testWithEnergy() {
        int energy = 100;
        Unit unit = unitBuilder.withEnergy(energy).build();
        
        assertEquals(energy, unit.getEnergy(), 
            "Erreur : L'énergie de l'unité ne correspond pas. Résultat attendu : " + energy + ", obtenu : " + unit.getEnergy());
    }

    @Test
    public void testWithBombs() {
        Unit unit = unitBuilder.withBombs().build();
        LinkedList<Bomb> bombs = unit.getBombs();
        
        assertNotNull(bombs, "Erreur : La liste de bombes ne doit pas être nulle.");
        assertEquals(10, bombs.size(), "Erreur : La taille de la liste de bombes est incorrecte. Résultat attendu : 10, obtenu : " + bombs.size());
        
        for (Bomb bomb : bombs) {
            assertEquals(50, bomb.getDamage(), 
                "Erreur : Les dégâts de chaque bombe sont incorrects. Résultat attendu : 50, obtenu : " + bomb.getDamage());
        }
    }

    @Test
    public void testWithMines() {
        Unit unit = unitBuilder.withMines().build();
        LinkedList<Mine> mines = unit.getMines();
        
        assertNotNull(mines, "Erreur : La liste de mines ne doit pas être nulle.");
        assertEquals(10, mines.size(), "Erreur : La taille de la liste de mines est incorrecte. Résultat attendu : 10, obtenu : " + mines.size());
        
        for (Mine mine : mines) {
            assertEquals(50, mine.getDamage(), 
                "Erreur : Les dégâts de chaque mine sont incorrects. Résultat attendu : 50, obtenu : " + mine.getDamage());
        }
    }

    @Test
    public void testWithProjectiles() {
        Unit unit = unitBuilder.withProjectiles().build();
        LinkedList<Projectile> projectiles = unit.getProjectiles();
        
        assertNotNull(projectiles, "Erreur : La liste de projectiles ne doit pas être nulle.");
        assertEquals(10, projectiles.size(), "Erreur : La taille de la liste de projectiles est incorrecte. Résultat attendu : 10, obtenu : " + projectiles.size());
        
        for (Projectile projectile : projectiles) {
            assertEquals(20, projectile.getDamage(), 
                "Erreur : Les dégâts de chaque projectile sont incorrects. Résultat attendu : 20, obtenu : " + projectile.getDamage());
            assertEquals(10, projectile.getScope(), 
                "Erreur : La distance de chaque projectile est incorrecte. Résultat attendu : 10, obtenu : " + projectile.getScope());
        }
    }

    @Test
    public void testBuildWithDefaults() {
        Unit unit = unitBuilder.build();
        
        // Vérifie que chaque attribut non initialisé a sa valeur par défaut
        assertNull(unit.getPosition(), "Erreur : La position par défaut de l'unité devrait être nulle.");
        assertNull(unit.getName(), "Erreur : Le nom par défaut de l'unité devrait être nul.");
        assertEquals(0, unit.getEnergy(), "Erreur : L'énergie par défaut de l'unité est incorrecte. Résultat attendu : 0, obtenu : " + unit.getEnergy());
        assertNull(unit.getBombs(), "Erreur : La liste de bombes par défaut devrait être nulle.");
        assertNull(unit.getMines(), "Erreur : La liste de mines par défaut devrait être nulle.");
        assertNull(unit.getProjectiles(), "Erreur : La liste de projectiles par défaut devrait être nulle.");
    }
}
