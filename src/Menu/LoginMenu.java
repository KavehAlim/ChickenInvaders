package Menu;

import Controller.GameStatus;
import Controller.JSON;
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
import java.util.ArrayList;

public class LoginMenu extends JPanel {

    private Button choose;
    private Button exit;
    private Button remove;
    private Button add;
    private ArrayList<Player> players = new ArrayList<>();
//    private ArrayList<PlayerDB> players = new ArrayList<>();
    private ArrayList<RadioButton> playersRadioButton = new ArrayList<>();
    private ButtonGroup playersButton = new ButtonGroup();
    private int numberOfPlayers;
    private GameStatus gameStatus;
    private Player currentPlayer;
    private JFrame frame;

    private static BufferedImage backGroundImage;
    private static BufferedImage headTitleImage;

    public LoginMenu(JFrame frame){

        super();

        this.frame = frame;
        this.frame.setSize(new Dimension(1680, 975));
        loadPlayers();
        this.setLayout(null);
        this.setBounds(0,0,frame.getWidth(),frame.getHeight());
        this.frame.add(this);

        setBackGroundImages();
        prepareButtons();

        this.frame.revalidate();
        this.frame.repaint();


    }

    private void loadPlayers(){


//        gameStatus = JSON.read();
//        if(gameStatus != null)
//            players.addAll(gameStatus.getPlayers());
//        numberOfPlayers = players.size();
//        System.out.println(players.size());
        ArrayList<PlayerDB> pdb = new ArrayList<>();
        pdb.addAll(PlayerDataBase.getPlayers());
        for(PlayerDB playerDB: pdb){

            players.add(PlayerDB.createPlayer(playerDB));

        }

        numberOfPlayers = players.size();

        redesignPlayersButton();

    }

    private void savePlayers(){

//        if(gameStatus != null)
//            JSON.write(new GameStatus(gameStatus.scores, players));
//        else
//            JSON.write(new GameStatus(null, players));


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

        prepareAdd();
        prepareChoose();
        prepareExit();
        prepareRemove();

    }

    private void prepareExit(){

        exit = new Button("EXIT");

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        exit.setBounds(40 , 800 ,400 , 200);

        this.add(exit);

    }

    private void prepareAdd(){

        add = new Button("ADD");

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add();
            }
        });

        add.setBounds(440 , 800 ,400 , 200);

        this.add(add);


    }

    private void prepareRemove(){

        remove = new Button("REMOVE");

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove();
            }
        });

        remove.setBounds(840 , 800 ,400 , 200);

        this.add(remove);

    }

    private void prepareChoose(){

        choose = new Button("CHOOSE");

        choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choose();
            }
        });

        choose.setBounds(1240 , 800 ,400 , 200);

        this.add(choose);

    }

    private void exit(){

//        System.out.println("exit");
//        savePlayers();

        int answer = JOptionPane.showConfirmDialog(null, new String("Are You Sure?"));
        if(answer == 0) System.exit(0);
    }

    private void add(){

        try {
            String newPlayerName = JOptionPane.showInputDialog("Enter User Name:");

            boolean valid = true;

            for (Player player : players) {

                if (player.getName().equals(newPlayerName)) valid = false;

            }

            if (newPlayerName.equals("")) valid = false;

            if (valid) {

                numberOfPlayers++;
                int y = 50 * numberOfPlayers - 50 + 100;
                Player p = new Player(newPlayerName);
                RadioButton b = new RadioButton(p.getName());
                b.setBounds(0, y, 300, 50);
                players.add(p);
                playersRadioButton.add(b);
                playersButton.add(b);
                b.setSelected(true);
                this.add(b);
                revalidate();

//            savePlayers();
                PlayerDataBase.add(newPlayerName);

            }

            if (!valid) {

                JOptionPane.showMessageDialog(null, "please enter valid name");

            }

        }

        catch (Exception e){

            JOptionPane.showMessageDialog(null, "please enter valid value");

        }

    }

    private void choose(){

        setSelectedPlayer();
        if(currentPlayer != null) {

            frame.remove(this);
            frame.revalidate();
            frame.repaint();

            new MainMenu(frame , currentPlayer);
        }

        else {

            JOptionPane.showMessageDialog(null, "select player first");

        }

    }

    private void remove(){

        setSelectedPlayer();

        if(currentPlayer != null) {

            numberOfPlayers--;

            removePlayersButton();
            int i = players.indexOf(currentPlayer);
            players.remove(currentPlayer);

            redesignPlayersButton();
            if(i < numberOfPlayers) playersRadioButton.get(i).setSelected(true);
            else if ( numberOfPlayers != 1) playersRadioButton.get(i-1).setSelected(true);
            revalidate();

//            savePlayers();
            PlayerDataBase.delete(currentPlayer.getName());

        }
        else {

            JOptionPane.showMessageDialog(null, "select player first");

        }
    }

    private void setSelectedPlayer(){

        for (int i = 0; i < numberOfPlayers; i++) {

            if(playersRadioButton.get(i).isSelected()){

                currentPlayer = players.get(i);
                return;
            }

        }

        currentPlayer = null;

    }

    private void removePlayersButton(){

        for (RadioButton b: playersRadioButton) {

            this.remove(b);


        }

        playersButton = new ButtonGroup();
        playersRadioButton = new ArrayList<RadioButton>();

    }

    private void redesignPlayersButton(){

        int i = 0;

        for (Player p: players) {

            RadioButton b = new RadioButton(p.getName());
            b.setBounds(0,50 * i + 100,500 ,50);

            playersRadioButton.add(b);
            playersButton.add(b);
            this.add(b);
            i++;
        }

        revalidate();
        repaint();

    }
}
