package View;

import Controller.*;
import DataBase.PlayerDataBase;
import Menu.EscapeMenu;
import Menu.LoginMenu;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

public class BattleFieldView extends JPanel implements ActionListener {

    private final static Dimension FullScreen = new Dimension(1680, 975);
    private BattleFieldListener BattleFieldListener;
    private Game game;

    private SinglePlayerDraw draw;
    private static BufferedImage BackgroundImage;

    private final static int Delay = 5;

    private Timer time = new Timer(Delay, this);

    private JFrame viewFrame;

    {
        setBackgroundImage();
    }

    BattleFieldView(JFrame frame, Game game) {

        viewFrame = frame;
        viewFrame.add(this);
        hideCursor();
        this.setBounds(0, 0, (int) View.frameSize.getWidth(), (int) View.frameSize.getHeight());
        this.BattleFieldListener = new BattleFieldListener();
        this.addKeyListener(BattleFieldListener);
        this.addMouseListener(BattleFieldListener);
        this.game = game;
        draw = new SinglePlayerDraw(game.getDrawInfo(), this);

        moveMouse(new Point(Game.FullScreen.width / 2 , (int) (Game.FullScreen.height * 0.8)));

        time.start();

        this.viewFrame.repaint();
        this.viewFrame.revalidate();

    }

    private void hideCursor() {

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

        // Set the blank cursor to the JFrame.
        try {
            viewFrame.getContentPane().setCursor(blankCursor);
        }
        catch (Exception e){
            //can't hide cursor
        }

    }

    class Escape extends EscapeMenu {

        Escape() {

            time.stop();

        }

        @Override
        public void continueBtn() {
            super.continueBtn();
            frame.setVisible(false);
            BattleFieldListener.setPaused(false);
            moveMouse(new Point((int)BattleFieldListener.getMouseStatus().getMouseLocation().x, (int)BattleFieldListener.getMouseStatus().getMouseLocation().y));
            time.start();
        }

        @Override
        public void quit() {

            super.quit();
            Player p = null;

//            GameStatus gs = JSON.read();
//
//            for (Player player: gs.getPlayers()){
//
//                if(player.getName().equals(game.name)){
//
//                    p = player;
//                    player.setLastGame(game);
//
//                }
//
//            }
//
//            JSON.write(gs);

            p = new Player(game.getName());
            p.setLastGame(game);
            PlayerDB pdb = PlayerDB.createPlayerDB(p);
            PlayerDataBase.update(pdb);


            closeGame();
            viewFrame.getContentPane().setCursor(Cursor.getDefaultCursor());
            new LoginMenu(viewFrame);

        }

        @Override
        public void addChickenGroup() {
            super.addChickenGroup();

            try {
                String directory =(JOptionPane.showInputDialog("Enter Directory:"));
                String className =(JOptionPane.showInputDialog("Enter Class Name:"));
                game.addChickens(directory,className);
            } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException | IllegalAccessException | InstantiationException | MalformedURLException e) {
                JOptionPane.showMessageDialog(null, "please enter valid values");
            }
        }

        @Override
        public void addBoss() {
            super.addBoss();
            try {
                String directory =(JOptionPane.showInputDialog("Enter Directory:"));
                String className =(JOptionPane.showInputDialog("Enter Class Name:"));
                game.addBoss(directory,className);
            } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException | IllegalAccessException | InstantiationException | MalformedURLException e) {
                JOptionPane.showMessageDialog(null, "please enter valid values");
            }

        }

//        @Override
//        protected void finalize() throws Throwable {
//            super.finalize();
//            time.start();
//        }
    }

    private void closeGame(){


        viewFrame.remove(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();

    }

    void toDo(Graphics g){

        game.draw();

        draw.draw(g);

//        Game.boss.draw(g);

        game.move(Delay);

        mouseStatusUpdate();

    }

    private void mouseStatusUpdate() {

        BattleFieldListener.setMouseLocation(MouseInfo.getPointerInfo().getLocation());

        game.setMouseStatus(BattleFieldListener.getMouseStatus());

//        game.mouseStatus.setRightClick(BattleFieldListener.isRightClick());
//
//        game.mouseStatus.setLeftClick(BattleFieldListener.isLeftClick());

//        game.mouseStatus.setPaused(BattleFieldListener.isPaused());

//        if(BattleFieldListener.isPaused()) {
//            GameStatus gameStatus = JSON.read();
//
//            ArrayList<Player> players = new ArrayList<>();
//            if (gameStatus != null) {
//                for (Player player : gameStatus.getPlayers()) {
//                    if (player.getName().equals(game.name)) {
//
//                        Player p = new Player(game.name);
//                        p.setLastGame(game);
//                        players.add(p);
//
//                    } else players.add(player);
//                }
//
//                JSON.write(new GameStatus(gameStatus.scores, players));
//
//                System.exit(0);
//            }

           if(BattleFieldListener.getMouseStatus().isPaused()) new Escape();

//        }
//
//        game.mouseStatus.setMouseLocation(new MyPoint(MouseInfo.getPointerInfo().getLocation().x , MouseInfo.getPointerInfo().getLocation().y));
    }

    public static void drawBackground(Graphics g){

        g.drawImage(BackgroundImage, 0, 0 , FullScreen.width, FullScreen.height, null);


    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        toDo(g);

    }

    public static void setBackgroundImage(){

        try {
            BackgroundImage = ImageIO.read(new File("Images/Menu/BackGround.jpg"));
        }catch ( Exception e) {
            e.printStackTrace();
        }

    }

    public void addNotify(){

        super.addNotify();
        requestFocus();

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        viewFrame.getContentPane().setCursor(Cursor.getDefaultCursor());
    }


    private void moveMouse(Point p) {

        try {
            Robot robot = new Robot();
            robot.mouseMove(p.x, p.y);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

}
