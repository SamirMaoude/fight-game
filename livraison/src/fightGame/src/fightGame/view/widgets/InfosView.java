package fightGame.view.widgets;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import fightGame.view.InterfaceSetting;
/**
 * A dialog box to display information messages to the user.
 */
public class InfosView extends JDialog implements ActionListener {
    private GameButton closeButton;

    /**
     * Constructs an information dialog.
     *
     * @param father  the parent frame
     * @param title   the title of the dialog
     * @param infos   the message to display
     * @param success true if the message is a success message, false otherwise
     */
    public InfosView(JFrame father, String title, String infos, boolean success) {
        super(father, title, true);

        this.setLayout(new BorderLayout(10, 10)); 
        this.setLocationRelativeTo(null);

        // Message panel
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel infoLabel = new JLabel(infos, JLabel.CENTER); 
        infoLabel.setFont(InterfaceSetting.TEXT_FONT);
        infoLabel.setForeground(success ? Color.BLUE : Color.RED);

        messagePanel.add(infoLabel, BorderLayout.CENTER);
        this.add(messagePanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.closeButton = new GameButton("Close", 150, 50);
        this.closeButton.addActionListener(this);
        buttonPanel.add(closeButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        // Dialog settings
        this.setMinimumSize(new Dimension(300, 300));
        this.pack();
        this.setVisible(true);
    }

    /**
     * Handles button click events.
     *
     * @param e the event triggered by the button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.closeButton)) {
            this.dispose();
        }
    }
}
