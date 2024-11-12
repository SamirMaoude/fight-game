package fightGame.view.utils;

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
        label.setIcon((ImageIcon) value);
        label.setHorizontalAlignment(JLabel.CENTER); // Centrer l'image
        return label;
    }
}
