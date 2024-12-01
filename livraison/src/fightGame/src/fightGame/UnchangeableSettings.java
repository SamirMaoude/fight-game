package fightGame;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import fightGame.view.widgets.InfosView;

/**
 * Stores unchangeable settings for the fight game, loaded from a configuration file.
 */
public class UnchangeableSettings {

    /**
     * Path to the game settings XML file.
     */
    public static String SETTINGS_FILE = "src/fightGame/gameSettings.xml";

    /**
     * Path to the log file.
     */
    public static final String LOG_FILE = "src/fightGame/log.txt";

    // Game constants
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
    public static int NB_MULTY_STRAT_PLAYERS;
    public static int NB_HUMAIN_PLAYERS;
    public static int NB_INIT_PELLET;
    public static int NB_WALL;
    public static int FILL_STRATEGIE;
    public static int BORING_MOVE_LIMIT;

    /**
     * Loads settings from an XML configuration file.
     *
     * @param file the path to the configuration file
     */
    public static void loadSettings(String file) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UnchangeableSettingsHandler handler = new UnchangeableSettingsHandler();
            saxParser.parse(SETTINGS_FILE, handler);
        } catch (Exception e) {
            new InfosView(null, "Error", "Config file loading failed : " + e.getMessage(), false);
        }
    }
}
