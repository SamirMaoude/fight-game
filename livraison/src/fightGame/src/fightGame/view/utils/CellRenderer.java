package fightGame.view.utils;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
    boolean hasFocus, int row, int column)  {
        
        // Si la valeur est une instance de List<ImageIcon>, on crée un panneau pour afficher les images côte à côte
      /*   if (value instanceof List<?>) {
            List<?> iconList = (List<?>) value;
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    
                    int x = 0; // Position initiale pour dessiner les images
                    int spacing = 10; // Espacement entre les images
                    
                    // Parcours de la liste des ImageIcons et dessin de chaque image côte à côte
                    for (Object obj : iconList) {
                        if (obj instanceof ImageIcon) {
                            ImageIcon icon = (ImageIcon) obj;
                            g.drawImage(icon.getImage(), x, 0, icon.getIconWidth(), icon.getIconHeight(), this);
                            x += icon.getIconWidth() + spacing; // Décale pour la prochaine image
                        }
                    }
                    
                    // Applique un effet de sélection si la cellule est sélectionnée
                    if (isSelected) {
                        g.setColor(new Color(0, 0, 0, 100)); // Couleur semi-transparente
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                }
            };
            
            panel.setOpaque(false); // Pour que les images soient bien visibles
            panel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            return panel;

        }else*/
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

