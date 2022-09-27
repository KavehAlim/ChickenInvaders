package Model;

public class Score {

    private int rank;
    private int point;
    private int wavesPassed;
    private int time;
    private String name;

    public Score(String name, int point, int wavesPassed, int time){

        this.name = name;
        this.point = point;
        this.wavesPassed = wavesPassed;
        this.time = time;

    }

    public Score(int rank, String name, int point, int wavesPassed, int time){

        this(name, point, wavesPassed, time);
        this.rank = rank;

    }

    public int getPoint() {
        return point;
    }

    public int getWavesPassed() {
        return wavesPassed;
    }

    public int getTime() {
        return time;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }
}
