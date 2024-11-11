package gamePlayers.objects;

import gamePlayers.board.GameBoard;
import gamePlayers.entities.GameEntity;
import gamePlayers.entities.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {

    private GameBoard gameBoard;
    private List<Player> players;

    @BeforeEach
    public void setUp() {
        players = new ArrayList<>();
        gameBoard = new GameBoard(10, 10, players);
    }

    @Test
    public void testAddEntity() {
        GameEntity entityValid = new GameEntity(); // Assurez-vous que GameEntity a un constructeur approprié
        GameEntity entityOutOfBounds = new GameEntity();
        GameEntity entityNegative = new GameEntity();

        // Test de l'ajout d'une entité à une position valide
        Point validPosition = new Point(5, 5);
        boolean validResult = gameBoard.addEntity(entityValid, validPosition);
        assertTrue(validResult, "Erreur : l'ajout de l'entité dans les limites du plateau a échoué.");
        assertTrue(gameBoard.getEntities().containsKey(validPosition),
                "Erreur : l'entité n'est pas présente à la position spécifiée dans le plateau.");
        assertEquals(entityValid, gameBoard.getEntities().get(validPosition),
                "Erreur : l'entité ajoutée n'est pas correcte. Résultat attendu : " + entityValid + ", obtenu : " + gameBoard.getEntities().get(validPosition));

        // Test de l'ajout d'une entité hors des limites
        Point outOfBoundsPosition = new Point(15, 15);
        boolean outOfBoundsResult = gameBoard.addEntity(entityOutOfBounds, outOfBoundsPosition);
        assertFalse(outOfBoundsResult, "Erreur : l'ajout d'une entité hors des limites aurait dû échouer.");
        assertFalse(gameBoard.getEntities().containsKey(outOfBoundsPosition),
                "Erreur : l'entité hors des limites est présente dans le plateau alors qu'elle ne devrait pas.");

        // Test de l'ajout d'une entité à une position négative
        Point negativePosition = new Point(-1, -1);
        boolean negativeResult = gameBoard.addEntity(entityNegative, negativePosition);
        assertFalse(negativeResult, "Erreur : l'ajout d'une entité à une position négative aurait dû échouer.");
        assertFalse(gameBoard.getEntities().containsKey(negativePosition),
                "Erreur : l'entité est présente à une position négative alors qu'elle ne devrait pas.");
    }
}

