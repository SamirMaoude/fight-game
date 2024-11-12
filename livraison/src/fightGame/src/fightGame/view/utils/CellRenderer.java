package fightGame.view.utils;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        cell.setBackground(Color.LIGHT_GRAY);
        cell.setFont(new Font("SansSerif", Font.BOLD, 16));
        ((JComponent) cell).setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return cell;
    }
}
