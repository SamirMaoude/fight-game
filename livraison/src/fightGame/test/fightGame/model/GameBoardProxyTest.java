package fightGame.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import fightGame.UnchangeableSettings;
import fightGame.model.objects.Wall;
import gamePlayers.AbstractGameEntity;
import gamePlayers.fighters.Unit;
import gamePlayers.util.EntityType;
import gamePlayers.util.Position;
import junit.framework.TestCase;

public class GameBoardProxyTest extends TestCase {

    private GameBoard gameBoard;
    private List<FightGamePlayer> players;
    private Wall wall;

    public void setUp() {

        UnchangeableSettings.loadSettings(null);

        // Initialisation d'un plateau pour chaque test
        gameBoard = new GameBoard(UnchangeableSettings.NB_ROWS, UnchangeableSettings.NB_COLS, players);

        players = new ArrayList<>();

        gameBoard.setPlayers(players);

        FightGamePlayer player1 = new FightGamePlayer(gameBoard, "player 0", 0);
        players.add(player1);
        FightGamePlayer player2 = new FightGamePlayer(gameBoard, "player 1", 1);
        players.add(player2);
        
        Unit unit1 = player1.getUnit();
        Unit unit2 = player2.getUnit();


        gameBoard.addEntity(unit1, new Position(0,0));

        gameBoard.addEntity(unit2, new Position(UnchangeableSettings.NB_ROWS - 1, UnchangeableSettings.NB_COLS - 1));

    }


    @Test
    public void testBombVisibility(){

        FightGamePlayer nextPlayer = gameBoard.getNextPlayer();

        Unit unit = nextPlayer.getUnit();
        Position position = unit.getPosition();

        FightGameAction action = new FightGameAction(FightGameActionType.USE_BOMB_AT_BOTTOM);

        gameBoard.performAction(action, nextPlayer);

        Position newPosition = new Position(position);
        newPosition.moveBottom();

        Set<AbstractGameEntity> entities = nextPlayer.getGameBoardProxy().getEntitiesAt(newPosition);
        
        boolean isBombSeen = false;

        for(AbstractGameEntity entity: entities){
            if(entity.getType() == EntityType.BOMB){
                isBombSeen = true;
                break;
            }
        }

        assertTrue(isBombSeen);

        nextPlayer = gameBoard.getNextPlayer();
        entities = nextPlayer.getGameBoardProxy().getEntitiesAt(newPosition);
        isBombSeen = false;

        for(AbstractGameEntity entity: entities){
            if(entity.getType() == EntityType.BOMB){
                isBombSeen = true;
                break;
            }
        }

        assertFalse(UnchangeableSettings.BOMB_VISIBILITY? isBombSeen == false : isBombSeen == true);
        
    }


    @Test
    public void testMineVisibility(){

        FightGamePlayer nextPlayer = gameBoard.getNextPlayer();

        Unit unit = nextPlayer.getUnit();
        Position position = unit.getPosition();

        FightGameAction action = new FightGameAction(FightGameActionType.USE_MINE_AT_BOTTOM);

        gameBoard.performAction(action, nextPlayer);

        Position newPosition = new Position(position);
        newPosition.moveBottom();

        Set<AbstractGameEntity> entities = nextPlayer.getGameBoardProxy().getEntitiesAt(newPosition);
        
        boolean isBombSeen = false;

        for(AbstractGameEntity entity: entities){
            if(entity.getType() == EntityType.MINE){
                isBombSeen = true;
                break;
            }
        }

        assertTrue(isBombSeen);

        nextPlayer = gameBoard.getNextPlayer();
        entities = nextPlayer.getGameBoardProxy().getEntitiesAt(newPosition);
        isBombSeen = false;

        for(AbstractGameEntity entity: entities){
            if(entity.getType() == EntityType.MINE){
                isBombSeen = true;
                break;
            }
        }

        assertFalse(UnchangeableSettings.MINE_VISIBILITY? isBombSeen == false : isBombSeen == true);
        
    }

}
