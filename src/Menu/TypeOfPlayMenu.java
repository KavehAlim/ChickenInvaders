package Menu;

import Controller.Game;
import Controller.Player;
import Controller.PlayerDB;
import DataBase.PlayerDataBase;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class TypeOfPlayMenu extends JPanel {

    private Button single;
    private Button socket;
    private JFrame frame;
    private Player player;
    private static BufferedImage backGroundImage;
    private static BufferedImage headTitleImage;


    TypeOfPlayMenu(JFrame frame, Player player){

        this.frame = frame;
        this.player = player;
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

        prepareSingle();
        prepareSocket();

    }


    private void prepareSingle(){

        single = new Button("SinglePlayer");

        single.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                single();
            }
        });

        single.setBounds(720 , 255 + 90 ,200 , 180);

        this.add(single);

    }

    private void prepareSocket(){

        socket = new Button("MultiPlayer");

        socket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                socket();
            }
        });

        socket.setBounds(720 , 255 + 180 + 90,200 , 180);

        this.add(socket);

    }


    private void single(){


        frame.remove(this);
        Game game = new Game(frame, player);
        game.setDrawInfoColor(player.getColor());
        player.setLastGame(game);

//            GameStatus gs = JSON.read();
//
//            for (Player player: gs.getPlayers()){
//
//                if(player.getName().equals(game.name)){
//
//                    player.setLastGame(new Game(frame, player));
//
//                }
//
//            }
//
//            JSON.write(gs);

        PlayerDB pdb = PlayerDB.createPlayerDB(player);
        PlayerDataBase.update(pdb);

    }

    private void socket(){

        frame.remove(this);
        new MultiPlayerMenu(frame, player);

    }

}
