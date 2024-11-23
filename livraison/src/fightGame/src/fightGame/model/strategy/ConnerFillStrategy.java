package fightGame.model.strategy;

import java.io.Serializable;
import java.util.*;

import fightGame.UnchangeableSettings;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.objects.Wall;
import gamePlayers.objects.Pellet;
import gamePlayers.util.Position;

public class ConnerFillStrategy implements GameBordInitFillStrategy, Serializable {
    public ConnerFillStrategy() {
    }

    @Override
    public void fillGrid(GameBoard gameBoard) {
        Random random = new Random();
        List<Position> used = new ArrayList<>();
        List<FightGamePlayer> players = gameBoard.getPlayers();

        // Positions des coins
        List<Position> corners = Arrays.asList(
                new Position(0, 0), // Coin haut-gauche
                new Position(UnchangeableSettings.NB_ROWS - 1, UnchangeableSettings.NB_COLS - 1), // Coin bas-droit

                new Position(0, UnchangeableSettings.NB_COLS - 1), // Coin haut-droit
                new Position(UnchangeableSettings.NB_ROWS - 1, 0) // Coin bas-gauche
        );

        // Associer les joueurs aux coins
        if (players.size() <= 4) {
            for (int i = 0; i < players.size(); i++) {
                if (i < corners.size()) {
                    Position position = corners.get(i);
                    used.add(position);
                    FightGamePlayer player = players.get(i);
                    System.out.println(player + " ajouté à la position " + position);
                    player.getUnit().setPosition(position);
                    gameBoard.addEntity(player.getUnit(), position);
                }
            }

        } else {
            int diff = players.size() - 4;
            for (int i = 0; i < 4; i++) {
                Position position = corners.get(i);
                used.add(position);
                FightGamePlayer player = players.get(i);
                //System.out.println(player + " ajouté à la position " + position);
                player.getUnit().setPosition(position);
                gameBoard.addEntity(player.getUnit(), position);
            }
            int i = players.size()-4;
            int j=0;
            do {
                int row = random.nextInt(UnchangeableSettings.NB_ROWS);
                int col = random.nextInt(UnchangeableSettings.NB_COLS);
                Position position = new Position(row, col);
                if (!used.contains(position)) {
                    i--;
                    used.add(position);
                    FightGamePlayer player = players.get(4+j);
                    j++;
                    player.getUnit().setPosition(position);
                    gameBoard.addEntity(player.getUnit(), position);
                }
            } while (i != 0);
        }

        // Ajouter les pellet sur des positions aléatoires
        if (UnchangeableSettings.NB_INIT_PELLET > 0
                && UnchangeableSettings.NB_INIT_PELLET < UnchangeableSettings.NB_ROWS * UnchangeableSettings.NB_COLS)

        {
            int i = UnchangeableSettings.NB_INIT_PELLET;
            do {
                int row = random.nextInt(UnchangeableSettings.NB_ROWS);
                int col = random.nextInt(UnchangeableSettings.NB_COLS);
                Position position = new Position(row, col);
                if (!used.contains(position)) {
                    i--;
                    used.add(position);
                    Pellet pellet = new Pellet(position, UnchangeableSettings.PELLET_BOOST);
                    gameBoard.addEntity(pellet, position);
                    System.out.println(pellet + " ajouté à la position " + position);
                }
            } while (i != 0);
        }

        // Ajouter les murs sur des positions aléatoires
        if (UnchangeableSettings.NB_WALL > 0
                && UnchangeableSettings.NB_WALL < UnchangeableSettings.NB_ROWS * UnchangeableSettings.NB_COLS) {
            int i = UnchangeableSettings.NB_WALL;
            do {
                int row = random.nextInt(UnchangeableSettings.NB_ROWS);
                int col = random.nextInt(UnchangeableSettings.NB_COLS);
                Position position = new Position(row, col);
                if (!used.contains(position)) {
                    i--;
                    used.add(position);
                    Wall wall = new Wall(position);
                    gameBoard.addEntity(wall, position);
                    System.out.println(wall + " ajouté à la position " + position);
                }
            } while (i != 0);
        }
    }
}
