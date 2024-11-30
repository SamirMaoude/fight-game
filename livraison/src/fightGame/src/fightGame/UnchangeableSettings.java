package fightGame;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
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
    public static int NB_RANDOM_PLAYERS;
    public static int NB_MINIMAX_PLAYERS;
    public static int NB_MULTY_STRAT_PLAYERS = 0;

    public static int NB_INIT_PELLET;
    public static int NB_WALL;
    public static int FILL_STRATEGIE;
    public static int BORING_MOVE_LIMIT;




    // Méthode pour charger les paramètres
    public static void loadSettings(String file) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UnchangeableSettingsHandler handler = new UnchangeableSettingsHandler();
            saxParser.parse(file == null ? SETTINGS_FILE : file, handler);
            System.out.println("Game settings loaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
