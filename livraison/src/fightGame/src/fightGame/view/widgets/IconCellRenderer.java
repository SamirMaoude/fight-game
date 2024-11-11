package fightGame.view.widgets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;;

public class IconCellRenderer extends DefaultTableCellRenderer {
    private ImageIcon imageIcon;

    public IconCellRenderer(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        JLabel label = new JLabel();
        label.setIcon(this.imageIcon); 
        return label;
    }
}
