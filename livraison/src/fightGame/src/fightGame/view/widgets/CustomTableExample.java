package fightGame.view.widgets;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;

public class CustomTableExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Custom JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columnNames = {"ID", "Name", "Status"};
        Object[][] data = {
            {1, "Alice", "Active"},
            {2, "Bob", "Inactive"},
            {3, "Charlie", "Active"}
        };

        JTable table = new JTable(data, columnNames);

        // Spécifiez la hauteur de chaque ligne
        table.setRowHeight(60); // Hauteur de chaque ligne

        // Spécifiez la largeur des colonnes
        table.getColumnModel().getColumn(0).setPreferredWidth(50);   // Largeur de la première colonne (ID)
        table.getColumnModel().getColumn(1).setPreferredWidth(150);  // Largeur de la deuxième colonne (Name)
        table.getColumnModel().getColumn(2).setPreferredWidth(100);  // Largeur de la troisième colonne (Status)

        // Appliquer une bordure autour de la JTable
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Bordure noire épaisse autour de la table

        // Utiliser un renderer pour ajouter une bordure autour de chaque cellule
        table.setDefaultRenderer(Object.class, new CustomCellRenderer());

        // Personnaliser l'en-tête de la table
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setForeground(Color.BLUE);

        // Ajouter la table dans un JScrollPane
        frame.add(new JScrollPane(table));
        frame.pack();
        frame.setVisible(true);
    }

    // Renderer personnalisé pour ajouter une bordure autour de chaque cellule
    static class CustomCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            // Ajustez la bordure de chaque cellule
            if (isSelected) {
                cell.setBackground(Color.YELLOW); // Couleur de sélection
                cell.setForeground(Color.BLACK);
            } else {
                cell.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE); // Couleur en alternance
                cell.setForeground(Color.BLACK);
            }

            // Ajouter une bordure fine noire autour de chaque cellule
            ((JComponent) cell).setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

            return cell;
        }
    }
}
