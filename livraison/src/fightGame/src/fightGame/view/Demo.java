package fightGame.view;

import javax.swing.JFrame;

public class Demo {
    public static void main(String[] args) {
         JFrame frame = new JFrame("Custom JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columnNames = {"ID", "Name", "Status"};
        Object[][] data = {
            {1, "Alice", "Active"},
            {2, "Bob", "Inactive"},
            {3, "Charlie", "Active"}
        };

        GameBoardView gameBoardView = new GameBoardView(data, columnNames);
        frame.add(gameBoardView);
        frame.pack();
        frame.setVisible(true);
    }
}
