package fightGame.view.widgets;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fightGame.controller.GameBoardAdapterToTable;
import fightGame.model.GameBoard;
import fightGame.model.GameBoardProxy;
import fightGame.view.InterfaceSetting;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;


public class GameView extends JFrame implements ModelListener{
    private GameButton nextButton;
    private GameButton saveButton;
    private GameBoard gameBoard;
    private GameBoardTable gameBoardTable;
    private DashBordView dashBordView;
    private GameBoardProxy proxy;
    

    public GameView(String name, GameBoard gameBoard, GameBoardProxy proxy){
        super(name);
        this.gameBoard = gameBoard;
        this.proxy = proxy;
        this.gameBoard.addModelListerner(this);
        GameBoardAdapterToTable gameBoardAdapterToTable = new GameBoardAdapterToTable(gameBoard,proxy);
        this.gameBoardTable = new GameBoardTable(gameBoardAdapterToTable);
        this.dashBordView = new DashBordView(gameBoard);
        buildContainer();
        this.setVisible(true);
        //gameBoard.run();
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
        this.pack();
    }

    @Override
    public void update(ListenableModel source) {
        this.revalidate();
        this.repaint();
    }
}
