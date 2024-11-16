package fightGame.view.widgets;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import fightGame.view.InterfaceSetting;

public class GameButton extends JButton {
    public GameButton(String name, int width, int height) {
        super(name);

        // Désactive les options Swing par défaut pour personnaliser l'apparence
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);

        // Appliquer le style
        this.setFont(InterfaceSetting.BTN_FONT); 
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.LIGHT_GRAY);
        this.setForeground(Color.BLACK); // Couleur du texte
        this.setBorder(BorderFactory.createEmptyBorder()); // Pas de bordure Swing par défaut
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Définir les couleurs en fonction de l'état du bouton
        if (getModel().isPressed()) {
            g2.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g2.setColor(getBackground().brighter());
        } else {
            g2.setColor(getBackground());
        }

        int arc = 20; 
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        g2.setColor(Color.BLACK); 
        g2.setStroke(new BasicStroke(3)); 
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

        g2.dispose();

        super.paintComponent(g);
    }
}
