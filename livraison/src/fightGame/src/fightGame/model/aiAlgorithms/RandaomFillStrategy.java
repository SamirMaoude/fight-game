package fightGame.model.aiAlgorithms;

import java.io.Serializable;
import java.util.*;

import fightGame.UnchangeableSettings;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.objects.Wall;
import gamePlayers.objects.Pellet;
import gamePlayers.util.Position;

public class RandaomFillStrategy implements GameBordInitFillStrategy, Serializable{
    public RandaomFillStrategy(){

    }
    @Override
    public void fillGrid(GameBoard gameBoard) {
        Random random = new Random();
        List<Position> used = new ArrayList<>();
        List<FightGamePlayer> players = gameBoard.getPlayers();
        for (FightGamePlayer player : players) {
            int row = random.nextInt(0, UnchangeableSettings.NB_ROWS);
            int col = random.nextInt(0, UnchangeableSettings.NB_COLS);
            Position position = new Position(row, col);
            used.add(position);
            System.out.println(player + " add at position" + position);
            player.getUnit().setPosition(position);
            System.out.println(gameBoard.addEntity(player.getUnit(), position));
        }
        if (UnchangeableSettings.NB_INIT_PELLET > 0 && UnchangeableSettings.NB_INIT_PELLET < UnchangeableSettings.NB_ROWS*UnchangeableSettings.NB_COLS) {
            int i = UnchangeableSettings.NB_INIT_PELLET;
            do {
                int row = random.nextInt(0, UnchangeableSettings.NB_ROWS);
                int col = random.nextInt(0, UnchangeableSettings.NB_COLS);
                Position position = new Position(row, col);
                if (!used.contains(position)) {
                    i--;
                    used.add(position);
                    Pellet pellet = new Pellet(position, UnchangeableSettings.PELLET_BOOST);
                    gameBoard.addEntity(pellet, position);
                    System.out.println(pellet + " add at position" + position);

                }
            } while (i != 0);
        }

        if (UnchangeableSettings.NB_WALL > 0 && UnchangeableSettings.NB_WALL< UnchangeableSettings.NB_ROWS*UnchangeableSettings.NB_COLS) {
            int i = UnchangeableSettings.NB_WALL;
            do {
                int row = random.nextInt(0, UnchangeableSettings.NB_ROWS);
                int col = random.nextInt(0, UnchangeableSettings.NB_COLS);
                Position position = new Position(row, col);
                if (!used.contains(position)) {
                    i--;
                    used.add(position);
                    Wall wall = new Wall(position);
                    gameBoard.addEntity(wall, position);
                    System.out.println(wall + " add at position" + position);
                }
            } while (i != 0);
        }

       

    }

}
