package fightGame.view.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class CellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        @SuppressWarnings("unchecked")
        List<ImageIcon> iconList = (List<ImageIcon>) value;

        int cellWidth = table.getColumnModel().getColumn(column).getWidth();
        int cellHeight = table.getRowHeight(row);

        int iconCount = iconList.size();
        int spacing = 1; 

        int maxWidth = (cellWidth - (iconCount - 1) * spacing) / iconCount;

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int x = 0; 
                for (ImageIcon icon : iconList) {
                    // Redimensionner l'icône pour s'adapter à l'espace disponible
                    int iconWidth = Math.min(maxWidth, icon.getIconWidth());
                    int iconHeight = Math.min(cellHeight, icon.getIconHeight());
                    int y = (cellHeight - iconHeight) / 2; // Centrer verticalement

                    g.drawImage(icon.getImage(), x, y, iconWidth, iconHeight, this);
                    x += iconWidth + spacing; // Décale pour la prochaine image
                }

                // Dessiner l'effet de sélection
                if (isSelected) {
                    g.setColor(new Color(0, 0, 0, 100)); // Couleur semi-transparente
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Bordure de la cellule
        return panel;
    }
}
