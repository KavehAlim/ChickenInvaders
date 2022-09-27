package Controller;

public class ShipData {

    private int missile_num;
    private int coin_num;
    private int life_num;

    ShipData(){

        missile_num = 3;
        coin_num = 0;
        life_num = 5;

    }

    public ShipData(int missile_num,int coin_num,int life_num){

        this.missile_num = missile_num;
        this.life_num = life_num;
        this.coin_num = coin_num;

    }

    public int getMissile_num() {
        return missile_num;
    }

    public int getCoin_num() {
        return coin_num;
    }

    public int getLife_num() {
        return life_num;
    }

    void setMissile_num(int missile_num) {
        this.missile_num = missile_num;
    }

    void setCoin_num(int coin_num) {
        this.coin_num = coin_num;
    }

    void setLife_num(int life_num) {
        this.life_num = life_num;
    }
}
