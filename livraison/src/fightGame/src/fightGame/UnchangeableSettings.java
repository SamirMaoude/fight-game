package fightGame;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
public class UnchangeableSettings {

    public static String SETTINGS_FILE = "livraison/src/fightGame/gameSettings.xml";
    
    // Déclaration des constantes de jeu
    public static int NB_ROWS;
    public static int NB_COLS;
    public static int NB_BOMBS;
    public static int NB_PROJECTILES;
    public static int NB_MINES;
    public static boolean BOMB_VISIBILITY;
    public static boolean MINE_VISIBILITY;
    public static int STARTING_ENERGY;
    public static int PROJECTILE_SCOPE;
    public static int BOMB_DAMAGE;
    public static int BOMB_TIMER;
    public static int MINE_DAMAGE;
    public static int PROJECTILE_DAMAGE;
    public static int PELLET_BOOST;
    public static int MOVE_COST;
    public static int SHIELD_COST;
    public static int SHIELD_ABSORPTION;
    public static int SHIELD_TIMER;
    public static int NB_PLAYERS = 2;



    // Méthode pour charger les paramètres
    public static void loadSettings() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UnchangeableSettingsHandler handler = new UnchangeableSettingsHandler();
            saxParser.parse(SETTINGS_FILE, handler);
            System.out.println("Game settings loaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
