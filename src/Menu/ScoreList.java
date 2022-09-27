package Menu;

import Controller.GameStatus;
import Controller.JSON;
import Controller.Player;
import DataBase.ScoreDataBase;
import Model.Score;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class ScoreList extends JPanel {

    private ArrayList<Score> scores;
    private JFrame frame;
    private Button menu;
    private static BufferedImage backGroundImage;
    private static BufferedImage headTitleImage;
    private Player player;

    public ScoreList(JFrame frame, Player player, Score score){

        scores = new ArrayList<>();

        this.player = player;
        this.frame = frame;
        this.setLayout(null);
        this.setBounds(0,0,frame.getWidth(),frame.getHeight());
        this.frame.add(this);

//        if(JSON.read() != null)
//            scores.addAll(JSON.read().scores);

        scores.addAll(ScoreDataBase.getScores());

        scores.add(score);

        for(Score s: scores){

            score.setRank(scores.indexOf(score));

        }

        for (int i = 0; i < scores.size() ; i++) {

            Score scoreI = scores.get(i);

            for (int j = 0; j < scores.size(); j++) {

                Score scoreJ = scores.get(j);


                if(scoreI.getRank() < scoreJ.getRank())
                    if(scoreI.getWavesPassed() < scoreJ.getWavesPassed() || (scoreI.getWavesPassed() == scoreJ.getWavesPassed() && scoreI.getPoint() < scoreJ.getPoint()) || (scoreI.getWavesPassed() == scoreJ.getWavesPassed() && scoreI.getPoint() == scoreJ.getPoint() && scoreI.getTime() > scoreJ.getTime())) {
                        int rank = scoreI.getRank();
                        scoreI.setRank(scoreJ.getRank());
                        scoreJ.setRank(rank);
                    }
                if(scoreI.getRank() > scoreJ.getRank())
                    if(scoreI.getWavesPassed() > scoreJ.getWavesPassed() || (scoreI.getWavesPassed() == scoreJ.getWavesPassed() && scoreI.getPoint() > scoreJ.getPoint()) || (scoreI.getWavesPassed() == scoreJ.getWavesPassed() && scoreI.getPoint() == scoreJ.getPoint() && scoreI.getTime() < scoreJ.getTime())) {
                        int rank = scoreI.getRank();
                        scoreI.setRank(scoreJ.getRank());
                        scoreJ.setRank(rank);
                    }

            }
        }

//        JSON.write(new GameStatus(scores, JSON.read().getPlayers()));

        ScoreDataBase.setScores(scores);

        setBackGroundImages();
        setLabels();
        setMenuButton();

        this.frame.revalidate();
        this.frame.repaint();


    }

    ScoreList(JFrame frame, Player player){

        scores = new ArrayList<>();

        this.player = player;
        this.frame = frame;
        this.setLayout(null);
        this.setBounds(0,0,frame.getWidth(),frame.getHeight());
        this.frame.add(this);

//        if(JSON.read() != null)
//            scores.addAll(JSON.read().scores);

        scores.addAll(ScoreDataBase.getScores());

        setBackGroundImages();
        setLabels();
        setMenuButton();

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

    private void setLabels(){

        int space = (800 - 250) / scores.size();
        JLabel rank = new JLabel(" Rank");
        rank.setForeground(Color.white);
        rank.setFont(new Font("Serif", Font.PLAIN, 30));
        rank.setBounds(100, 230 , 300, 50);

        JLabel name = new JLabel("  Name");
        name.setForeground(Color.white);
        name.setFont(new Font("Serif", Font.PLAIN, 30));
        name.setBounds(100 + 300, 230 , 300, 50);

        JLabel wave = new JLabel("  Waves");
        wave.setForeground(Color.white);
        wave.setFont(new Font("Serif", Font.PLAIN, 30));
        wave.setBounds(100 + 300 * 2, 230, 300, 50);

        JLabel point = new JLabel("  Point");
        point.setForeground(Color.white);
        point.setFont(new Font("Serif", Font.PLAIN, 30));
        point.setBounds(100 + 300 * 3, 230, 300, 50);

        JLabel time = new JLabel("  Time");
        time.setForeground(Color.white);
        time.setFont(new Font("Serif", Font.PLAIN, 30));
        time.setBounds(100 + 300 * 4, 230 , 300, 50);

        this.add(rank);
        this.add(name);
        this.add(wave);
        this.add(point);
        this.add(time);

        for (int i = 0; i < scores.size(); i++) {

            for (Score score:scores) {
                if(score.getRank() == i) {

                    JLabel rankLabel = new JLabel("  " + (score.getRank()+1));
                    rankLabel.setForeground(Color.white);
                    rankLabel.setFont(new Font("Serif", Font.PLAIN, 30));
                    rankLabel.setBounds(100, 255 + space * (i), 300, space);

                    JLabel nameLabel = new JLabel("  " + score.getName());
                    nameLabel.setForeground(Color.white);
                    nameLabel.setFont(new Font("Serif", Font.PLAIN, 30));
                    nameLabel.setBounds(100 + 300, 255 + space * (i), 300, space);

                    JLabel waveLabel = new JLabel("  " + score.getWavesPassed());
                    waveLabel.setForeground(Color.white);
                    waveLabel.setFont(new Font("Serif", Font.PLAIN, 30));
                    waveLabel.setBounds(100 + 300 * 2, 255 + space * (i), 300, space);

                    JLabel pointLabel = new JLabel("  " + score.getPoint());
                    pointLabel.setForeground(Color.white);
                    pointLabel.setFont(new Font("Serif", Font.PLAIN, 30));
                    pointLabel.setBounds(100 + 300 * 3, 255 + space * (i), 300, space);

                    JLabel timeLabel = new JLabel("  " + score.getTime());
                    timeLabel.setForeground(Color.white);
                    timeLabel.setFont(new Font("Serif", Font.PLAIN, 30));
                    timeLabel.setBounds(100 + 300 * 4, 255 + space * (i), 300, space);

                    this.add(rankLabel);
                    this.add(nameLabel);
                    this.add(waveLabel);
                    this.add(pointLabel);
                    this.add(timeLabel);

                }
            }

        }

    }

    private void setMenuButton(){

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

    private void menu(){

        frame.remove(this);
        new MainMenu(frame, player);

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(backGroundImage,0,0, getWidth(), getHeight(), this);
        g.drawImage(headTitleImage, 620 , 0 , this);

    }

}
