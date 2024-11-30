package fightGame.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fightGame.UnchangeableSettings;
import fightGame.model.GameBoard;
import fightGame.model.objects.Wall;
import fightGame.model.strategy.ConnerFillStrategy;
import gamePlayers.AbstractGameEntity;
import gamePlayers.fighters.Unit;
import gamePlayers.util.Action;
import gamePlayers.util.Direction;
import gamePlayers.util.EntityType;
import gamePlayers.util.Position;

import junit.framework.TestCase;

public class GameBoardTest extends TestCase {
    private GameBoard gameBoard;
    private List<FightGamePlayer> players;
    private Wall wall;

    public void setUp() {

        UnchangeableSettings.loadSettings("gameSettings.xml");

        // Initialisation d'un plateau de 10x10 pour chaque test
        gameBoard = new GameBoard(UnchangeableSettings.NB_ROWS, UnchangeableSettings.NB_COLS, players);

        players = new ArrayList<>();

        players.add(new FightGamePlayer(gameBoard, "player 0", 0));
        players.add(new FightGamePlayer(gameBoard, "player 1", 1));
        
        wall = new Wall(new Position(0,0));
    }


    @Test
    public void testAddEntity() {

        FightGamePlayer player1 = players.get(0);
        Unit unit1 = player1.getUnit();
        assertTrue(gameBoard.addEntity(unit1, new Position(1, 1)));

        for (AbstractGameEntity entity : gameBoard.getEntitiesAt(new Position(1, 1))) {
            if(entity.getType() == EntityType.UNIT) assertTrue(unit1.equals(entity));
        }
        
    }

    @Test
    public void testMoveUnit() {
        
        // Undefined unit
        assertFalse(gameBoard.moveUnit(new Position(1,1), Direction.LEFT));

        
        FightGamePlayer player1 = players.get(0);
        Unit unit1 = player1.getUnit();
        assertTrue(gameBoard.addEntity(unit1, new Position(0, 0)));

        // Invalid Move To left
        assertFalse(gameBoard.moveUnit(new Position(unit1.getPosition()), Direction.LEFT));


        // Valid Move To Right
        assertTrue(gameBoard.moveUnit(new Position(unit1.getPosition()), Direction.RIGHT));

        // valid Move to left
        assertTrue(gameBoard.moveUnit(new Position(unit1.getPosition()), Direction.LEFT));

        // Invalid Move to Top
        assertFalse(gameBoard.moveUnit(new Position(unit1.getPosition()), Direction.TOP));

        
        // Valid Move to bottom
        assertTrue(gameBoard.moveUnit(new Position(unit1.getPosition()), Direction.BOTTOM));

        // Valid Move to Top
        assertTrue(gameBoard.moveUnit(new Position(unit1.getPosition()), Direction.TOP));

        // Invalid Move to bottom because of wall
        Position wallPos = new Position(unit1.getPosition());
        wallPos.moveBottom();
        assertTrue(gameBoard.addEntity(wall, wallPos));
        assertFalse(gameBoard.moveUnit(unit1.getPosition(), Direction.BOTTOM));

        // Invalid Move to right because of another unit
        FightGamePlayer player2 = players.get(1);
        Unit unit2 = player2.getUnit();
        assertTrue(gameBoard.addEntity(unit2, new Position(0, 1)));
        assertFalse(gameBoard.moveUnit(new Position(unit1.getPosition()), Direction.RIGHT));


    }

    @Test
    public void testIsValidPosition(){
        Position position = new Position(0, 0);
        
        assertTrue(gameBoard.isValidPosition(position));

        wall.setPosition(new Position(0, 1));
        gameBoard.addEntity(wall, new Position(0, 1));
        assertFalse(gameBoard.isValidPosition(new Position(0, 1)));


    }

    

    
}