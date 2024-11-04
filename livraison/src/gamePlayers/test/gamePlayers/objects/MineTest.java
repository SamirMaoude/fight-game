package gamePlayers.objects;

import gamePlayers.util.EntityType;
import gamePlayers.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MineTest {

    private Mine mine;
    private Position position;

    @BeforeEach
    public void setUp() {
        position = new Position(10, 20); 
        mine = new Mine(position, 50); 
    }

    @Test
    public void testMineAndPosition() {
        assertNotNull(mine, "La mine ne doit pas être nulle après la création.");
        
        assertEquals(EntityType.MINE, mine.getType(),
                "Erreur : Le type d'entité de la mine est incorrect. Résultat attendu : MINE, obtenu : " + mine.getType());
        
        assertEquals(position, mine.getPosition(),
                "Erreur : La position de la mine est incorrecte. Résultat attendu : " + position + ", obtenu : " + mine.getPosition());
        
        assertEquals(50, mine.getDamage(),
                "Erreur : Les dégâts de la mine sont incorrects. Résultat attendu : 50, obtenu : " + mine.getDamage());

        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Mine(position, -15);
        });
        assertEquals("Les dégâts doivent être positifs.", exception.getMessage());
        
        Exception invalidPositionException = assertThrows(IllegalArgumentException.class, () -> {
            new Position(-5, -10);
        });
        assertEquals("Les coordonnées de la position doivent être non négatives.", invalidPositionException.getMessage());
    }

    @Test
    public void testGetDamage() {
        assertEquals(50, mine.getDamage(), 
                "Erreur : Les dégâts de la mine sont incorrects. Résultat attendu : 50, obtenu : " + mine.getDamage());
    }

    @Test
    public void testGetPosition() {
        assertEquals(position, mine.getPosition(), 
                "Erreur : La position de la mine est incorrecte. Résultat attendu : " + position + ", obtenu : " + mine.getPosition());
    }

    @Test
    public void testGetEntityType() {
        assertEquals(EntityType.MINE, mine.getType(), 
                "Erreur : Le type d'entité de la mine est incorrect. Résultat attendu : MINE, obtenu : " + mine.getType());
    }
}
