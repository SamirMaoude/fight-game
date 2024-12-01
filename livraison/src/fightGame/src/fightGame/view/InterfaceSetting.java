package fightGame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

/**
 * This class defines various interface settings such as window dimensions, fonts, and colors
 * that are used across the game's graphical user interface.
 */
public class InterfaceSetting {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       
    /**
     * The width of the game window.
     */
    public static int WIDTH = screenSize.width;

    /**
     * The height of the game window.
     */
    public static int HEIGHT = screenSize.height;

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

    public static String BOMB_EXPLOSION_URL = "src/fightGame/src/fightGame/view/img/bombexplosion.jpg";
    public static String PROJECTIL_RIGHT_ICON_URL = "src/fightGame/src/fightGame/view/img/right.jpg";
    public static String PROJECTIL_LEFT_ICON_URL = "src/fightGame/src/fightGame/view/img/left.jpg";
    public static String PROJECTIL_TOP_ICON_URL = "src/fightGame/src/fightGame/view/img/top.jpg";
    public static String PROJECTIL_BOTTOM_ICON_URL = "src/fightGame/src/fightGame/view/img/bottom.jpg";
    public static String BOMB_ICON_URL = "src/fightGame/src/fightGame/view/img/bombicon.png";
    public static String WALL_ICON_URL = "src/fightGame/src/fightGame/view/img/wall.png";
    public static String MINE_ICON_URL = "src/fightGame/src/fightGame/view/img/mines.jpg";
    public static String UNIT_ICON_URL = "src/fightGame/src/fightGame/view/img/unit.png";
    public static String MAIN_UNIT_ICON_URL = "src/fightGame/src/fightGame/view/img/mainUnit.png";
    public static String SHIELD_URL = "src/fightGame/src/fightGame/view/img/shield.jpg";
    public static String PELLET_ICON_URL = "src/fightGame/src/fightGame/view/img/pellet.jpg";
    public static String BLANC_ICON_URL = "src/fightGame/src/fightGame/view/img/blanc.png";
}
