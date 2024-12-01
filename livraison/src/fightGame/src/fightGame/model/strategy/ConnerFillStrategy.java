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
 * Implementation of {@link GameBordInitFillStrategy} that initializes the game board by placing players in corners,
 * and randomly positions pellets and walls on the grid.
 */
public class ConnerFillStrategy implements GameBordInitFillStrategy, Serializable {

    /**
     * Default constructor.
     */
    public ConnerFillStrategy() {
    }

    /**
     * Fills the game board by placing players in corners or random positions and adds pellets and walls at random locations.
     *
     * @param gameBoard the {@link GameBoard} to be filled
     */
    @Override
    public void fillGrid(GameBoard gameBoard) {
        Random random = new Random();
        List<Position> used = new ArrayList<>();
        List<FightGamePlayer> players = gameBoard.getPlayers();

        // Define corner positions
        List<Position> corners = Arrays.asList(
            new Position(0, 0), 
            new Position(UnchangeableSettings.NB_ROWS - 1, UnchangeableSettings.NB_COLS - 1), 
            new Position(0, UnchangeableSettings.NB_COLS - 1), 
            new Position(UnchangeableSettings.NB_ROWS - 1, 0)
        );

        // Place players in corners or random positions
        if (players.size() <= 4) {
            for (int i = 0; i < players.size(); i++) {
                Position position = corners.get(i);
                used.add(position);
                FightGamePlayer player = players.get(i);
                player.getUnit().setPosition(position);
                gameBoard.addEntity(player.getUnit(), position);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                Position position = corners.get(i);
                used.add(position);
                FightGamePlayer player = players.get(i);
                player.getUnit().setPosition(position);
                gameBoard.addEntity(player.getUnit(), position);
            }

            int remainingPlayers = players.size() - 4;
            int index = 0;

            while (remainingPlayers > 0) {
                int row = random.nextInt(UnchangeableSettings.NB_ROWS);
                int col = random.nextInt(UnchangeableSettings.NB_COLS);
                Position position = new Position(row, col);

                if (!used.contains(position)) {
                    remainingPlayers--;
                    used.add(position);
                    FightGamePlayer player = players.get(4 + index++);
                    player.getUnit().setPosition(position);
                    gameBoard.addEntity(player.getUnit(), position);
                }
            }
        }

        // Randomly place pellets
        if (UnchangeableSettings.NB_INIT_PELLET > 0 && 
            UnchangeableSettings.NB_INIT_PELLET < UnchangeableSettings.NB_ROWS * UnchangeableSettings.NB_COLS) {
            int remainingPellets = UnchangeableSettings.NB_INIT_PELLET;

            while (remainingPellets > 0) {
                int row = random.nextInt(UnchangeableSettings.NB_ROWS);
                int col = random.nextInt(UnchangeableSettings.NB_COLS);
                Position position = new Position(row, col);

                if (!used.contains(position)) {
                    remainingPellets--;
                    used.add(position);
                    Pellet pellet = new Pellet(position, UnchangeableSettings.PELLET_BOOST);
                    gameBoard.addEntity(pellet, position);
                }
            }
        }

        // Randomly place walls
        if (UnchangeableSettings.NB_WALL > 0 && 
            UnchangeableSettings.NB_WALL < UnchangeableSettings.NB_ROWS * UnchangeableSettings.NB_COLS) {
            int remainingWalls = UnchangeableSettings.NB_WALL;

            while (remainingWalls > 0) {
                int row = random.nextInt(UnchangeableSettings.NB_ROWS);
                int col = random.nextInt(UnchangeableSettings.NB_COLS);
                Position position = new Position(row, col);

                if (!used.contains(position)) {
                    remainingWalls--;
                    used.add(position);
                    Wall wall = new Wall(position);
                    gameBoard.addEntity(wall, position);
                }
            }
        }
    }
}
