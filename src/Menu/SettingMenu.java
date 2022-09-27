package Menu;

import Controller.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class SettingMenu extends JPanel{

    private Button red;
    private Button green;
    private Button blue;
    private Button menu;
    private JFrame frame;
    private Player player;
    private static BufferedImage backGroundImage;
    private static BufferedImage headTitleImage;


    SettingMenu(JFrame frame, Player player){

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

        prepareRed();
        prepareGreen();
        prepareBlue();
        prepareMenuButton();

    }


    private void prepareRed(){

        red = new Button("");

        red.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                red();
            }
        });

        red.setIcon(new ImageIcon("Images/SpaceShip/RedSpaceShip.PNG"));

        red.setBounds(720 , 245 + 90 ,200 , 180);

        this.add(red);

    }

    private void prepareGreen(){

        green = new Button("");

        green.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                green();
            }
        });

        green.setIcon(new ImageIcon("Images/SpaceShip/GreenSpaceShip.PNG"));

        green.setBounds(720 , 245 + 180 + 90,200 , 180);

        this.add(green);

    }

    private void prepareBlue(){

        blue = new Button("");

        blue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blue();
            }
        });

        blue.setIcon(new ImageIcon("Images/SpaceShip/BlueSpaceShip.PNG"));

        blue.setBounds(720 , 245 + 180 + 180 + 90,200 , 180);

        this.add(blue);

    }

    private void prepareMenuButton(){

        menu = new Button("Main Menu");

        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });

        menu.setBounds(720 , 800 ,200 , 180);

        this.add(menu);

    }

    private void red(){

        player.setColor("Red");

    }

    private void green(){

        player.setColor("Green");

    }

    private void blue(){

        player.setColor("Blue");

    }

    private void menu(){

        frame.remove(this);
        new MainMenu(frame, player);

    }

}
