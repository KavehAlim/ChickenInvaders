package MultiPlayer.Client;

import Model.*;
import MultiPlayer.Server.DrawInfo;
import MultiPlayer.Server.ShipDrawInfo;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


class ClientDraw {

    private int id;
    private boolean observer;
    private JPanel panel;
    DrawInfo drawInfo;
    private ShipDrawInfo shipDrawInfo;

    private static BufferedImage coinIcon;
    private static BufferedImage heartIcon;
    private static BufferedImage missileIcon;
    private JLabel lifeLabel;
    private JLabel coinLabel;
    private JLabel missileLabel;
    private JLabel pointLabel;
    private JLabel waveLabel;

    private ArrayList<JLabel> shipsIdLabels;
    private JLabel levelLabel;


    ClientDraw(JPanel panel, int id, boolean observer){

        this.observer = observer;
        this.id = id;
        this.panel = panel;
        drawInfo = new DrawInfo();
        shipsIdLabels = new ArrayList<>();
        if(!observer) {
            shipDrawInfo = new ShipDrawInfo();
            setStatusBar();
        }
        setImages();
    }

    void draw(Graphics g){

        if(drawInfo != null) {

            if(!observer) {
                heatBar(g);
                statusBar(g);
            }
        for (MyPoint location: drawInfo.eggLocations ) {

            g.drawImage(eggImage, (int) location.x, (int) location.y , Egg.SIZE.width , Egg.SIZE.height , null);

        }


        for (MyPoint location: drawInfo.coinLocations ) {

            BufferedImage image = coinImages[drawInfo.coinImageNumber.get(drawInfo.coinLocations.indexOf(location)) / 10 % 16];

            g.drawImage(image, (int) location.x, (int) location.y , Coin.SIZE.width , Coin.SIZE.height , null);


        }

            for (ShipDrawInfo ship : drawInfo.shipsInfo) {

                if(!observer && ship.id == this.id) {
                    this.shipDrawInfo.setShipDrawInfo(ship);
                }

                for (MyPoint location : ship.bulletLocations) {

                    BufferedImage image = BulletType.getImage(ship.bulletType.get(ship.bulletLocations.indexOf(location)));
                    g.drawImage(image, (int) location.x, (int) location.y, Bullet.SIZE.width, Bullet.SIZE.height, null);

                }

                for (MyPoint location : ship.missileLocations) {

                    g.drawImage(missileIcon, (int) location.x, (int) location.y,  Missile.SIZE.width * 2, Missile.SIZE.height * 2 , null);


                }


                if (!ship.hit){

                    g.drawImage(ShipImage, (int) ship.shipLocation.x, (int)ship.shipLocation.y, Ship.SIZE.width, Ship.SIZE.height, null);

                }

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

        }

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

        waveLabel = new JLabel("");
        waveLabel.setForeground(Color.CYAN);
        waveLabel.setFont(new Font("Serif", Font.PLAIN, 90));
        waveLabel.setBounds(680 , 400 , 400, 90);

        missileLabel = new JLabel("  " + shipDrawInfo.bombNumber);
        missileLabel.setForeground(Color.white);
        missileLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        missileLabel.setIcon(new ImageIcon(missileIcon));
        missileLabel.setBounds(10 , 850 , 120, 40);

        lifeLabel = new JLabel("  " + shipDrawInfo.shipLife);
        lifeLabel.setForeground(Color.white);
        lifeLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        lifeLabel.setIcon(new ImageIcon(heartIcon));
        lifeLabel.setBounds(10 , 890 , 120 , 40);

        coinLabel = new JLabel("  " + shipDrawInfo.coinNumber);
        coinLabel.setForeground(Color.white);
        coinLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        coinLabel.setIcon(new ImageIcon(coinIcon));
        coinLabel.setBounds(10 , 930 , 120 , 40);

        pointLabel = new JLabel("  " + shipDrawInfo.points);
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


        if(!waveLabel.isVisible() && drawInfo.changing) {

                waveLabel.setVisible(true);
                waveLabel.setText("wave: " + drawInfo.waveNum);

        }

        if(!drawInfo.changing && waveLabel.isVisible())
            waveLabel.setVisible(false);

        missileLabel.setText("  " + shipDrawInfo.bombNumber);
        lifeLabel.setText("  " + shipDrawInfo.shipLife);
        coinLabel.setText("  " + shipDrawInfo.coinNumber);
        pointLabel.setText("  " + shipDrawInfo.points);
        panel.revalidate();

    }

    void setDrawInfo(DrawInfo info) {


        for (JLabel label:shipsIdLabels) {

            panel.remove(label);

        }
        shipsIdLabels.clear();

        for (ShipDrawInfo ship:info.shipsInfo) {

            JLabel label = new JLabel("  " + ship.id);
            label.setForeground(Color.white);
            label.setFont(new Font("Serif", Font.PLAIN, 30));
            label.setBounds((int) ship.shipLocation.x + Ship.SIZE.width / 7, (int)ship.shipLocation.y + Ship.SIZE.height / 2 , 120, 40);
            shipsIdLabels.add(label);
            if(!ship.hit)panel.add(label);

        }

        drawInfo.setDrawInfo(info);

    }

    private void heatBar(Graphics g) {

        g.setColor(Color.RED);
        g.fillRect(10 , 10 , (int)(2 * shipDrawInfo.temperature) ,22);
        g.setColor(Color.white);
        g.drawRect(10 , 10 , 205 ,22);
    }


    private static BufferedImage eggImage;
    private static BufferedImage ShipImage;
    private static BufferedImage[] coinImages = new BufferedImage[16];
    private static BufferedImage[] chickenImages = new BufferedImage[14];
    public static BufferedImage[] bossImages = new BufferedImage[4];

    private static void setImages(){

        try {
            setBossImages();
            setCoinImages();
            setChickenImages();
            ShipImage = ImageIO.read(new File("Images/SpaceShip/RedSpaceShip.png"));
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
