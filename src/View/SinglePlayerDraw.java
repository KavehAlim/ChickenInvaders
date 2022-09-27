package View;

import Controller.SinglePlayerDrawInfo;
import Model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class SinglePlayerDraw {

    private SinglePlayerDrawInfo drawInfo;

    private static BufferedImage coinIcon;
    private static BufferedImage heartIcon;
    private static BufferedImage missileIcon;
    private JLabel lifeLabel;
    private JLabel coinLabel;
    private JLabel missileLabel;
    private JLabel waveLabel;
    private JLabel levelLabel;
    private JLabel pointLabel;

    private JPanel panel;



    SinglePlayerDraw(SinglePlayerDrawInfo drawInfo , JPanel panel){

        setImages();
        this.panel = panel;
        this.drawInfo = drawInfo;
        setStatusBar();
    }

    void draw(Graphics g){


        statusBar(g);

        BattleFieldView.drawBackground(g);

        heatBar(g);

        for (MyPoint location: drawInfo.eggLocations ) {


            g.drawImage(eggImage, (int) location.x, (int) location.y , Egg.SIZE.width , Egg.SIZE.height , null);


        }


        for (MyPoint location: drawInfo.coinLocations ) {

            BufferedImage image = coinImages[drawInfo.coinImageNumber.get(drawInfo.coinLocations.indexOf(location)) / 10 % 16];

            g.drawImage(image, (int) location.x, (int) location.y , Coin.SIZE.width , Coin.SIZE.height , null);


        }

        for (MyPoint location: drawInfo.bulletLocations) {

            BufferedImage image = BulletType.getImage(drawInfo.bulletType.get(drawInfo.bulletLocations.indexOf(location)));
            g.drawImage(image, (int) location.x, (int) location.y , Bullet.SIZE.width , Bullet.SIZE.height , null);

        }

        for (MyPoint location: drawInfo.missileLocations) {

            g.drawImage(missileIcon, (int) location.x, (int) location.y , Missile.SIZE.width * 2, Missile.SIZE.height * 2 , null);


        }

        for (MyPoint location: drawInfo.powerUpLocations ) {

            BufferedImage image = PowerUpType.getImage(drawInfo.powerUpType.get(drawInfo.powerUpLocations.indexOf(location)));
            g.drawImage(image, (int) location.x, (int) location.y , PowerUp.SIZE.width , PowerUp.SIZE.height , null);

        }



        for (MyPoint location: drawInfo.chickenLocations) {

            BufferedImage image;
            Dimension size;
            int imageNumber = drawInfo.chickenImageNumber.get(drawInfo.chickenLocations.indexOf(location));
            if(imageNumber > 0) {
                image = chickenImages[imageNumber / 10 % 14];
                size = Chicken.SIZE;
            }

            else {
                image = bossImages[imageNumber * (-1) - 1];
                size = Boss.SIZE;
            }

            g.drawImage(image, (int) location.x, (int) location.y , size.width , size.height , null);

        }

        if (!drawInfo.hit){

            BufferedImage image = Ship.getImage(drawInfo.color);
            g.drawImage(image, (int) drawInfo.shipLocation.x, (int)drawInfo.shipLocation.y, Ship.SIZE.width, Ship.SIZE.height, null);

        }

    }

    private void heatBar(Graphics g) {

        g.setColor(Color.RED);
        g.fillRect(10 , 10 , (int)(2 * drawInfo.temperature) ,22);
        g.setColor(Color.white);
        g.drawRect(10 , 10 , 205 ,22);

    }

    private void statusBar(Graphics g) {

        revalidateLabels();

    }
    private static void setStatusBarImages(){


        try {
            coinIcon = ImageIO.read(new File("Images/StatusBar/CoinIcon.png"));
            heartIcon = ImageIO.read(new File("Images/StatusBar/HeartIcon.png"));
            missileIcon = ImageIO.read(new File("Images/StatusBar/MissileIcon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setStatusBar()
    {

        setStatusBarImages();
        setLabels();

    }

    private void setLabels(){

        levelLabel = new JLabel();
        levelLabel.setForeground(Color.YELLOW);
        levelLabel.setText("level completed!!!");
        levelLabel.setFont(new Font("Serif", Font.PLAIN, 120));
        levelLabel.setBounds(380 , 600 , 1000, 90);

        waveLabel = new JLabel();
        waveLabel.setForeground(Color.CYAN);
        waveLabel.setFont(new Font("Serif", Font.PLAIN, 90));
        waveLabel.setBounds(680 , 400 , 400, 90);

        missileLabel = new JLabel("  " + drawInfo.bombNumber);
        missileLabel.setForeground(Color.white);
        missileLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        missileLabel.setIcon(new ImageIcon(missileIcon));
        missileLabel.setBounds(10 , 850 , 120, 40);

        lifeLabel = new JLabel("  " + drawInfo.shipLife);
        lifeLabel.setForeground(Color.white);
        lifeLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        lifeLabel.setIcon(new ImageIcon(heartIcon));
        lifeLabel.setBounds(10 , 890 , 120 , 40);

        coinLabel = new JLabel("  " + drawInfo.coinNumber);
        coinLabel.setForeground(Color.white);
        coinLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        coinLabel.setIcon(new ImageIcon(coinIcon));
        coinLabel.setBounds(10 , 930 , 120 , 40);

        pointLabel = new JLabel("  " + drawInfo.points);
        pointLabel.setForeground(Color.white);
        pointLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        pointLabel.setBounds(10 , 30, 120 , 40);

        panel.setLayout(null);
        panel.add(levelLabel);
        levelLabel.setVisible(false);
        panel.add(waveLabel);
        panel.add(pointLabel);
        panel.add(missileLabel);
        panel.add(coinLabel);
        panel.add(lifeLabel);

    }

    private void revalidateLabels(){

        if(drawInfo.waveNum % 5 != 1 && levelLabel.isVisible() || !drawInfo.changing)  {

            levelLabel.setVisible(false);

        }

        if(drawInfo.changing && drawInfo.waveNum != 1 && drawInfo.waveNum % 5 == 1 && !levelLabel.isVisible())
            levelLabel.setVisible(true);

        if(drawInfo.changing && !waveLabel.isVisible())  {

            waveLabel.setVisible(true);
            waveLabel.setText("wave: " + drawInfo.waveNum);

        }

        if(!drawInfo.changing && waveLabel.isVisible())
            waveLabel.setVisible(false);

        missileLabel.setText("  " + drawInfo.bombNumber);
        lifeLabel.setText("  " + drawInfo.shipLife);
        coinLabel.setText("  " + drawInfo.coinNumber);
        pointLabel.setText("  " + drawInfo.points);
        panel.revalidate();

    }


    private static BufferedImage eggImage;
    private static BufferedImage ShipImage;
    private static BufferedImage[] coinImages = new BufferedImage[16];
    private static BufferedImage[] chickenImages = new BufferedImage[14];
    private static BufferedImage[] bossImages = new BufferedImage[4];

    private static void setImages(){

        try {
            setBossImages();
            setCoinImages();
            setChickenImages();
            ShipImage = ImageIO.read(new File("Images/SpaceShip/GreenSpaceShip.png"));
            eggImage = ImageIO.read(new File("Images/Chicken/Egg.png"));
        }catch ( Exception e) {
            e.printStackTrace();
        }

    }

    private static void setCoinImages() throws IOException {

        for (int i = 0; i < coinImages.length / 2; i++) {

            coinImages[i] = ImageIO.read(new File("Images/Coin/Coin" + (i+1) + ".png"));


        }

        for (int i = coinImages.length / 2; i < coinImages.length; i++) {

            coinImages[i] = ImageIO.read(new File("Images/Coin/Coin" + (coinImages.length - i) + ".png"));

        }

    }

    private static void setChickenImages(){

        for (int i = 0; i < chickenImages.length / 2; i++) {

            try {
                chickenImages[i] = ImageIO.read(new File("Images/Chicken/Chicken" + (i+1) + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        for (int i = chickenImages.length / 2; i < chickenImages.length; i++) {

            try {
                chickenImages[i] = ImageIO.read(new File("Images/Chicken/Chicken" + (chickenImages.length - i) + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private static void setBossImages(){

        try {

            for (int i = 0; i < bossImages.length; i++) {

                bossImages[i] = ImageIO.read(new File("Images/Boss/Boss" + (i+1) +".png"));

            }

        }catch ( Exception e) {
            e.printStackTrace();
        }

    }
}
