package fightGame.model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import fightGame.model.GameBoard;
import gamePlayers.AbstractGameEntity;
import gamePlayers.fighters.Unit;
import gamePlayers.util.EntityType;
import gamePlayers.util.Position;


public class GameBoardTest extends TestCase {
    private GameBoard gameBoard;
    private List<FightGamePlayer> players;

    @Before
    protected void setUp() {
        // Initialisation d'un plateau de 10x10 pour chaque test
        gameBoard = new GameBoard(10, 10, players);

        players = new ArrayList<>();

        players.add(new FightGamePlayer(gameBoard, 0));
        players.add(new FightGamePlayer(gameBoard, 1));
        

    }


    @Test
    void testAddEntity() {

        FightGamePlayer player1 = players.get(0);
        Unit unit1 = player1.getUnit();
        gameBoard.addEntity(unit1, new Position(1, 1));

        for (AbstractGameEntity entity : gameBoard.getEntitiesAt(new Position(1, 1))) {
            if(entity.getType() == EntityType.UNIT) assertEquals(unit1, entity);
        }
        
    }

}