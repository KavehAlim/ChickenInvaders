package Controller;

public class Player {

    private String name;
    private Game lastGame;
    private boolean audio;
    private String color;

    public Player(String name){

        setName(name);

    }
    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Game getLastGame() {
        return lastGame;
    }

    public void setLastGame(Game lastGame) {
        this.lastGame = lastGame;
    }

    public boolean isAudio() {
        return audio;
    }

    public void setAudio(boolean audio) {
        this.audio = audio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
