package fightGame.view.widgets;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import fightGame.UnchangeableSettings;
import fightGame.model.GameBoard;
import fightGame.view.InterfaceSetting;
import fightGame.view.utils.BombCellRenderer;
import fightGame.view.utils.CellRenderer;
import fightGame.view.utils.IconCellRenderer;
import gamePlayers.objects.Bomb;

public class GameBoardTable extends JTable {
    private GameBoard gameBoard;
    public static int WIDTH = (InterfaceSetting.WIDTH*2) / 3;
    public static int HEIGHT = InterfaceSetting.HEIGHT-300;

    public GameBoardTable(TableModel model){
        super(model);
        build();
    }

    private void build() {
        this.setRowHeight(HEIGHT/this.getRowCount()+30);
        for (int i = 0; i < this.getColumnModel().getColumnCount(); i++) {
            TableColumn column = this.getColumnModel().getColumn(i);
            column.setPreferredWidth(WIDTH/this.getColumnCount());
        }
        this.setDefaultRenderer(Object.class,new BombCellRenderer());  
    }

    public GameBoard getGameBoard(){
        return this.gameBoard;
    }
}
