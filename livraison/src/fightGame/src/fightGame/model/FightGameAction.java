package fightGame.model;

import java.io.Serializable;

import gamePlayers.util.Action;

public class FightGameAction implements Action, Serializable {

    public FightGameActionType TYPE;
    public FightGameAction(FightGameActionType type){
        this.TYPE = type;
    }
    @Override
    public String toString() {
        return "Action [TYPE=" + TYPE + "]";
    }

    public String screenDisplay(){
        String display = "";
        switch (TYPE){
            case MOVE_UNIT_TO_RIGHT :
                display = "Aller à droite";
                break;
            case MOVE_UNIT_TO_LEFT :
                display = "Aller à gauche";
                break;
            case  MOVE_UNIT_TO_BOTTOM :
                display = "Aller en bas";
                break;
            case MOVE_UNIT_TO_TOP :
                display = "Aller en haut";
                break;
            case USE_MINE_AT_LEFT :
                display = "Placer une mine à gauche";
                break;
            case USE_MINE_AT_RIGHT :
                display = "Placer une mine à droite";
                break;
            case USE_MINE_AT_BOTTOM :
                display = " Placer une mine en bas";
                break;
            case  USE_MINE_AT_TOP :
                display = "Placer une mine en haut";
                break;
            case USE_MINE_AT_TOP_LEFT :
                display = "Placer une mine en haut à gauche ";
                break;
            case  USE_MINE_AT_TOP_RIGHT :
                display = "Placer une mine en haut à droite";
                break;
            case USE_MINE_AT_BOTTOM_LEFT :
                display = "Placer une mine en bas à gauche ";
                break;
            case USE_MINE_AT_BOTTOM_RIGHT :
                display = "Placer une mine en bas à droite ";
            case USE_BOMB_AT_LEFT :
                display = "Placer une bombe à gauche ";
                break;
            case USE_BOMB_AT_RIGHT :
                display = "Placer une bombe à droite ";
                break;
            case  USE_BOMB_AT_BOTTOM :
                display = "Placer une bombe en bas " ;
                break;
            case USE_BOMB_AT_TOP :
                display = "Placer une bombe en haut ";
                break;
            case USE_BOMB_AT_TOP_LEFT :
                display = "Placer une bombe en haut à gauche ";
                break;
            case  USE_BOMB_AT_TOP_RIGHT :
                display = "Placer une bombe en haut à droite ";
                break;
            case USE_BOMB_AT_BOTTOM_LEFT :
                display = "Placer une bombe en bas à gauche ";
                break;
            case  USE_BOMB_AT_BOTTOM_RIGHT :
                display = "Placer une bombe en bas à droite ";
                break;
            case USE_PROJECTILE_AT_RIGHT :
                display = "Tirer à droite ";
                break;
            case USE_PROJECTILE_AT_LEFT :
                display = "Tirer à gauche ";
                break;
            case USE_PROJECTILE_AT_TOP :
                display = "Tirer en haut ";
                break;
            case USE_PROJECTILE_AT_BOTTOM :
                display = "Tirer en bas ";
                break;
            case NOTHING :
                display ="Ne rien utiliser";
                break;
            case ACTIVATE_SHIELD :
                display = "Activer l'armure ";
                break;
                
            default :
                break;
        }
        return display;
    }

    public FightGameActionType getTYPE() {
        return TYPE;
    }

}
