package DataBase;
import Model.Score;

import java.sql.*;
import java.util.ArrayList;

public class ScoreDataBase {

    public static void setScores(ArrayList<Score> scores){

        dropTable();

        createScoreTable();

        for (Score score : scores) {

            add(score);

        }

    }

    private static void add(Score score){

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            int rank = score.getRank();
            String name = score.getName();
            int wave_passed = score.getWavesPassed();
            int point = score.getPoint();
            int time = score.getTime();

            String sql = "INSERT INTO score (rank,name,wave_num,points,playing_time) "
                    + "VALUES ( "
                    + rank + " , '"
                    + name + "', "
                    + wave_passed + ", "
                    + point + ", "
                    + time +" );";

            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static ArrayList<Score> getScores(){

        ArrayList<Score> scores = new ArrayList<>();

        Connection c;
        Statement stmt;
        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet scoreTable = stmt.executeQuery("SELECT * FROM score;");

            while (scoreTable.next()) {

                int rank = scoreTable.getInt("rank");
                String name = scoreTable.getString("name");
                int wave_num = scoreTable.getInt("wave_num");
                int points = scoreTable.getInt("points");
                int playing_time = scoreTable.getInt("playing_time");

//                System.out.println("rank = " + rank + "name = " + name + " wave_num = " + wave_num + " points = " + points + " playing_time = " + playing_time);

                scores.add(new Score(rank, name, points, wave_num, playing_time));
            }

            scoreTable.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.out.println("score");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return scores;

    }

    static void createScoreTable(){

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM score;");
            rs.close();
            stmt.close();
            c.close();
//            System.out.println("score table already exists");

        } catch (Exception e) {

//            System.out.println("score table doesn't exists");

            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/game",
                                "mream", "123");

                stmt = c.createStatement();
                String sql = "CREATE TABLE score " +
                        "(rank               int PRIMARY KEY     NOT NULL," +
                        " name               text                NOT NULL, " +
                        " wave_num           int                 NOT NULL, " +
                        " points             int                 NOT NULL, " +
                        " playing_time       int                 NOT NULL); ";
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();


            } catch (Exception ex) {
                System.out.println("can't create score Table");
                System.exit(0);
            }
//            System.out.println("score Table created successfully");


        }

    }

    static void dropTable(){

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/game",
                            "mream", "123");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "DROP TABLE score;";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

}
