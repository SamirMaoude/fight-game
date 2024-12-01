package fightGame.model.strategy;

import java.io.Serializable;
import java.util.*;

import fightGame.UnchangeableSettings;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.objects.Wall;
import gamePlayers.objects.Pellet;
import gamePlayers.util.Position;
/**
 * Implementation of {@link GameBordInitFillStrategy} that randomly populates the game board with players, pellets, and walls.
 * Ensures no two entities occupy the same position.
 */
public class RandaomFillStrategy implements GameBordInitFillStrategy, Serializable {

    /**
     * Default constructor.
     */
    public RandaomFillStrategy() {
    }

    /**
     * Fills the game board with players, pellets, and walls at random positions.
     * Ensures each entity is placed on a unique, valid position.
     *
     * @param gameBoard the {@link GameBoard} to be filled
     */
    @Override
    public void fillGrid(GameBoard gameBoard) {
        Random random = new Random();
        List<Position> used = new ArrayList<>();
        List<FightGamePlayer> players = gameBoard.getPlayers();
        int nbPlayers = players.size();

        // Assigns random starting positions for players.
        if (nbPlayers > 0 && nbPlayers < UnchangeableSettings.NB_ROWS * UnchangeableSettings.NB_COLS + 1) {
            int i = nbPlayers;
            do {
                int row = random.nextInt(UnchangeableSettings.NB_ROWS);
                int col = random.nextInt(UnchangeableSettings.NB_COLS);
                Position position = new Position(row, col);
                if (!used.contains(position)) {
                    i--;
                    used.add(position);
                }
            } while (i != 0);
        }

        // Assigns players to their respective starting positions.
        int i = 0;
        for (FightGamePlayer player : players) {
            Position position = used.get(i++);
            player.getUnit().setPosition(position);
            gameBoard.addEntity(player.getUnit(), position);
        }

        // Adds pellets at random positions.
        if (UnchangeableSettings.NB_INIT_PELLET > 0 &&
            UnchangeableSettings.NB_INIT_PELLET < UnchangeableSettings.NB_ROWS * UnchangeableSettings.NB_COLS) {
            i = UnchangeableSettings.NB_INIT_PELLET;
            do {
                int row = random.nextInt(UnchangeableSettings.NB_ROWS);
                int col = random.nextInt(UnchangeableSettings.NB_COLS);
                Position position = new Position(row, col);
                if (!used.contains(position)) {
                    i--;
                    used.add(position);
                    Pellet pellet = new Pellet(position, UnchangeableSettings.PELLET_BOOST);
                    gameBoard.addEntity(pellet, position);
                }
            } while (i != 0);
        }

        // Adds walls at random positions.
        if (UnchangeableSettings.NB_WALL > 0 &&
            UnchangeableSettings.NB_WALL < UnchangeableSettings.NB_ROWS * UnchangeableSettings.NB_COLS) {
            i = UnchangeableSettings.NB_WALL;
            do {
                int row = random.nextInt(UnchangeableSettings.NB_ROWS);
                int col = random.nextInt(UnchangeableSettings.NB_COLS);
                Position position = new Position(row, col);
                if (!used.contains(position)) {
                    i--;
                    used.add(position);
                    Wall wall = new Wall(position);
                    gameBoard.addEntity(wall, position);
                }
            } while (i != 0);
        }
    }
}
