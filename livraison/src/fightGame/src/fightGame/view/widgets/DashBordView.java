package fightGame.view.widgets;

import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        this.gameBoard.addModelListerner(this);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.playersViews = new HashMap<>();
        JLabel infoLabel = new JLabel("Dashboard");
        infoLabel.setAlignmentX(CENTER_ALIGNMENT);
        infoLabel.setFont(InterfaceSetting.TITLE_FONT);
        this.add(infoLabel);
        List<FightGamePlayer> players = this.gameBoard.getPlayers();
        for (FightGamePlayer fightGamePlayer : players) {
            PlayerView view = new PlayerView(fightGamePlayer.getUnit().getName(), 100,
                    fightGamePlayer.getUnit().getEnergy());
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
                }
            }
        }
        this.revalidate();
        this.repaint();
    }

}
