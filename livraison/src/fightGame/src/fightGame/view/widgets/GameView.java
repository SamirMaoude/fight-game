package fightGame.view.widgets;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import fightGame.controller.GameBoardAdapterToTable;
import fightGame.model.GameBoard;
import fightGame.model.GameBoardProxy;
import fightGame.view.InterfaceSetting;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;

public class GameView extends JFrame implements ModelListener, ActionListener {
    private GameButton nextButton;
    private GameButton saveButton;
    private GameBoard gameBoard;
    private GameBoardTable gameBoardTable;
    private DashBordView dashBordView;
    private GameBoardProxy proxy;

    public GameView(String name, GameBoard gameBoard, GameBoardProxy proxy) {
        super(name);
        this.gameBoard = gameBoard;
        this.proxy = proxy;
        this.gameBoard.addModelListerner(this);
        GameBoardAdapterToTable gameBoardAdapterToTable = new GameBoardAdapterToTable(gameBoard, this.proxy);
        this.gameBoardTable = new GameBoardTable(gameBoardAdapterToTable);
        this.dashBordView = new DashBordView(gameBoard);

        buildContainer();
    }

    private void buildContainer() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        this.nextButton = new GameButton("Next", 150, 60);
        this.saveButton = new GameButton("Save", 150, 60);
        this.nextButton.addActionListener(this);
        this.saveButton.addActionListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        southPanel.setSize(InterfaceSetting.WIDTH, 300);
        southPanel.add(this.nextButton);
        southPanel.add(this.saveButton);

        this.dashBordView.setSize(500, 500);

        JScrollPane scrollPane = new JScrollPane(this.dashBordView);

        container.add(this.gameBoardTable, BorderLayout.WEST);
        container.add(scrollPane, BorderLayout.EAST);

        container.add(southPanel, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);

    }

    @Override
    public void update(ListenableModel source) {
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.saveButton)) {
            this.saveGame();
        }
    }

    private void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sauvegarder la partie");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            try (FileOutputStream fileOut = new FileOutputStream(filePath);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(this.gameBoard);
                new InfosView(this, "Sauvegarde", "Votre jeu a été bien sauvegardé dans le fichier " + filePath, true);
            } catch (IOException e) {
                new InfosView(this, "Sauvegarde", "Erreur lors de la sauvegarde : " + e.getMessage(), false);
            }
        } else {
            System.out.println("L'utilisateur a annulé la sauvegarde.");
        }
    }
}
