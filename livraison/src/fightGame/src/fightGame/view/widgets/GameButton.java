package fightGame.view.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import fightGame.view.InterfaceSetting;

public class GameButton extends JButton {
    public GameButton(String name, int width, int height){
        super(name);
        this.setFont(InterfaceSetting.BTN_FONT);
        this.setMinimumSize(new Dimension(width,height));
        this.setBackground(Color.LIGHT_GRAY);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK,3) );
    }
    
}
