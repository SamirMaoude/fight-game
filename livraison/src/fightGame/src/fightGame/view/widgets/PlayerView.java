package fightGame.view.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

import fightGame.UnchangeableSettings;
import fightGame.model.FightGamePlayer;
import fightGame.view.InterfaceSetting;

public class PlayerView extends JPanel {
  
    public static int WIDTH = (InterfaceSetting.WIDTH) / 3 -50;
    private GameProgressBar energyProgressBar;
    JLabel nbMineLabel;
    JLabel nbBombLabel;
    JLabel nbProjectilLabel;
    
    public PlayerView(FightGamePlayer player){
        super();
        JLabel nameLabel = new JLabel(player.getName());
        nameLabel.setFont(InterfaceSetting.TEXT_FONT);
        this.energyProgressBar = new GameProgressBar(0, UnchangeableSettings.STARTING_ENERGY);
        this.energyProgressBar.setValue(player.getUnit().getEnergy());
    
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel energyPanel = new JPanel();
        energyPanel.add(nameLabel);
        energyPanel.add(this.energyProgressBar);
        this.add(energyPanel);

        JPanel weaponPanel = new JPanel();
        weaponPanel.setLayout(new FlowLayout());
        JLabel bombLabel = new JLabel("Bombs");
        bombLabel.setFont(InterfaceSetting.TEXT_FONT);
        this.nbBombLabel = new JLabel(String.valueOf(player.getUnit().getBombs().size()));
        this.nbBombLabel.setFont(InterfaceSetting.TEXT_FONT);

        JLabel mineLabel = new JLabel("Mines");
        mineLabel.setFont(InterfaceSetting.TEXT_FONT);
        this.nbMineLabel = new JLabel(String.valueOf(player.getUnit().getMines().size()));
        this.nbMineLabel.setFont(InterfaceSetting.TEXT_FONT);

        JLabel projectilLabel = new JLabel("Projectils");
        projectilLabel.setFont(InterfaceSetting.TEXT_FONT);
        this.nbProjectilLabel = new JLabel(String.valueOf(player.getUnit().getProjectiles().size()));
        this.nbProjectilLabel.setFont(InterfaceSetting.TEXT_FONT);

        weaponPanel.add(bombLabel);
        weaponPanel.add(this.nbBombLabel);
        weaponPanel.add(mineLabel);
        weaponPanel.add(this.nbMineLabel);
        weaponPanel.add(projectilLabel);
        weaponPanel.add(this.nbProjectilLabel);

        this.add(weaponPanel);

        this.setBorder(BorderFactory.createLineBorder(Color.black));
      
    }
    
    public JProgressBar getEnergyProgressBar() {
        return energyProgressBar;
    }

    public void setEnergy(int energy){
        if(energy<=60 && energy>30){
            this.energyProgressBar.setForeground(InterfaceSetting.WARNING_COLOR);
        }else if(energy<=30){
            this.energyProgressBar.setForeground(InterfaceSetting.DANGER_COLOR);
        }
        this.energyProgressBar.setValue(energy);
    }

    public void setNbMineLabel(String nbMineLabel) {
        this.nbMineLabel.setText(nbMineLabel);;
    }

    public void setNbBombLabel(String nbBombLabel) {
        this.nbBombLabel.setText(nbBombLabel);;
    }

    public void setNbProjectilLabel(String nbProjectilLabel) {
        this.nbProjectilLabel .setText(nbProjectilLabel);
    }

    

    
}
