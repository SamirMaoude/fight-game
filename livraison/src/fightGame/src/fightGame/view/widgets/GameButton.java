package fightGame.view.widgets;

import java.awt.*;
import javax.swing.*;

import fightGame.view.InterfaceSetting;
/**
 * Custom button class for the game interface with a modern and rounded design.
 */
public class GameButton extends JButton {

    /**
     * Constructs a new GameButton with the specified text, width, and height.
     *
     * @param name   the text displayed on the button
     * @param width  the preferred width of the button
     * @param height the preferred height of the button
     */
    public GameButton(String name, int width, int height) {
        super(name);

        // Disable default Swing styling for custom appearance
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);

        // Apply styling
        this.setFont(InterfaceSetting.BTN_FONT);
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.LIGHT_GRAY);
        this.setForeground(Color.BLACK); // Text color
        this.setBorder(BorderFactory.createEmptyBorder()); // No default border
    }

    /**
     * Paints the button with a custom rounded design and hover/press effects.
     *
     * @param g the {@link Graphics} object to protect and render
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Change background color based on button state
        if (getModel().isPressed()) {
            g2.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g2.setColor(InterfaceSetting.OK_COLOR);
        } else {
            g2.setColor(getBackground());
        }

        int arc = 20; // Corner arc for rounded design
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        // Draw border
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3)); // Border thickness
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

        g2.dispose();

        super.paintComponent(g);
    }
}
