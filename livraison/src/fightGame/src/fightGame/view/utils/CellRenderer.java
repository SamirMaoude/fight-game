package fightGame.view.utils;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        
        // Si la valeur est une instance de ImageIcon, on crée un panneau avec l'image en fond
        if (value instanceof ImageIcon) {
            ImageIcon icon = (ImageIcon) value;
            
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    
                    // Dessine l'image comme fond
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                    
                    // Applique un effet de sélection si la cellule est sélectionnée
                    if (isSelected) {
                        g.setColor(new Color(0, 0, 0, 100)); // couleur semi-transparente
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                }
            };
            
            panel.setOpaque(false); // Pour que l'image soit bien visible
            panel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            return panel;
        }

        // Sinon, comportement par défaut pour le texte
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        cell.setBackground(isSelected ? table.getSelectionBackground() : Color.LIGHT_GRAY);
        cell.setFont(new Font("SansSerif", Font.BOLD, 16));
        ((JComponent) cell).setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        return cell;
    }
}
