package Controller;

public class GameData {

    private int wave_num;
    private int points;
    private int playing_time;

    GameData(){

        wave_num = 0;
        points = 0;
        playing_time = 0;

    }

    public GameData(int wave_num,int points,int playing_time){

        this.wave_num = wave_num;
        this.playing_time = playing_time;
        this.points = points;

    }


    public int getWave_num() {
        return wave_num;
    }

    public int getPoints() {
        return points;
    }

    public int getPlaying_time() {
        return playing_time;
    }

    void setWave_num(int wave_num) {
        this.wave_num = wave_num;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    void setPlaying_time(int playing_time) {
        this.playing_time = playing_time;
    }
}
