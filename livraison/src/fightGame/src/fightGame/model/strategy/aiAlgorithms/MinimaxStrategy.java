package fightGame.model.strategy.aiAlgorithms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fightGame.UnchangeableSettings;
import fightGame.model.FightGameAction;
import fightGame.model.*;
import fightGame.model.strategy.FightGamePlayerStrategy;
import gamePlayers.fighters.Unit;
import gamePlayers.util.*;

public class MinimaxStrategy implements FightGamePlayerStrategy, Serializable {

    private int nbPlayers;
    private List<FightGamePlayer> players;
    private int playerIndex;

    private static class Result {
        double score;
        Action action;
    
        Result(double score, Action action) {
            this.score = score;
            this.action = action;
        }
    }


    private static final double PROXIMITY_WEIGHT = 30;

    // Other properties and methods...

    public double evaluate(List<FightGamePlayer> players) {
        double score = UnchangeableSettings.STARTING_ENERGY;

        FightGamePlayer currentPlayer = players.get(playerIndex);
        Unit currentUnit = currentPlayer.getUnit();

        if(currentUnit == null) return Double.NEGATIVE_INFINITY;

        // 1. Health/Energy metric: Higher energy is better
        score += currentUnit.getEnergy();

        // 3. Simulate players to move to each other
        double totalDistance = 0.0;
        int nbOpponents = 0;
        for (FightGamePlayer opponent : players) {
            if(opponent.equals(currentPlayer))continue;
            if(opponent.getUnit() == null) continue;
            if(opponent.getUnit().isAlive()){
                double distance = currentUnit.getPosition().distanceTo(opponent.getUnit().getPosition());
                totalDistance += distance;
                nbOpponents++;

                // if(currentUnit.getEnergy() < opponent.getUnit().getEnergy()) score += opponent.getUnit().getEnergy() - currentUnit.getEnergy()  ;
            }

            
            
        }
        double averageDistance = 0;
        if(nbOpponents>0)averageDistance = totalDistance / (nbOpponents);

        // Closer proximity (smaller average distance) adds to the score for aggressive play
        score += PROXIMITY_WEIGHT / (averageDistance + 1);

        return score;
    }


