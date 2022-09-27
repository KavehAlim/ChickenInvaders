package Menu;

import Controller.Game;
import Controller.Player;
import MultiPlayer.Client.Client;
import MultiPlayer.Server.Server;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class MultiPlayerMenu extends JPanel {


    private Button server;
    private Button client;
    private Button observer;
    private JFrame frame;
    private Player player;
    private static BufferedImage backGroundImage;
    private static BufferedImage headTitleImage;


    MultiPlayerMenu(JFrame frame, Player player){

        this.player = player;
        this.frame = frame;
        this.setLayout(null);
        this.setBounds(0,0,frame.getWidth(),frame.getHeight());
        this.frame.add(this);

        setBackGroundImages();
        prepareButtons();

        this.frame.revalidate();
        this.frame.repaint();
    }

    private void setBackGroundImages(){

        try {

            backGroundImage = ImageIO.read(new File("Images/Menu/BackGround.jpg"));
            headTitleImage = ImageIO.read(new File("Images/Menu/HeadTitle.png"));

        }catch ( Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(backGroundImage,0,0, getWidth(), getHeight(), this);
        g.drawImage(headTitleImage, 620 , 0 , this);

    }

    private void prepareButtons(){

        prepareServer();
        prepareClient();
        prepareObserver();

    }


    private void prepareServer(){

        server = new Button("Server");

        server.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server();
            }
        });

        server.setBounds(720 , 255 + 90 ,200 , 180);

        this.add(server);

    }

    private void prepareClient(){

        client = new Button("Client");

        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client();
            }
        });

        client.setBounds(720 , 255 + 180 + 90,200 , 180);

        this.add(client);

    }

    private void prepareObserver(){

        observer = new Button("Observer");

        observer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                observer();
            }
        });

        observer.setBounds(720 , 255 + 180 + 180 + 90,200 , 180);

        this.add(observer);

    }


    private void server(){

        try {
            int port = Integer.parseInt(JOptionPane.showInputDialog("Enter Port Number:"));
            int maxClients = Integer.parseInt(JOptionPane.showInputDialog("Enter Maximum Clients:"));
            int missionNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter Mission Number:"));
            frame.remove(this);
            new ServerWaitingPage(player, frame, port, maxClients, missionNumber);
        }

        catch (Exception e){

            JOptionPane.showMessageDialog(null, "please enter valid values");

        }

    }

    private void client(){

        try {
            String host = JOptionPane.showInputDialog("Enter Host Address:");
            int port = Integer.parseInt(JOptionPane.showInputDialog("Enter Port Number:"));


            frame.remove(this);
            new Client(player, host, port, frame, false);
        }
        catch (Exception e){

            JOptionPane.showMessageDialog(null, "please enter valid values");

        }


    }

    private void observer(){

        try {
            String host = JOptionPane.showInputDialog("Enter Host Address:");
            int port = Integer.parseInt(JOptionPane.showInputDialog("Enter Port Number:"));


            frame.remove(this);
            new Client(player, host, port, frame, true);
        }
        catch (Exception e){

            JOptionPane.showMessageDialog(null, "please enter valid values");

        }

    }

}
