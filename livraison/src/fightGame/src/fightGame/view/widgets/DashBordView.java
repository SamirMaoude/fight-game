package fightGame.view.widgets;

import java.util.*;
import javax.swing.*;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.view.InterfaceSetting;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;

public class DashBordView extends JPanel implements ModelListener {
    private GameBoard gameBoard;
    Map<FightGamePlayer, PlayerView> playersViews;

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

        List<FightGamePlayer> players = this.gameBoard.getPlayers();
        for (FightGamePlayer fightGamePlayer : players) {
            PlayerView view = new PlayerView(fightGamePlayer);
            playersViews.put(fightGamePlayer, view);
            this.add(view);
        }
    }

    @Override
    public void update(ListenableModel source) {
        if (source == gameBoard) {
            for (FightGamePlayer player : gameBoard.getPlayers()) {
                PlayerView view = playersViews.get(player);
                if (view != null) {
                    view.setEnergy(player.getUnit().getEnergy()); // Mise à jour de l'énergie
                    view.setNbBombLabel(String.valueOf(player.getUnit().getBombs().size()));
                    view.setNbMineLabel(String.valueOf(player.getUnit().getMines().size()));
                    view.setNbProjectilLabel(String.valueOf(player.getUnit().getProjectiles().size()));
                }
            }
        }
        this.revalidate();
        this.repaint();
    }

}
