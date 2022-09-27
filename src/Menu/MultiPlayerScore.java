package Menu;

import Controller.Player;
import DataBase.ScoreDataBase;
import Model.Score;
import MultiPlayer.Server.DrawInfo;
import MultiPlayer.Server.ShipDrawInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class MultiPlayerScore extends JPanel{

        private ArrayList<mpScore> scores;
        private JFrame frame;
        private Button menu;
        private static BufferedImage backGroundImage;
        private static BufferedImage headTitleImage;
        private Player player;

        public MultiPlayerScore(JFrame frame, Player player, DrawInfo drawInfo){

            scores = new ArrayList<>();
            for (ShipDrawInfo shipDrawInfo: drawInfo.shipsInfo) {

                scores.add(new mpScore(shipDrawInfo.id, shipDrawInfo.points));

            }
            this.player = player;
            this.frame = frame;
            this.setLayout(null);
            this.setBounds(0,0,frame.getWidth(),frame.getHeight());
            this.frame.add(this);

            for(mpScore score: scores){

                score.rank = scores.indexOf(score);

            }

            for (int i = 0; i < scores.size() ; i++) {

                mpScore scoreI = scores.get(i);

                for (int j = 0; j < scores.size(); j++) {

                    mpScore scoreJ = scores.get(j);


                    if(scoreI.rank < scoreJ.rank)
                        if(scoreI.points < scoreJ.points) {
                            int rank = scoreI.rank;
                            scoreI.rank = (scoreJ.rank);
                            scoreJ.rank = (rank);
                        }
                    if(scoreI.rank > scoreJ.rank)
                        if(scoreI.points > scoreJ.points) {
                            int rank = scoreI.rank;
                            scoreI.rank = (scoreJ.rank);
                            scoreJ.rank = (rank);
                        }

                }
            }

            setBackGroundImages();
            setLabels();
            setMenuButton();

            this.frame.revalidate();
            this.frame.repaint();


        }

        private class mpScore{

            int id;
            int points;
            int rank;

            mpScore(int id, int points){

                this.id = id;
                this.points = points;

            }

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

            JLabel name = new JLabel("  ID");
            name.setForeground(Color.white);
            name.setFont(new Font("Serif", Font.PLAIN, 30));
            name.setBounds(100 + 500, 230 , 300, 50);

            JLabel point = new JLabel("  Point");
            point.setForeground(Color.white);
            point.setFont(new Font("Serif", Font.PLAIN, 30));
            point.setBounds(100 + 500 * 2, 230, 300, 50);

            this.add(rank);
            this.add(name);
            this.add(point);

            for (int i = 0; i < scores.size(); i++) {

                for (mpScore score:scores) {
                    if(score.rank == i) {

                        JLabel rankLabel = new JLabel("  " + (score.rank+1));
                        rankLabel.setForeground(Color.white);
                        rankLabel.setFont(new Font("Serif", Font.PLAIN, 30));
                        rankLabel.setBounds(100, 255 + space * (i), 300, space);

                        JLabel nameLabel = new JLabel("  " + score.id);
                        nameLabel.setForeground(Color.white);
                        nameLabel.setFont(new Font("Serif", Font.PLAIN, 30));
                        nameLabel.setBounds(100 + 500, 255 + space * (i), 300, space);

                        JLabel pointLabel = new JLabel("  " + score.points);
                        pointLabel.setForeground(Color.white);
                        pointLabel.setFont(new Font("Serif", Font.PLAIN, 30));
                        pointLabel.setBounds(100 + 500 * 2, 255 + space * (i), 300, space);

                        this.add(rankLabel);
                        this.add(nameLabel);
                        this.add(pointLabel);

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
