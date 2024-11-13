package fightGame.view.widgets;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fightGame.view.InterfaceSetting;
import fightGame.view.PlayerView;


public class GameView extends JFrame{
    private GameButton nextButton;
    private GameButton saveButton;
    private GameBoardTable gameBoardTable;

    public GameView(String name, GameBoardTable gameBoardTable){
        super(name);
        this.gameBoardTable = gameBoardTable;
        this.nextButton = new GameButton("Next", 300, 200);
        this.saveButton = new GameButton("Save", 300, 200);
        this.setSize(InterfaceSetting.WIDTH, InterfaceSetting.HEIGHT);
        this.setResizable(false);
        buildContainer();
        this.setVisible(true);
    }

    private void buildContainer(){
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        southPanel.setSize(InterfaceSetting.WIDTH, 300);
        southPanel.add(this.nextButton);
        southPanel.add(this.saveButton);


        JPanel playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.Y_AXIS));


        JLabel infoLabel = new JLabel("Infos about players");
        infoLabel.setFont(InterfaceSetting.TEXT_FONT);
        playerInfoPanel.add(infoLabel);
        for(int i=0; i<10; i++){
            PlayerView player = new PlayerView("Player "+i, 100, i*4+50);
            playerInfoPanel.add(player);
        }
        
        JScrollPane scrollPane = new JScrollPane(playerInfoPanel);

        container.add(this.gameBoardTable, BorderLayout.WEST);
        container.add(scrollPane, BorderLayout.EAST);

        container.add(southPanel, BorderLayout.SOUTH);
        //this.pack();
    }
}
