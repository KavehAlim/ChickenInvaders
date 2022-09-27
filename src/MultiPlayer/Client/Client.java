package MultiPlayer.Client;

import Controller.Player;
import Menu.MainMenu;
import Menu.MultiPlayerScore;
import MultiPlayer.Server.DrawInfo;
import com.google.gson.Gson;
import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private boolean observer;

    private Gson gson;

    private Socket socket;

    public int id;

    private boolean connect;

    private ClientManager manager;

    private DrawInfo info;

    private ClientView view;

    private Player player;

    private JFrame frame;

    class ClientManager implements Runnable{

        DataOutputStream output;

        DataInputStream input;

        ClientManager(){

            gson = new Gson();

            try {

                output = new DataOutputStream(socket.getOutputStream());
                input = new DataInputStream(socket.getInputStream());
                id = gson.fromJson(input.readUTF(),Integer.class);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "problem occurred while setting output and input");
            }


        }

        void send(ClientStatus status){

            try {
                status.id = id;
                output.writeUTF(gson.toJson(status));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
//                    System.out.println("error when sleeps");
                }
                output.flush();
            } catch (IOException e) {
//                System.out.println("problem occurred while sending form client to server");
                connect = false;
                if(!info.end) {
//                    JOptionPane.showMessageDialog(null, "can't connect to server");
                    view.goToScoreList();
                }
            }

        }

        private void receive(){

            try {
                info = gson.fromJson(input.readUTF(), DrawInfo.class);
                view.draw.setDrawInfo(info);

                if(info.paused && !view.esc.frame.isVisible() && !observer/*!view.status.mouseStatus.isPaused()*/){
                        view.esc.frame.setVisible(true);
                        view.status.mouseStatus.setPaused(true);
                        view.listener.setPaused(true);

                }

                if(!info.paused && view.esc.frame.isVisible()/*view.status.mouseStatus.isPaused()*/) {
                        view.esc.frame.setVisible(false);
                        view.status.mouseStatus.setPaused(false);
                    view.listener.setPaused(false);
                }

//                if(view.esc.frame.isVisible() && observer){
//
//
//
//                }

                if (info.end){
                    socket.close();
                    view.goToScoreList();
                }

//                if(view.esc.frame.isVisible() && !view.status.mouseStatus.isPaused()) {
//
//                    view.status.mouseStatus.setPaused(true);
//
//                }
            }
            catch (IOException es) {
//                System.out.println("can't read drawInfo");
                connect = false;
                if(!info.end) {
//                    JOptionPane.showMessageDialog(null, "can't connect to server");
                    view.goToScoreList();
                }
            }
            catch (NullPointerException nullPointerException){


                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }

        @Override
        public void run() {

            while (connect) {

                receive();
                send(view.getStatus());

            }
        }

    }


    public Client(Player player, String host, int port, JFrame frame, boolean observer){

        this.frame = frame;
        this.player = player;
        this.observer = observer;

        try {

            this.socket = new Socket(host, port);
            this.connect = true;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"problem while connecting to port");
        }

        view = new ClientView(player,socket.getLocalPort(), frame, observer);

        manager = new ClientManager();

        new Thread(manager).start();

    }



}
