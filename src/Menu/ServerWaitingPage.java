package Menu;

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

public class ServerWaitingPage extends JPanel implements ActionListener {


    private JFrame frame;
    private Button start;
    private static BufferedImage backGroundImage;
    private static BufferedImage headTitleImage;
    private Server server;
    private int port;
    private Player player;

    private Timer timer = new Timer(100,this);

    ServerWaitingPage(Player player, JFrame frame, int port, int maxClient, int missionNumber){

        this.player = player;
        this.port = port;
        server = new Server(port,maxClient,missionNumber);
        this.frame = frame;
        this.setLayout(null);
        this.setBounds(0,0,frame.getWidth(),frame.getHeight());
        this.frame.add(this);

        setBackGroundImages();
        start = new Button("Start");
        prepareStartButton();

        this.frame.revalidate();
        this.frame.repaint();

        timer.start();

    }

    private void setBackGroundImages(){

        try {

            backGroundImage = ImageIO.read(new File("Images/Menu/BackGround.jpg"));
            headTitleImage = ImageIO.read(new File("Images/Menu/HeadTitle.png"));

        }catch ( Exception e) {
            e.printStackTrace();
        }


    }

    private void setLabels(){

        int space = 40;
        int i = 0;
        for (Integer clientPort : server.clientPorts) {

            JLabel idLabel = new JLabel("  " + (clientPort));
            idLabel.setForeground(Color.white);
            idLabel.setFont(new Font("Serif", Font.PLAIN, 30));
            idLabel.setBounds(100, 255 + space * (i+1), 300, space);

            i++;

            this.add(idLabel);

        }

        this.add(start);

    }

    private void prepareStartButton(){

//        start = new Button("Start");

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });

        start.setBounds(720 , 800 ,200 , 180);

        this.add(start);

    }

    private void start(){

        frame.remove(this);
        Client mainClient = new Client(player,"localhost", port, frame, false);
        server.setMainPort(mainClient.id);
        server.startGame();

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(backGroundImage,0,0, getWidth(), getHeight(), this);
        g.drawImage(headTitleImage, 620 , 0 , this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        this.removeAll();
        this.add(start);
        setLabels();
        this.repaint();
        this.revalidate();
        frame.revalidate();
        frame.repaint();

    }
}
