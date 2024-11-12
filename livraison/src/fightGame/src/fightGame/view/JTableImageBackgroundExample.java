package fightGame.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class JTableImageBackgroundExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Image comme fond dans JTable");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            // Exemple de données pour le tableau avec les chemins d'images
            Object[][] data = {
                {"Texte 1", "path/to/your/image1.jpg"},
                {"Texte 2", "path/to/your/image2.jpg"},
                {"Texte 3", "path/to/your/image3.jpg"}
            };

            // Noms des colonnes
            String[] columnNames = {"Texte", "Image en fond"};

            // Créer un modèle de table avec les données
            JTable table = new JTable(data, columnNames);

            // Définir un renderer personnalisé pour la colonne de l'image
            table.getColumnModel().getColumn(1).setCellRenderer(new ImageBackgroundCellRenderer());

            // Ajouter la table dans un JScrollPane
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane);

            frame.setVisible(true);
        });
    }

    // Classe pour le rendu personnalisé de la cellule avec une image en fond
    static class ImageBackgroundCellRenderer extends DefaultTableCellRenderer {

        private String imagePath; // Chemin de l'image
        private Image image; // L'image chargée à partir du chemin

        // Constructeur prenant le chemin de l'image en argument
        public ImageBackgroundCellRenderer() {
            this.image = null;
        }

        // Ce constructeur est appelé lorsque l'on crée un renderer spécifique à une cellule
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            // Appel à la méthode de la classe parente pour récupérer la cellule standard
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (value instanceof String) {
                String path = (String) value;
                // Charger l'image depuis le chemin
                try {
                    this.image = ImageIO.read(new File(path)); // Lire l'image depuis le fichier
                } catch (IOException e) {
                    e.printStackTrace();
                    this.image = null;
                }

                // S'assurer que la cellule est opaque et que le fond est correctement affiché
                label.setOpaque(true);
                label.setBackground(Color.white);  // Couleur de fond pour la cellule

                // Retirer tout texte de la cellule, si vous voulez uniquement l'image
                label.setText(""); 
            }

            return label;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Si l'image a été chargée, la dessiner comme fond
            if (image != null) {
                // Redimensionner l'image pour qu'elle remplisse toute la cellule
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
