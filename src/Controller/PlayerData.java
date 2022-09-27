package Controller;

public class PlayerData {

    private String name;
    private boolean audio;
    private String color;

    PlayerData(String name){

        this.name = name;
        this.color = "Red";
        this.audio = true;

    }

    public PlayerData(String name, String color, boolean audio){

        this(name);
        this.color = color;
        this.audio = audio;

    }


    public String getName() {
        return name;
    }

    public boolean isAudio() {
        return audio;
    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAudio(boolean audio) {
        this.audio = audio;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
