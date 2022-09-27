package MultiPlayer.Server;
import MultiPlayer.Client.ClientStatus;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ServerSocket serverSocket;
    private ConnectManager manager;
    private GameManager gameManager;
    public ArrayList<Integer> clientPorts;

    private ArrayList<ClientStatus> addLine = new ArrayList<>();

    private Gson gson;

    private int maxClient;

    private int port;

    private int mainPort;

    private boolean added;

    private int missionNumber;

    private Clients clients = new Clients();

    private DrawInfo info;

    private final Lock lock = new Lock();

    public Server(int port, int maxClient, int missionNumber){

        clientPorts = new ArrayList<>();
        gson = new Gson();

        this.maxClient = maxClient;
        this.port = port;
        this.missionNumber = missionNumber;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "port is not available");

        }

        manager = new ConnectManager();
        new Thread(manager).start();

        gameManager = new GameManager();

    }

    public void startGame(){

        new Thread(gameManager).start();
    }

    public class Clients extends ArrayList<ClientManager>{

        public synchronized boolean add(ClientManager manager){
            return super.add(manager);
        }

        public synchronized boolean remove(ClientManager manager){
            return super.remove(manager);
        }

    }

    class ConnectManager implements Runnable{

        @Override
        public void run() {

            while (true) {

                if (maxClient > clients.size()) {
                    try {
                        Socket socket = serverSocket.accept();
                        ClientManager manager = new ClientManager(socket);
                        clients.add(manager);
                        new Thread(manager).start();
                        System.out.println(socket.getPort() + " connected");
                        clientPorts.add(socket.getPort());
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "error while trying to connect server side");
                    }

                }

//                else System.out.println(maxClient - clients.size());

            }

        }

    }

    class GameManager implements Runnable{

        private MultiPlayerGame game;
        private long time;
        private int removePlayerId;
        private boolean removePlayer = false;

        GameManager(){

            game = new MultiPlayerGame(maxClient, missionNumber);
            time = System.currentTimeMillis();

        }

        @Override
        public void run() {

            while (true) {

                synchronized(lock) {

                    if (System.currentTimeMillis() > time + 5) {

                        if(!game.paused) {
                            game.move((int) (System.currentTimeMillis() - time));
                            game.draw();
                        }
                        time = System.currentTimeMillis();

                        if (addLine.size() != 0 && game.changingWaves) {

                            game.addPlayer(addLine.get(0));
                            addLine.remove(0);

                        }

                        if(removePlayer){

                            removePlayer = false;
                            game.removePlayer(removePlayerId);

                        }

                        setInfo();
                        updateStatus();

                        lock.notify();
                    }

                }

            }

        }

        synchronized void setInfo(){


            info = game.drawInfo;


        }

        synchronized void addPlayer(ClientStatus status){


            addLine.add(status);

        }

        synchronized void removePlayer(int id) {

            removePlayer = true;
            removePlayerId = id;

        }

        void updateStatus(){

            boolean result = true;

            boolean end = true;

            for (ClientStatus stat:game.status) {


//                if(stat.id == port && !clients.get(game.status.indexOf(stat)).connect) {
//
//                    System.out.println("id: " + stat.id);
//
//                    for (ClientManager clientManager: clients) {
//
//                        clientManager.connect = false;
//                        try {
//                            clientManager.socket.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                }
                if(stat.id == mainPort) end = false;

                ClientStatus s = clients.get(game.status.indexOf(stat)).status;
                if(!s.observer) result = result && !s.mouseStatus.isPaused();

                if(s.mouseStatus.isPaused() && !game.drawInfo.paused && !s.observer){

                    game.paused = true;
                    game.drawInfo.paused = true;

                }


                if (s.newClass.bytes != null && !s.newClass.className.equals(clients.get(game.status.indexOf(stat)).newClassName)){

                    clients.get(game.status.indexOf(stat)).newClassName = s.newClass.className;

                    try {
//                        System.out.println("Reflect/" + s.newClass.className.substring(s.newClass.className.indexOf('.')+1) + ".class");
                        OutputStream os = new FileOutputStream("Reflect/" + "ReflectionGroup.class" /*s.newClass.className.substring(s.newClass.className.indexOf('.')+1)+ ".class"*/);
                        os.write(s.newClass.bytes);
                        os.close();
                    } catch (Exception e) {

                        System.err.println("Exception: " + e);
                    }

                    if (s.newClass.boss) game.addBoss(s.newClass.className);

                    if (!s.newClass.boss) game.addChickens(s.newClass.className);

                }

                stat.setClientStatus(s);

            }

            if(end && added){

                info.end = true;

            }

            if(result && game.drawInfo.paused){

                game.paused = false;
                game.drawInfo.paused = false;

            }

        }

    }

    public class ClientManager implements Runnable{

        DataOutputStream output;
        DataInputStream input;
        Socket socket;
        ClientStatus status;
        private boolean connect;
        String newClassName;
        int id;
        private boolean check;

        ClientManager(Socket socket){

            connect = true;

            this.socket = socket;

            status = new ClientStatus(false);

            id = socket.getPort();

            status.id = socket.getPort();



            try {
                output = new DataOutputStream(socket.getOutputStream());
                output.writeUTF(gson.toJson(status.id));
            } catch (IOException e) {
//                System.out.println("error while set output on server side");
            }
            try {
                input = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
//                System.out.println("error while set input on server side");
            }

            gameManager.addPlayer(status);

        }

        public int getId() {
            return id;
        }

        @Override
        public void run() {

            while (connect) {
                synchronized (lock) {

                    if(socket.getPort() == mainPort && !added) {
                        added = true;
                    }

                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    send();

                }
                receive();

            }

        }

        private void send(){

            try {
                output.writeUTF(gson.toJson(info));
                output.flush();
            } catch (IOException e) {
//                System.out.println("error while sending info");
                connect = false;
//                System.out.println("disconnect");
                remove(id);
            }

        }

        private void receive(){

            try {
                status = (gson.fromJson(input.readUTF(),ClientStatus.class));
                if(status.observer && !check) {
                    check = true;
//                    maxClient ++;
                    remove(status.id);
                }
            } catch (IOException e) {
//                System.out.println("error while reading status");
                connect = false;
//                System.out.println("disconnect");
                remove(id);

            }

        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            serverSocket.close();
        }
    }


    void remove(int id){

        ClientManager clientManager = null;

        for (ClientManager cm: clients) {

            if(cm.getId() == id) clientManager = cm;

        }

        clients.remove(clientManager);

        gameManager.removePlayer(id);


    }

    public void setMainPort(int mainPort) {
        this.mainPort = mainPort;
    }
}
