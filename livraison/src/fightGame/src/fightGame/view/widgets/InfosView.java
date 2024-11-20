package fightGame.view.widgets;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import fightGame.view.InterfaceSetting;

public class InfosView extends JDialog implements ActionListener {
    private GameButton closeButton;

    public InfosView(JFrame father, String title, String infos, boolean success) {
        super(father, title, true);

        this.setLayout(new BorderLayout(10, 10)); 
        this.setLocationRelativeTo(null);

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(new EmptyBorder(20, 20, 20, 20)); 

        JLabel infoLabel = new JLabel(infos, JLabel.CENTER); 
        infoLabel.setFont(InterfaceSetting.TEXT_FONT);
        infoLabel.setForeground(success ? Color.BLUE : Color.RED);

        messagePanel.add(infoLabel, BorderLayout.CENTER); 
        this.add(messagePanel, BorderLayout.CENTER); 

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        this.closeButton = new GameButton("Close", 150, 50);
        this.closeButton.addActionListener(this);
        buttonPanel.add(closeButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setMinimumSize(new Dimension(300, 300));; 
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.closeButton)) {
            this.dispose();
           // System.exit(0);
        }
    }
}
