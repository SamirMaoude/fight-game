package fightGame.view;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;

public class PlayerView extends JPanel {
    public static int WIDTH = (InterfaceSetting.WIDTH) / 3 -50;
    private JLabel nameLabel;
    private JProgressBar energyProgressBar;
    public PlayerView(String name, int maxEnergy, int energy){
        super();
        this.nameLabel = new JLabel(name);
        this.nameLabel.setFont(InterfaceSetting.TEXT_FONT);
        this.energyProgressBar = new JProgressBar(0, maxEnergy);
        energyProgressBar.setPreferredSize(new Dimension(200, 90));
        energyProgressBar.setStringPainted(true);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setMinimumSize(new Dimension(250,80));
        this.add(this.nameLabel);
        this.add(this.energyProgressBar);
    }
}
