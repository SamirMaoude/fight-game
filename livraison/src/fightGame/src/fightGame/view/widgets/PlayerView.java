package fightGame.view.widgets;

import java.awt.*;
import javax.swing.*;
import fightGame.UnchangeableSettings;
import fightGame.model.FightGamePlayer;
import fightGame.view.InterfaceSetting;

/**
 * A view component that displays the status and weapons of a player.
 */
public class PlayerView extends JPanel {

    public static final int WIDTH = (InterfaceSetting.WIDTH) / 3 - 50;

    private GameProgressBar energyProgressBar;
    private JLabel nbMineLabel;
    private JLabel nbBombLabel;
    private JLabel nbProjectilLabel;
    private JLabel nbEnergyLabel;


    /**
     * Constructs a view for a specific player.
     *
     * @param player the player whose information is to be displayed
     */
    public PlayerView(FightGamePlayer player) {
        super();

        // Player name
        JLabel nameLabel = new JLabel(player.getName());
        nameLabel.setFont(InterfaceSetting.TEXT_FONT);

        // Energy bar
        this.energyProgressBar = new GameProgressBar(0, UnchangeableSettings.STARTING_ENERGY);
        this.energyProgressBar.setValue(player.getUnit().getEnergy());

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Energy panel
        JPanel energyPanel = new JPanel();
        energyPanel.add(nameLabel);
        energyPanel.add(this.energyProgressBar);
        this.add(energyPanel);

        // Weapons panel
        JPanel weaponPanel = new JPanel(new FlowLayout());

        JLabel bombLabel = new JLabel("Bombs");
        bombLabel.setFont(InterfaceSetting.TEXT_FONT);
        this.nbBombLabel = new JLabel(String.valueOf(player.getUnit().getBombs().size()));
        this.nbBombLabel.setFont(InterfaceSetting.TEXT_FONT);

        JLabel mineLabel = new JLabel("Mines");
        mineLabel.setFont(InterfaceSetting.TEXT_FONT);
        this.nbMineLabel = new JLabel(String.valueOf(player.getUnit().getMines().size()));
        this.nbMineLabel.setFont(InterfaceSetting.TEXT_FONT);

        JLabel projectilLabel = new JLabel("Projectiles");
        projectilLabel.setFont(InterfaceSetting.TEXT_FONT);
        this.nbProjectilLabel = new JLabel(String.valueOf(player.getUnit().getProjectiles().size()));
        this.nbProjectilLabel.setFont(InterfaceSetting.TEXT_FONT);

        JLabel energyLabel = new JLabel("Energy");
        energyLabel.setFont(InterfaceSetting.TEXT_FONT);
        this.nbEnergyLabel = new JLabel(String.valueOf(player.getUnit().getEnergy()));
        this.nbEnergyLabel.setFont(InterfaceSetting.TEXT_FONT);


        weaponPanel.add(bombLabel);
        weaponPanel.add(this.nbBombLabel);
        weaponPanel.add(mineLabel);
        weaponPanel.add(this.nbMineLabel);
        weaponPanel.add(projectilLabel);
        weaponPanel.add(this.nbProjectilLabel);
        weaponPanel.add(energyLabel);
        weaponPanel.add(this.nbEnergyLabel);
      

        this.add(weaponPanel);

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }


    /**
     * Updates the display for the number of mines.
     *
     * @param nbEnergyLabel the number of mines as a string
     */
    public void setNbEnergyLabel(String nbEnergyLabel) {
        this.nbEnergyLabel.setText(nbEnergyLabel);
    }


    /**
     * Returns the energy progress bar of the player.
     *
     * @return the energy progress bar
     */
    public JProgressBar getEnergyProgressBar() {
        return energyProgressBar;
    }

    /**
     * Updates the player's energy display.
     *
     * @param energy the current energy level
     */
    public void setEnergy(int energy) {
        if (energy <= 60 && energy > 30) {
            this.energyProgressBar.setForeground(InterfaceSetting.WARNING_COLOR);
        } else if (energy <= 30) {
            this.energyProgressBar.setForeground(InterfaceSetting.DANGER_COLOR);
        }
        this.energyProgressBar.setValue(energy);
    }

    /**
     * Updates the display for the number of mines.
     *
     * @param nbMineLabel the number of mines as a string
     */
    public void setNbMineLabel(String nbMineLabel) {
        this.nbMineLabel.setText(nbMineLabel);
    }

    /**
     * Updates the display for the number of bombs.
     *
     * @param nbBombLabel the number of bombs as a string
     */
    public void setNbBombLabel(String nbBombLabel) {
        this.nbBombLabel.setText(nbBombLabel);
    }

    /**
     * Updates the display for the number of projectiles.
     *
     * @param nbProjectilLabel the number of projectiles as a string
     */
    public void setNbProjectilLabel(String nbProjectilLabel) {
        this.nbProjectilLabel.setText(nbProjectilLabel);
    }
}
