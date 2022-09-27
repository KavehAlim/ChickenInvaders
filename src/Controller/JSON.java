package Controller;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class JSON {

    public static GameStatus read(){

        try {
            FileReader fileReader = new FileReader("game.data");
            GameStatus status = new Gson().fromJson(fileReader , GameStatus.class);
            fileReader.close();
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void write(GameStatus status){

        if(status == null) {
            System.out.println("null");
            return;
        }
        if(status.getPlayers() == null) {
            System.out.println("null players");
            return;
        }
        if(status.getScores() == null){

            System.out.println("null scores");
            return;

        }
        try {
            PrintWriter printWriter = new PrintWriter("game.data");
            System.out.println(status.toString());
            printWriter.write(new Gson().toJson(status));
            printWriter.flush();
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
