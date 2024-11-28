package fightGame.view.widgets;
import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

import fightGame.view.InterfaceSetting;

import java.awt.*;
/**
 * Custom progress bar with a rounded and modern design.
 */
public class GameProgressBar extends JProgressBar {

    /**
     * Creates a new GameProgressBar with a specified minimum and maximum value.
     *
     * @param min the minimum value of the progress bar
     * @param max the maximum value of the progress bar
     */
    public GameProgressBar(int min, int max) {
        super(min, max);

        // Set base dimensions and appearance
        setPreferredSize(new Dimension(200, 40));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Outer border
        setForeground(InterfaceSetting.OK_COLOR); // Fill color
        setBackground(new Color(224, 224, 224)); // Background color
        setStringPainted(true); // Display percentage text
        setFont(new Font("Arial", Font.BOLD, 14)); // Text font
        setUI(new RoundedProgressBarUI()); // Custom UI for rounded design
    }

    /**
     * Custom UI for rounded progress bar appearance.
     */
    private static class RoundedProgressBarUI extends BasicProgressBarUI {

        /**
         * Paints the progress bar in determinate mode.
         *
         * @param g the {@link Graphics} object to paint with
         * @param c the {@link JComponent} being painted
         */
        @Override
        protected void paintDeterminate(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = progressBar.getWidth();
            int height = progressBar.getHeight();
            int progress = (int) (progressBar.getPercentComplete() * width);

            // Draw progress fill
            g2.setColor(progressBar.getForeground());
            g2.fillRoundRect(0, 0, progress, height, 0, 0);

            // Draw outer border
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(0, 0, width - 1, height - 1, 0, 0);

            g2.dispose();
            super.paintDeterminate(g, c);
        }

        /**
         * Paints the progress bar in indeterminate mode, reusing the determinate painting logic.
         *
         * @param g the {@link Graphics} object to paint with
         * @param c the {@link JComponent} being painted
         */
        @Override
        protected void paintIndeterminate(Graphics g, JComponent c) {
            paintDeterminate(g, c); 
        }
    }
}
