package fightGame;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UnchangeableSettingsHandler extends DefaultHandler {
    private String currentElement = "";

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName; // Sauvegarde le nom de la balise actuelle
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();

        if (!value.isEmpty()) {
            switch (currentElement) {
                case "nbRows":
                    UnchangeableSettings.NB_ROWS = Integer.parseInt(value);
                    break;
                case "nbCols":
                    UnchangeableSettings.NB_COLS = Integer.parseInt(value);
                    break;
                case "nbBombs":
                    UnchangeableSettings.NB_BOMBS = Integer.parseInt(value);
                    break;
                case "nbProjectiles":
                    UnchangeableSettings.NB_PROJECTILES = Integer.parseInt(value);
                    break;
                case "nbMines":
                    UnchangeableSettings.NB_MINES = Integer.parseInt(value);
                    break;
                case "bombVisibility":
                    if(Integer.parseInt(value) == 1) UnchangeableSettings.BOMB_VISIBILITY = true;
                    else UnchangeableSettings.BOMB_VISIBILITY = false;
                    break;
                case "mineVisibility":
                    if(Integer.parseInt(value) == 1) UnchangeableSettings.MINE_VISIBILITY = true;
                    else UnchangeableSettings.MINE_VISIBILITY = false;
                    break;
                case "startingEnergy":
                    UnchangeableSettings.STARTING_ENERGY = Integer.parseInt(value);
                    break;
                case "projectileScope":
                    UnchangeableSettings.PROJECTILE_SCOPE = Integer.parseInt(value);
                    break;
                case "bombDamage":
                    UnchangeableSettings.BOMB_DAMAGE = Integer.parseInt(value);
                    break;
                case "bombTimer":
                    UnchangeableSettings.BOMB_TIMER = Integer.parseInt(value);
                    break;
                case "mineDamage":
                    UnchangeableSettings.MINE_DAMAGE = Integer.parseInt(value);
                    break;
                case "projectileDamage":
                    UnchangeableSettings.PROJECTILE_DAMAGE = Integer.parseInt(value);
                    break;
                case "pelletBoost":
                    UnchangeableSettings.PELLET_BOOST = Integer.parseInt(value);
                    break;
                case "moveCost":
                    UnchangeableSettings.MOVE_COST = Integer.parseInt(value);
                    break;
                case "shieldCost":
                    UnchangeableSettings.SHIELD_COST = Integer.parseInt(value);
                    break;
                case "shieldAbsorption":
                    UnchangeableSettings.SHIELD_ABSORPTION = Integer.parseInt(value);
                    break;
                case "shieldTimer":
                    UnchangeableSettings.SHIELD_TIMER = Integer.parseInt(value);
                    break;

                case "randomPlayers":{
                    int intVal = Integer.parseInt(value);
                    if(intVal >=0){
                        UnchangeableSettings.NB_RANDOM_PLAYERS = intVal;
                    }
                    break;
                }

                case "minimaxPlayers":{
                    int intVal = Integer.parseInt(value);
                    if(intVal >=0){
                        UnchangeableSettings.NB_MINIMAX_PLAYERS = intVal;
                    }
                    break;
                }
                    
                case "initPellet":{
                    int intVal = Integer.parseInt(value);
                    if(intVal >=0){
                        UnchangeableSettings.NB_INIT_PELLET = intVal;
                    }
                    break;
                }

                case "nbWall":{
                    int intVal = Integer.parseInt(value);
                    if(intVal >=0){
                        UnchangeableSettings.NB_WALL = intVal;
                    }
                    break;
                }
                case "fillStrategy":{
                    int intVal = Integer.parseInt(value) % 2;
                    if(intVal >=0){
                        UnchangeableSettings.FILL_STRATEGIE = intVal;
                    }
                    break;
                }

                case "boringAction":{
                    int intVal = Integer.parseInt(value);
                    if(intVal >=0){
                        UnchangeableSettings.BORING_MOVE_LIMIT = intVal;
                    }
                    break;
                }
                

                
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentElement = ""; // Réinitialise l'élément actuel
    }
}
