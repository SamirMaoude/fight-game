package fightGame.view.widgets;

import java.util.*;
import javax.swing.*;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.view.InterfaceSetting;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;
/**
 * Represents a dashboard view that displays the state of players in the game board.
 * It listens for changes in the {@link GameBoard} and updates player information dynamically.
 */
public class DashBordView extends JPanel implements ModelListener {
    private GameBoard gameBoard;
    private Map<FightGamePlayer, PlayerView> playersViews;

    /**
     * Constructs a dashboard view for the given game board.
     *
     * @param gameBoard the {@link GameBoard} to monitor and display
     */
    public DashBordView(GameBoard gameBoard) {
        super();
        this.gameBoard = gameBoard;
        this.gameBoard.addModelListener(this);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.playersViews = new HashMap<>();

        JLabel infoLabel = new JLabel("Dashboard");
        infoLabel.setAlignmentX(CENTER_ALIGNMENT);
        infoLabel.setFont(InterfaceSetting.TITLE_FONT);
        this.add(infoLabel);

        // Create player views and add them to the dashboard
        List<FightGamePlayer> players = this.gameBoard.getPlayers();
        for (FightGamePlayer fightGamePlayer : players) {
            PlayerView view = new PlayerView(fightGamePlayer);
            playersViews.put(fightGamePlayer, view);
            this.add(view);
        }
    }

    /**
     * Updates the dashboard view whenever the game board state changes.
     *
     * @param source the {@link ListenableModel} that triggered the update
     */
    @Override
    public void update(ListenableModel source) {
        if (source == gameBoard) {
            for (FightGamePlayer player : gameBoard.getPlayers()) {
                PlayerView view = playersViews.get(player);
                if (view != null) {
                    view.setEnergy(player.getUnit().getEnergy());
                    view.setNbBombLabel(String.valueOf(player.getUnit().getBombs().size()));
                    view.setNbMineLabel(String.valueOf(player.getUnit().getMines().size()));
                    view.setNbProjectilLabel(String.valueOf(player.getUnit().getProjectiles().size()));
                    view.setNbEnergyLabel(String.valueOf(player.getUnit().getEnergy()));

                }
            }
        }
        this.revalidate();
        this.repaint();
    }
}
