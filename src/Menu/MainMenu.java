package Menu;

import Controller.Game;
import Controller.Player;
import View.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainMenu extends JPanel {


    private Button continueBtn;
    private Button newGame;
    private Button setting;
    private Button aboutUs;
    private Button exitBtn;
    private Button scoreList;
//  todo add score list  private Button scores;
    private JFrame frame;
    private Player player;
    private static BufferedImage backGroundImage;
    private static BufferedImage headTitleImage;

    public MainMenu(JFrame frame, Player player){

        this.frame = frame;
        this.player = player;
        this.setLayout(null);
        this.setBounds(0,0,frame.getWidth(),frame.getHeight());
        this.frame.add(this);

        prepareButtons();
        setTexts();

        this.frame.revalidate();
        this.frame.repaint();
    }

    private void setTexts(){

        JLabel name = new JLabel("Hello " + player.getName());
        name.setBounds(720 , 175 , 400 ,180);
        name.setForeground(Color.white);
        name.setFont(new Font("Serif", Font.BOLD, 30));
        this.add(name);
        setBackGroundImages();

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

    private void prepareButtons() {

        prepareExit();
        prepareAboutUs();
        prepareNewGame();
        prepareSetting();
        prepareContinue();
        prepareScoreButton();

    }

    private void prepareScoreButton(){

        scoreList = new Button("Scores");

        scoreList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreList();
            }
        });

        scoreList.setBounds(720 , 255 + 150 * 3  ,200 , 150);

        this.add(scoreList);

    }

    private void prepareContinue(){

        continueBtn = new Button("Continue");

        continueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                continueBtn();
            }
        });

        continueBtn.setBounds(720 , 255 ,200 , 150);

        this.add(continueBtn);

    }

    private void prepareNewGame(){

        newGame = new Button("New Game");

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });

        newGame.setBounds(720 , 255 + 150 ,200 , 150);

        this.add(newGame);

    }

    private void prepareSetting(){

        setting = new Button("Setting");

        setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setting();
            }
        });

        setting.setBounds(720 , 255 + 150 * 2 ,200 , 150);

        this.add(setting);

    }

    private void prepareAboutUs(){

        aboutUs = new Button("About Us");

        aboutUs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aboutUs();
            }
        });

        aboutUs.setBounds(1400 , 255 + 180 * 3  ,200 , 180);

        this.add(aboutUs);

    }

    private void prepareExit(){

        exitBtn = new Button("LoginMenu");

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitBtn();
            }
        });

        exitBtn.setBounds(80 , 255 + 180 * 3  ,200 , 180);

        this.add(exitBtn);

    }

    private void exitBtn(){

        frame.remove(this);
        new LoginMenu(frame);

    }

    private void newGame(){

        frame.remove(this);
        new TypeOfPlayMenu(frame,player);

    }

    private void continueBtn(){

        Game game = player.getLastGame();
        if(game != null && game.getWaveNum() > 1){

            game.setWaveNum(game.getWaveNum() - 1);
            game.setDrawInfoColor(player.getColor());
            frame.remove(this);
            new View(game,frame);


        }
        else JOptionPane.showMessageDialog(null, "select new game");

    }

    private void scoreList(){

        this.frame.remove(this);
        new ScoreList(frame, player);

    }

    private void aboutUs(){

        JOptionPane.showMessageDialog(null, "E-Mail: mream79123@gmail.com");

    }

    private void setting(){

        this.frame.remove(this);
        new SettingMenu(frame, player);

    }

}
