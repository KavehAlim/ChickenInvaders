package Controller;

public class WeaponData {

    private String type;
    private int power;
    private int max_temp;
    private int bullet_num;

    WeaponData(){

        type = "Yellow";
        power = 4;
        max_temp = 100;
        bullet_num = 1;

    }

    public WeaponData(String type,int max_temp,int power,int bullet_num){

        this.type = type;
        this.max_temp = max_temp;
        this.bullet_num = bullet_num;
        this.power = power;

    }

    public String getType() {
        return type;
    }

    public double getPower() {
        return power / 4.0;
    }

    public int getMax_temp() {
        return max_temp;
    }

    public int getBullet_num() {
        return bullet_num;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPower(double power) {
        this.power = (int) (power * 4);
    }

    public void setMax_temp(int max_temp) {
        this.max_temp = max_temp;
    }

    public void setBullet_num(int bullet_num) {
        this.bullet_num = bullet_num;
    }
}
