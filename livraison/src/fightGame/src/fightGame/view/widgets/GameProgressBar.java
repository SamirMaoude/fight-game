package fightGame.view.widgets;
import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

public class GameProgressBar extends JProgressBar {

    public GameProgressBar(int min, int max) {
        super(min, max);

        // Configurez les dimensions de base
        setPreferredSize(new Dimension(200, 40)); 
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 
        setForeground(new Color(76, 175, 80)); 
        setBackground(new Color(224, 224, 224)); 
        setStringPainted(true); 
        setFont(new Font("Arial", Font.BOLD, 14)); 
        setUI(new RoundedProgressBarUI());
    }

    private static class RoundedProgressBarUI extends BasicProgressBarUI {
        @Override
        protected void paintDeterminate(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = progressBar.getWidth();
            int height = progressBar.getHeight();
            int progress = (int) (progressBar.getPercentComplete() * width);

            g2.setColor(progressBar.getForeground());
            g2.fillRoundRect(0, 0, progress, height, 0, 0);

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(3)); 
            g2.drawRoundRect(0, 0, width - 1, height - 1, 0, 0);

            g2.dispose();
            super.paintDeterminate(g, c);
        }

        @Override
        protected void paintIndeterminate(Graphics g, JComponent c) {
            paintDeterminate(g, c); // Reuse determinate for indeterminate mode
        }
    }
}
