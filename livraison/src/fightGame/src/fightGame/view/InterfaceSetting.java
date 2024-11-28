package fightGame.view;

import java.awt.Color;
import java.awt.Font;

/**
 * This class defines various interface settings such as window dimensions, fonts, and colors
 * that are used across the game's graphical user interface.
 */
public class InterfaceSetting {

    /**
     * The width of the game window.
     */
    public static int WIDTH = 1400;

    /**
     * The height of the game window.
     */
    public static int HEIGHT = 900;

    /**
     * The font used for buttons throughout the interface.
     */
    public static Font BTN_FONT = new Font("Arial", Font.BOLD, 25);

    /**
     * The font used for titles in the interface.
     */
    public static Font TITLE_FONT = new Font("Arial", Font.BOLD, 30);

    /**
     * The font used for general text in the interface.
     */
    public static Font TEXT_FONT = new Font("Arial", Font.BOLD, 15);

    /**
     * The color used to indicate a successful action (green).
     */
    public static Color OK_COLOR = new Color(76, 175, 80);

    /**
     * The color used to indicate a warning (yellow-orange).
     */
    public static Color WARNING_COLOR = new Color(254, 161, 18);

    /**
     * The color used to indicate danger or critical situations (red).
     */
    public static Color DANGER_COLOR = new Color(245, 18, 18);  
}
