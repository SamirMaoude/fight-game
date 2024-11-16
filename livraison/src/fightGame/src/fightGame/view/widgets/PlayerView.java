package fightGame.view.widgets;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import fightGame.view.InterfaceSetting;

public class PlayerView extends JPanel {
    
    public static int WIDTH = (InterfaceSetting.WIDTH) / 3 -50;
    private JLabel nameLabel;
    private GameProgressBar energyProgressBar;
    
    public PlayerView(String name, int maxEnergy, int energy){
        super();
        this.nameLabel = new JLabel(name);
        this.nameLabel.setFont(InterfaceSetting.TEXT_FONT);
        this.energyProgressBar = new GameProgressBar(0, maxEnergy);
        this.energyProgressBar.setValue(energy);
    
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    
        // Configuration de nameLabel
        gbc.gridx = 0; 
        gbc.gridy = 0;
        gbc.weightx = 0; 
        gbc.insets = new Insets(20, 10, 10, 10); 
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.fill = GridBagConstraints.NONE; 
        this.add(this.nameLabel, gbc);
    
        // Configuration de energyProgressBar
        gbc.gridx = 1;
        gbc.weightx = 1; 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.anchor = GridBagConstraints.CENTER; 
        this.add(this.energyProgressBar, gbc);
    }
    
    public JProgressBar getEnergyProgressBar() {
        return energyProgressBar;
    }

    public void setEnergy(int energy){
        this.energyProgressBar.setValue(energy);
    }

    
}
