package Controller;

import Model.Score;

import java.util.ArrayList;

public class GameStatus {

    private ArrayList<Score> scores;
    private ArrayList<Player> players;

    private GameStatus(){

        this.scores = new ArrayList<>();
        this.players = new ArrayList<>();

    }

    public GameStatus(ArrayList<Score> scores, ArrayList<Player> players){

        this();
        if(scores != null)        this.scores.addAll(scores);
        if(players != null)     this.players.addAll(players);

    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    ArrayList<Player> getPlayers() {
        return players;
    }

}
