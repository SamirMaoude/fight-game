package fightGame.view.widgets;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fightGame.view.InterfaceSetting;


public class GameView extends JFrame{
    private GameButton nextButton;
    private GameButton saveButton;
    private GameBoardTable gameBoardTable;
    private DashBordView dashBordView;

    public GameView(String name, GameBoardTable gameBoardTable, DashBordView dashBordView){
        super(name);
        this.gameBoardTable = gameBoardTable;
        this.dashBordView = dashBordView;
        buildContainer();
        this.setVisible(true);
    }

    private void buildContainer(){
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        this.nextButton = new GameButton("Next", 150, 60);
        this.saveButton = new GameButton("Save", 150, 60);
        this.setSize(InterfaceSetting.WIDTH, InterfaceSetting.HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        southPanel.setSize(InterfaceSetting.WIDTH, 300);
        southPanel.add(this.nextButton);
        southPanel.add(this.saveButton);


       this.dashBordView.setSize(500,500);
      
        JScrollPane scrollPane = new JScrollPane(this.dashBordView);

        container.add(this.gameBoardTable, BorderLayout.WEST);
        container.add(scrollPane, BorderLayout.EAST);

        container.add(southPanel, BorderLayout.SOUTH);
        //this.pack();
    }
}
