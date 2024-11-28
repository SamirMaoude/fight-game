package fightGame.view.widgets;

import javax.swing.*;
import javax.swing.table.*;
import fightGame.model.GameBoard;
import fightGame.view.InterfaceSetting;
import fightGame.view.utils.CellRenderer;
/**
 * Represents a graphical table view of the game board, displaying the state of each cell.
 */
public class GameBoardTable extends JTable {
    private GameBoard gameBoard;
    public static final int WIDTH = (InterfaceSetting.WIDTH * 2) / 3;
    public static final int HEIGHT = InterfaceSetting.HEIGHT - 300;

    /**
     * Constructs a GameBoardTable with the specified table model.
     *
     * @param model the table model containing the data to display
     */
    public GameBoardTable(TableModel model) {
        super(model);
        build();
    }

    /**
     * Configures the table's appearance and behavior.
     * Sets row height, column width, and default cell renderer.
     */
    private void build() {
        this.setRowHeight(HEIGHT / this.getRowCount());
        for (int i = 0; i < this.getColumnModel().getColumnCount(); i++) {
            TableColumn column = this.getColumnModel().getColumn(i);
            column.setPreferredWidth(WIDTH / this.getColumnCount());
        }
        this.setDefaultRenderer(Object.class, new CellRenderer());
    }

    /**
     * Returns the associated {@link GameBoard}.
     *
     * @return the game board
     */
    public GameBoard getGameBoard() {
        return this.gameBoard;
    }
}
