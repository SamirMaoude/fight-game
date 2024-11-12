package fightGame.view;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import fightGame.UnchangeableSettings;
import fightGame.view.widgets.CellRenderer;

public class GameBoardView extends JTable {
    
    public GameBoardView( Object[][] data, String[] columnNames){
        super(data, columnNames);
        build();
    }

    public GameBoardView(TableModel model){
        super(model);
        build();
    }

    private void build() {
        int d = this.getParent().getHeight()/3*this.getRowCount();
        //System.out.println(d);
        this.setRowHeight(d);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 
        for (int i = 0; i < this.getColumnModel().getColumnCount(); i++) {
            TableColumn column = this.getColumnModel().getColumn(i);
            column.setPreferredWidth(d);
        }
        this.setDefaultRenderer(Object.class,new CellRenderer());   
    }
}