    @Override
    public Action play(Player player, GameBoardProxy proxy) {
        
        playerIndex = player.getPlayerIndex();

        GameBoard gameBoard = proxy.getGameBoard();
        players = gameBoard.getPlayers();

        nbPlayers = gameBoard.getNbPlayers();   
        
        Result result = minimax(gameBoard, 5, players.get(playerIndex), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

        return result.action;
    }


    private Result minimax(GameBoard gameBoard, int depth, FightGamePlayer currentPlayer, double alpha, double beta) {

        Unit currUnit = currentPlayer.getUnit();
        
        if (gameBoard.isGameOver() || depth == 0 || !currUnit.isAlive()) {

            double score = this.evaluate(gameBoard.getPlayers());

            return new Result(score, null);  // Terminal node, no move to return
        }


        List<FightGamePlayer> players = gameBoard.getPlayers();

        // Get all possible moves for the current player
        List<Action> possibleActions = new ArrayList<>();
        
        // Build Attack First Actions
        Position currPosition = currUnit.getPosition();
        for (FightGamePlayer opponent : players) {
            if(opponent.equals(currentPlayer))continue;
            if(opponent.getUnit() == null) continue;

            Unit oppUnit = opponent.getUnit();
            
            if(oppUnit.isAlive()){
                
                Position opponentPosition = oppUnit.getPosition();
                double distance = currPosition.distanceTo(opponentPosition);
                if(currPosition.getRow() == opponentPosition.getRow()){
                    if(distance <= UnchangeableSettings.PROJECTILE_SCOPE){
                        
                        if(currPosition.getCol()< opponentPosition.getCol()){
                            if(currUnit.hasProjectiles())possibleActions.add(new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_RIGHT));
                            if(distance <= 2){
                                if(currUnit.hasBombs())possibleActions.add(new FightGameAction(FightGameActionType.USE_BOMB_AT_RIGHT));
                                if(currUnit.hasMines())possibleActions.add(new FightGameAction(FightGameActionType.USE_MINE_AT_RIGHT));
                            }
                        }
                        else{
                            if(currUnit.hasProjectiles())possibleActions.add(new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_LEFT));
                            if(distance <= 2){
                                if(currUnit.hasBombs())possibleActions.add(new FightGameAction(FightGameActionType.USE_BOMB_AT_LEFT));
                                if(currUnit.hasMines())possibleActions.add(new FightGameAction(FightGameActionType.USE_MINE_AT_LEFT));
                            }
                        }
                    }
                }
                else if(currPosition.getCol() == opponentPosition.getCol()){
                    if(distance <= UnchangeableSettings.PROJECTILE_SCOPE){
                        if(currPosition.getRow()< opponentPosition.getRow()){
                            if(currUnit.hasProjectiles())possibleActions.add(new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_BOTTOM));
                            if(distance <= 2){
                                if(currUnit.hasBombs())possibleActions.add(new FightGameAction(FightGameActionType.USE_BOMB_AT_BOTTOM));
                                if(currUnit.hasMines())possibleActions.add(new FightGameAction(FightGameActionType.USE_MINE_AT_BOTTOM));
                            }
                        }
                        else{
                            if(currUnit.hasProjectiles())possibleActions.add(new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_TOP));
                            if(distance <= 2){
                                if(currUnit.hasBombs())possibleActions.add(new FightGameAction(FightGameActionType.USE_BOMB_AT_TOP));
                                if(currUnit.hasMines())possibleActions.add(new FightGameAction(FightGameActionType.USE_MINE_AT_TOP));
                            }
                        }
                    }
                }
            }

            
            
        }


        if(possibleActions.isEmpty()){
            possibleActions = gameBoard.getActions(currentPlayer);

            int idx = 0;
            for(Action action: new ArrayList<>(possibleActions)){
                FightGameAction fightGameAction = (FightGameAction)action;
                switch (fightGameAction.getTYPE()) {
                    case USE_PROJECTILE_AT_RIGHT:
                    case USE_PROJECTILE_AT_LEFT:
                    case USE_PROJECTILE_AT_BOTTOM:
                    case USE_PROJECTILE_AT_TOP:
                    case USE_BOMB_AT_BOTTOM:
                    case USE_BOMB_AT_TOP:
                    case USE_BOMB_AT_BOTTOM_LEFT:
                    case USE_BOMB_AT_RIGHT:
                    case USE_MINE_AT_BOTTOM:
                    case USE_MINE_AT_TOP:
                    case USE_MINE_AT_BOTTOM_LEFT:
                    case USE_MINE_AT_RIGHT:
                        possibleActions.remove(idx);
                        idx -= 1;
                        break;
                    default:
                        break;
                }

                idx += 1;
            }
        }
        

        int currentPlayerIndex = currentPlayer.getPlayerIndex();

        double bestScore = currentPlayerIndex == playerIndex  ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;


        Action bestAction = null;

        for (Action action : possibleActions) {

            FightGameAction fightGameAction = (FightGameAction)action;

            // Apply the move to get the next game state
            GameBoard nextGameBoard = new GameBoard(gameBoard);
            
            nextGameBoard.performAction(fightGameAction, new FightGamePlayer(currentPlayer));
            
            FightGamePlayer nextPlayer = nextGameBoard.getNextPlayer();
            
            // Recursive minimax call
            Result result = minimax(nextGameBoard, depth - 1, nextPlayer, alpha, beta);
            double score = result.score;
           

            if (currentPlayerIndex == playerIndex) {  // Maximizing player
                if (score > bestScore) {
                    bestScore = score;
                    bestAction = action;
                }
                alpha = Math.max(alpha, score);
            } else {  // Minimizing player
                if (score < bestScore) {
                    bestScore = score;
                    bestAction = action;
                }
                beta = Math.min(beta, score);
            }

            // Prune branches where possible
            if (beta <= alpha) {
                break;
            }
        }
        
        if(bestAction == null){
            Random random = new Random();
            bestAction = possibleActions.get(random.nextInt(possibleActions.size()));
        }
        // Return the best scores and the move that led to it
        return new Result(bestScore, bestAction);
    }
    
}
