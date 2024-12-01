package fightGame.model;

import java.io.Serializable;

import gamePlayers.util.Action;

/**
 * Represents an action in the fight game, such as moving, attacking, or using items.
 */
public class FightGameAction implements Action, Serializable {

    private FightGameActionType TYPE;

    /**
     * Constructor to initialize the action type.
     *
     * @param type the type of the action
     */
    public FightGameAction(FightGameActionType type) {
        this.TYPE = type;
    }

    /**
     * Provides a string representation of the action.
     *
     * @return a string describing the action type
     */
    @Override
    public String toString() {
        return "Action [TYPE=" + TYPE + "]";
    }

    /**
     *
     * @return a string describing the action in French
     */
    public String screenDisplay() {
        switch (TYPE) {
            case MOVE_UNIT_TO_RIGHT:
                return "Aller à droite";
            case MOVE_UNIT_TO_LEFT:
                return "Aller à gauche";
            case MOVE_UNIT_TO_BOTTOM:
                return "Aller en bas";
            case MOVE_UNIT_TO_TOP:
                return "Aller en haut";
            case USE_MINE_AT_LEFT:
                return "Placer une mine à gauche";
            case USE_MINE_AT_RIGHT:
                return "Placer une mine à droite";
            case USE_MINE_AT_BOTTOM:
                return "Placer une mine en bas";
            case USE_MINE_AT_TOP:
                return "Placer une mine en haut";
            case USE_MINE_AT_TOP_LEFT:
                return "Placer une mine en haut à gauche";
            case USE_MINE_AT_TOP_RIGHT:
                return "Placer une mine en haut à droite";
            case USE_MINE_AT_BOTTOM_LEFT:
                return "Placer une mine en bas à gauche";
            case USE_MINE_AT_BOTTOM_RIGHT:
                return "Placer une mine en bas à droite";
            case USE_BOMB_AT_LEFT:
                return "Placer une bombe à gauche";
            case USE_BOMB_AT_RIGHT:
                return "Placer une bombe à droite";
            case USE_BOMB_AT_BOTTOM:
                return "Placer une bombe en bas";
            case USE_BOMB_AT_TOP:
                return "Placer une bombe en haut";
            case USE_BOMB_AT_TOP_LEFT:
                return "Placer une bombe en haut à gauche";
            case USE_BOMB_AT_TOP_RIGHT:
                return "Placer une bombe en haut à droite";
            case USE_BOMB_AT_BOTTOM_LEFT:
                return "Placer une bombe en bas à gauche";
            case USE_BOMB_AT_BOTTOM_RIGHT:
                return "Placer une bombe en bas à droite";
            case USE_PROJECTILE_AT_RIGHT:
                return "Tirer à droite";
            case USE_PROJECTILE_AT_LEFT:
                return "Tirer à gauche";
            case USE_PROJECTILE_AT_TOP:
                return "Tirer en haut";
            case USE_PROJECTILE_AT_BOTTOM:
                return "Tirer en bas";
            case NOTHING:
                return "Ne rien utiliser";
            case ACTIVATE_SHIELD:
                return "Activer l'armure";
            default:
                return "Action inconnue";
        }
    }

    /**
     * Retrieves the type of the action.
     *
     * @return the action type
     */
    public FightGameActionType getTYPE() {
        return TYPE;
    }

    @Override
    public int compareTo(Object o) {
        FightGameAction action = (FightGameAction)o;

        return this.screenDisplay().compareTo(action.screenDisplay());
    }

}

