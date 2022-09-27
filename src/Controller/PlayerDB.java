package Controller;

import Model.Ship;
import Model.Weapon;

public class PlayerDB {

    private PlayerData pd;
    private WeaponData wd;
    private ShipData sd;
    private GameData gd;

    public PlayerDB(String name){

        pd = new PlayerData(name);
        wd = new WeaponData();
        sd = new ShipData();
        gd = new GameData();

    }

    public PlayerDB(PlayerData playerData, GameData gameData, WeaponData weaponData, ShipData shipData){

        pd = playerData;
        gd = gameData;
        wd = weaponData;
        sd = shipData;

    }

    public static Player createPlayer(PlayerDB playerDB){

        Player player = new Player(playerDB.pd.getName());
        player.setAudio(playerDB.pd.isAudio());
        player.setColor(playerDB.pd.getColor());

        if(playerDB.gd.getWave_num() != 0){

            Game game = new Game(playerDB.pd.getName());

            game.setWaveNum(playerDB.gd.getWave_num());
            game.setPlayingTime(playerDB.gd.getPlaying_time());

            Weapon weapon = new Weapon();

            weapon.setCurrentBulletType(playerDB.wd.getType());
            weapon.setMaxTemperature(playerDB.wd.getMax_temp());
            weapon.setNumOfBullet(playerDB.wd.getBullet_num());
            weapon.setPowerOfBullet(playerDB.wd.getPower());

            Ship ship = new Ship();

            ship.setPoints(playerDB.gd.getPoints());
            ship.setLifeNumber(playerDB.sd.getLife_num());
            ship.setCoinNum(playerDB.sd.getCoin_num());
            ship.missilery.setMissileNum(playerDB.sd.getMissile_num());

            ship.setWeapon(weapon);
            game.setShip(ship);
            player.setColor(playerDB.pd.getColor());


            player.setLastGame(game);

        }

        return player;

    }


    public static PlayerDB createPlayerDB(Player player){

        PlayerDB playerDB = new PlayerDB(player.getName());
        playerDB.pd.setColor(player.getLastGame().getDrawInfoColor());
        playerDB.pd.setAudio(player.isAudio());

        if(player.getLastGame() != null){

            Game game = player.getLastGame();

            playerDB.gd.setPoints(game.getShip().getPoints());
            playerDB.gd.setWave_num(game.getWaveNum());
            playerDB.gd.setPlaying_time((int) game.getPlayingTime());

            playerDB.wd.setBullet_num(game.getShip().weapon.getNumOfBullet());
            playerDB.wd.setMax_temp(game.getShip().weapon.getMaxTemperature());
            playerDB.wd.setType(game.getShip().weapon.getCurrentBulletType().getType());
            playerDB.wd.setPower(game.getShip().weapon.getPowerOfBullet());

            playerDB.sd.setCoin_num(game.getShip().getCoinNum());
            playerDB.sd.setLife_num(game.getShip().getLifeNumber());
            playerDB.sd.setMissile_num(game.getShip().missilery.getMissileNum());

        }

        return playerDB;

    }


    public PlayerData getPd() {
        return pd;
    }

    public void setPd(PlayerData pd) {
        this.pd = pd;
    }

    public WeaponData getWd() {
        return wd;
    }

    public void setWd(WeaponData wd) {
        this.wd = wd;
    }

    public ShipData getSd() {
        return sd;
    }

    public void setSd(ShipData sd) {
        this.sd = sd;
    }

    public GameData getGd() {
        return gd;
    }

    public void setGd(GameData gd) {
        this.gd = gd;
    }
}
