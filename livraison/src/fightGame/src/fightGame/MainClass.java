package fightGame;

import gamePlayers.fighters.Unit;

public class MainClass {
    public static void main(String[] args) {

        // Charger les paramètres de jeu depuis le fichier XML
        UnchangeableSettings.loadSettings();

        // Affichage des paramètres pour vérifier le chargement
        System.out.println("Nombre de rangées: " + UnchangeableSettings.NB_ROWS);
        System.out.println("Nombre de bombes: " + UnchangeableSettings.NB_BOMBS);

        
        System.out.println("fightGame package ok");
    }
}
