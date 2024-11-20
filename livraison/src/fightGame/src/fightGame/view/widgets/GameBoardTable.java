package fightGame.view.widgets;

import javax.swing.*;
import javax.swing.table.*;
import fightGame.model.GameBoard;
import fightGame.view.InterfaceSetting;
import fightGame.view.utils.CellRenderer;

public class GameBoardTable extends JTable {
    private GameBoard gameBoard;
    public static int WIDTH = (InterfaceSetting.WIDTH*2) / 3;
    public static int HEIGHT = InterfaceSetting.HEIGHT-300;

    public GameBoardTable(TableModel model){
        super(model);
        build();
    }

    private void build() {
        this.setRowHeight(HEIGHT/this.getRowCount());
        for (int i = 0; i < this.getColumnModel().getColumnCount(); i++) {
            TableColumn column = this.getColumnModel().getColumn(i);
            column.setPreferredWidth(WIDTH/this.getColumnCount());
        }
        this.setDefaultRenderer(Object.class,new CellRenderer());  
    }

    public GameBoard getGameBoard(){
        return this.gameBoard;
    }
}
