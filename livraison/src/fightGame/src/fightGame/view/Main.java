package fightGame.view;

import fightGame.UnchangeableSettings;

public class Main {
    public static void main(String[] args) {
        UnchangeableSettings.loadSettings();
        //NewGame newGame = new NewGame();
        HomeView view = new HomeView();
        //newGame.play();

    }
}
