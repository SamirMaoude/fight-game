package fightGame.view;

import java.util.List;

import javax.swing.*;
import fightGame.model.FightGamePlayer  ;
import fightGame.model.GameBoard;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;

public class DashBordView extends JPanel implements ModelListener{
    private GameBoard gameBoard;

    public DashBordView(GameBoard gameBoard){
        super();
        this.gameBoard = gameBoard;
        this.gameBoard.addModelListerner(this);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        JLabel infoLabel = new JLabel("Dashboard");
        infoLabel.setFont(InterfaceSetting.TEXT_FONT);
        this.add(infoLabel);
        List<FightGamePlayer> players = this.gameBoard.getPlayers();
        for (FightGamePlayer fightGamePlayer : players) {
            PlayerView view = new PlayerView(fightGamePlayer.getUnit().getName(), 100, fightGamePlayer.getUnit().getEnergy());
            this.add(view);

        }
    }
    @Override
    public void update(ListenableModel source) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
