package fightGame.model.aiAlgorithms;

import java.util.List;

import fightGame.UnchangeableSettings;
import fightGame.model.FightGameAction;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.GameBoardProxy;
import gamePlayers.fighters.Unit;
import gamePlayers.util.Action;
import gamePlayers.util.Player;



public class MinimaxStrategy implements FightGamePlayerStrategy {

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


    private static final double WEAPONS_WEIGHT = 200;
    private static final double PROXIMITY_WEIGHT = 50;

    // Other properties and methods...

    public double evaluate(List<FightGamePlayer> players) {
        double score = 0.0;

        FightGamePlayer currentPlayer = players.get(playerIndex);
        Unit currentUnit = currentPlayer.getUnit();

        // 1. Health/Energy metric: Higher energy is better
        score += currentUnit.getEnergy();

        // 2. Weapons count: Stimulate player to use weapon
        int totalWeapons = currentUnit.getBombs().size() + currentUnit.getMines().size() + currentUnit.getProjectiles().size();
        score += WEAPONS_WEIGHT / (totalWeapons + 1 );

        // 3. Simulate players to move to each other
        double totalDistance = 0.0;
        int nbOpponents = 0;
        for (FightGamePlayer opponent : players) {
            if(opponent.equals(currentPlayer))continue;
            if(opponent.getUnit().isAlive()){
                double distance = currentUnit.getPosition().distanceTo(opponent.getUnit().getPosition());
                totalDistance += distance;
                nbOpponents++;
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
        if (gameBoard.isGameOver() || depth == 0) {

            double score = this.evaluate(gameBoard.getPlayers());

            return new Result(score, null);  // Terminal node, no move to return
        }

        int currentPlayerIndex = currentPlayer.getPlayerIndex();

        double bestScore = currentPlayerIndex == playerIndex  ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

        

        Action bestAction = null;


        // Get all possible moves for the current player
        List<Action> possibleActions = gameBoard.getActions(currentPlayer);

        for (Action action : possibleActions) {
            // Apply the move to get the next game state
            GameBoard nextGameBoard = new GameBoard(gameBoard);
            
            nextGameBoard.performAction((FightGameAction)action, new FightGamePlayer(currentPlayer));
            
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
                return new Result(bestScore, bestAction);
                // break;
            }
        }
        
        // Return the best scores and the move that led to it
        return new Result(bestScore, bestAction);
    }
    
}
