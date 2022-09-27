package DataBase;

import Controller.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PlayerDataBase {

    public static  void update(PlayerDB player){

        String id = player.getPd().getName();

        delete(id);
        add(player.getPd());
        add(id, player.getGd());
        add(id, player.getWd());
        add(id, player.getSd());

    }

    public static void delete(String name){

        PlayerDB player = new PlayerDB(name);
        String id = player.getPd().getName();
        deletePlayer(id);
        deleteGame(id);
        deleteShip(id);
        deleteWeapon(id);
    }

    private static void deletePlayer(String id){

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "DELETE from player where ID = '"+ id +"';";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    private static void deleteGame(String id){

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "DELETE from game where ID = '"+ id +"';";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


    }

    private static void deleteWeapon(String id){

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "DELETE from weapon where ID = '"+ id +"';";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    private static void deleteShip(String id){

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "DELETE from ship where ID = '"+ id +"';";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    public static void add(String name){

        PlayerDB player = new PlayerDB(name);
        String id = player.getPd().getName();
        add(player.getPd());
        add(id, player.getGd());
        add(id, player.getWd());
        add(id, player.getSd());

    }

    private static void add(PlayerData playerData){

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            String name = playerData.getName();
            String color = playerData.getColor();
            boolean audio = playerData.isAudio();
            String sql = "INSERT INTO player (id,color,audio) "
                    + "VALUES ( '"
                    + name + "' , '"
                    + color + "', "
                    + audio +" );";

            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    private static void add(String id, GameData gameData){

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            int wave_num = gameData.getWave_num();
            int points = gameData.getPoints();
            int playing_time = gameData.getPlaying_time();

            String sql = "INSERT INTO game (id,wave_num,points,playing_time) "
                    + "VALUES ( '"
                    + id + "' , "
                    + wave_num + ", "
                    + points + ", "
                    + playing_time +" );";

            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


    }

    private static void add(String id, WeaponData weaponData){

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            String t = weaponData.getType();
            int max_temperature = weaponData.getMax_temp();
            int power = (int) (weaponData.getPower() * 4);
            int bullet_number = weaponData.getBullet_num();

            String sql = "INSERT INTO weapon (id,type,max_temperature,power,bullet_number) "
                    + "VALUES ( '"
                    + id + "' , '"
                    + t + "' , "
                    + max_temperature + ", "
                    + power + ", "
                    + bullet_number +" );";

            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    private static void add(String id, ShipData shipData){

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            int missile_num = shipData.getMissile_num();
            int life_num = shipData.getLife_num();
            int coin_num = shipData.getCoin_num();

            String sql = "INSERT INTO ship (id,missile_num,life_num,coin_num) "
                    + "VALUES ( '"
                    + id + "' , "
                    + missile_num + ", "
                    + life_num + ", "
                    + coin_num +" );";

            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    public static ArrayList<PlayerDB> getPlayers(){

        ArrayList<PlayerDB> players = new ArrayList<>();

        ArrayList<PlayerData> pd = getPlayerData();
        ArrayList<GameData> gd = getGameData();
        ArrayList<WeaponData> wd = getWeaponData();
        ArrayList<ShipData> sd = getShipData();

        for (PlayerData playerData: pd) {

            int i = pd.indexOf(playerData);
            PlayerDB playerDB = new PlayerDB(playerData, gd.get(i), wd.get(i), sd.get(i));
            players.add(playerDB);

        }

        return players;

    }

    private static ArrayList<PlayerData> getPlayerData(){

        ArrayList<PlayerData> playersData = new ArrayList<>();

        Connection c;
        Statement stmt;
        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet playerTable = stmt.executeQuery("SELECT * FROM player;");

            while (playerTable.next()) {

                String id = playerTable.getString("id");
                String color = playerTable.getString("color");
                boolean audio = playerTable.getBoolean("audio");

//                System.out.println("name = " + id + " color = " + color + " audio = " + audio);

                PlayerData pd = new PlayerData(id,color,audio);
                playersData.add(pd);

            }

            playerTable.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.out.println("player");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


        return playersData;

    }

    private static ArrayList<GameData> getGameData(){

        ArrayList<GameData> gamesData = new ArrayList<>();

        Connection c;
        Statement stmt;
        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet gameTable = stmt.executeQuery("SELECT * FROM game;");


            while (gameTable.next()) {

                int wave_num = gameTable.getInt("wave_num");
                int points = gameTable.getInt("points");
                int playing_time = gameTable.getInt("playing_time");

//                System.out.println("wave_num = " + wave_num + " points = " + points + " time = " + playing_time);

                GameData gd = new GameData(wave_num,points,playing_time);
                gamesData.add(gd);

            }

            gameTable.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.out.println("game");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


        return gamesData;

    }

    private static ArrayList<WeaponData> getWeaponData(){

        ArrayList<WeaponData> weaponsData = new ArrayList<>();

        Connection c;
        Statement stmt;
        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet weaponTable = stmt.executeQuery("SELECT * FROM weapon;");


            while (weaponTable.next()) {

                String type = weaponTable.getString("type");
                int max_temp = weaponTable.getInt("max_temperature");
                int power = weaponTable.getInt("power");
                int bullet_number = weaponTable.getInt("bullet_number");

//                System.out.println("type = " + type + " max_temp = " + max_temp + " power = " + power + " bullet_number = " + bullet_number);

                WeaponData wd = new WeaponData(type,max_temp,power,bullet_number);
                weaponsData.add(wd);

            }

            weaponTable.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.out.println("weapon");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


        return weaponsData;

    }

    private static ArrayList<ShipData> getShipData(){

        ArrayList<ShipData> shipsData = new ArrayList<>();

        Connection c;
        Statement stmt;
        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet shipsTable = stmt.executeQuery("SELECT * FROM ship;");


            while (shipsTable.next()) {

                int missile_num = shipsTable.getInt("missile_num");
                int life_num = shipsTable.getInt("life_num");
                int coin_num = shipsTable.getInt("coin_num");

//                System.out.println("missile_num = " + missile_num + " life_num = " + life_num + " coin_num = " + coin_num);

                ShipData sd = new ShipData(missile_num,coin_num,life_num);
                shipsData.add(sd);

            }

            shipsTable.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


        return shipsData;

    }


    public static void initialWork(){

        connectToDataBase();
        createTable();
        ScoreDataBase.createScoreTable();

    }

    private static void connectToDataBase() {

        try {

            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/game", "mream", "123");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "can't connect to database");
            System.out.println("can't connect to database");
            System.exit(0);

        }

        System.out.println("connected to database successfully");

    }

    private static void createTable(){

        createPlayerTable();
        createShipTable();
        createWeaponTable();
        createGameTable();

    }

    private static void createPlayerTable(){

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM player;");
            rs.close();
            stmt.close();
            c.close();

//            System.out.println("player table already exists");

        } catch (Exception e) {

            System.out.println("player table doesn't exists");
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/game",
                                "mream", "123");

                stmt = c.createStatement();
                String sql = "CREATE TABLE player " +
                        "(id                 char(50) PRIMARY KEY     NOT NULL," +
                        " color              text                     NOT NULL, " +
                        " audio              boolean                  NOT NULL); ";
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
            } catch (Exception ex) {
                System.err.println(ex);
                System.out.println("can't create player Table");
                System.exit(0);
            }
//            System.out.println("player Table created successfully");


        }

    }

    private static void createWeaponTable(){

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM weapon;");
            rs.close();
            stmt.close();
            c.close();

//            System.out.println("weapon table already exists");

        } catch (Exception e) {

            System.out.println("weapon table doesn't exists");
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/game",
                                "mream", "123");

                stmt = c.createStatement();
                String sql = "CREATE TABLE weapon " +
                        "(id                 char(50) PRIMARY KEY     NOT NULL," +
                        " type               text                     NOT NULL, " +
                        " max_temperature    int                      NOT NULL, " +
                        " power              int                      NOT NULL, " +
                        " bullet_number      int                      NOT NULL); ";
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
            } catch (Exception ex) {
                System.out.println("can't create weapon Table");
                System.exit(0);
            }
//            System.out.println("weapon Table created successfully");


        }

    }

    private static void createShipTable(){

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ship;");
            rs.close();
            stmt.close();
            c.close();
//            System.out.println("ship table already exists");

        } catch (Exception e) {

            System.out.println("ship table doesn't exists");
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/game",
                                "mream", "123");

                stmt = c.createStatement();
                String sql = "CREATE TABLE ship " +
                        "(id                 char(50) PRIMARY KEY     NOT NULL," +
                        " missile_num        int                      NOT NULL, " +
                        " life_num           int                      NOT NULL, " +
                        " coin_num           int                      NOT NULL); ";
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
            } catch (Exception ex) {
                System.out.println("can't create ship Table");
                System.exit(0);
            }
            System.out.println("ship Table created successfully");


        }

    }

    private static void createGameTable(){

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM game;");
            rs.close();
            stmt.close();
            c.close();
//            System.out.println("game table already exists");

        } catch (Exception e) {

            System.out.println("table doesn't exists");

            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/game",
                                "mream", "123");

                stmt = c.createStatement();
                String sql = "CREATE TABLE game " +
                        "(id                 char(50) PRIMARY KEY     NOT NULL," +
                        " wave_num           int                      NOT NULL, " +
                        " points             int                      NOT NULL, " +
                        " playing_time       int                      NOT NULL); ";
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();


            } catch (Exception ex) {
                System.out.println("can't create Game Table");
                System.exit(0);
            }
            System.out.println("Game Table created successfully");


        }

    }
}
