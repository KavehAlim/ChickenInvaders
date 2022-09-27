package MultiPlayer.Client;
import Controller.Player;
import Reflection.ReflectionExtension;
import View.BattleFieldView;
import Menu.EscapeMenu;
import Menu.MainMenu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

import Menu.MultiPlayerScore;

class ClientView extends JPanel implements ActionListener {

    private JFrame mainFrame;

    ClientListener listener;
    ClientDraw draw;

    private final static int Delay = 5;

    private Timer time = new Timer(Delay, this);
    ClientStatus status;
    private Player player;
    MultiEscape esc;

    private boolean observer;

    private boolean dead;


    ClientView(Player player, int id, JFrame mainFrame, boolean observer) {

        this.player = player;
        this.mainFrame = mainFrame;
        hideCursor();
        this.setLayout(null);
        this.setBounds(0, 0, mainFrame.getWidth(), mainFrame.getHeight());
        this.mainFrame.add(this);

        moveMouse(new Point(400 + new Random().nextInt(10) * 100, 900));

        status = new ClientStatus(false);
        this.listener = new ClientListener();
        this.addKeyListener(listener);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
        this.observer = observer;
        status.observer = observer;
        draw = new ClientDraw(this, id, observer);

        time.start();


        this.mainFrame.revalidate();
        this.mainFrame.repaint();

//        mainFrame = new JFrame();
//        mainFrame.setLayout(null);
//        mainFrame.setSize(new Dimension(1680, 975));
//        mainFrame.setUndecorated(true);
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainFrame.add(this);
//        mainFrame.setVisible(true);


//        status = new ClientStatus();
//        this.listener = new ClientListener();
//        this.addKeyListener(listener);
//        this.addMouseListener(listener);
//        this.addMouseMotionListener(listener);
//
//        draw = new ClientDraw(this, id);
//
//        time.start();

        esc = new MultiEscape();
        esc.frame.setVisible(false);
    }

//    public void newEscape(){
//
//        esc = new MultiEscape();
//
//    }

    void removePanel(){

        mainFrame.remove(this);

    }

    public class MultiEscape extends EscapeMenu {

        MultiEscape() {

//            setVisible(false);
//            time.stop();

        }

        @Override
        public void continueBtn() {

            super.continueBtn();
            status.mouseStatus.setPaused(false);
            listener.setPaused(false);
            moveMouse(new Point((int)status.mouseStatus.getMouseLocation().x, (int)status.mouseStatus.getMouseLocation().y));

        }

        public void remove(){

            frame.setVisible(false);
            time.start();

        }

        @Override
        public void quit() {

            super.quit();
            continueBtn();
            remove();
            removePanel();
            esc = null;
            goToScoreList();

        }


        @Override
        public void addChickenGroup() {


            try {
                String directory = (JOptionPane.showInputDialog("Enter Directory:"));
                String className = (JOptionPane.showInputDialog("Enter Class Name:"));
                File myFile = new File(directory);
                status.newClass = new ReflectionExtension(false, className);
                status.newClass.bytes = new byte[(int) myFile.length()];
                FileInputStream fis;
                fis = new FileInputStream(myFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                bis.read(status.newClass.bytes, 0, status.newClass.bytes.length);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "please enter valid values");
            }

        }

        @Override
        public void addBoss() {

            try {
                String directory = (JOptionPane.showInputDialog("Enter Directory:"));
                String className = (JOptionPane.showInputDialog("Enter Class Name:"));
                File myFile = new File(directory);
                status.newClass = new ReflectionExtension(true, className);
                status.newClass.bytes = new byte[(int) myFile.length()];
                FileInputStream fis;
                fis = new FileInputStream(myFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                bis.read(status.newClass.bytes, 0, status.newClass.bytes.length);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "please enter valid values");
            }

        }

    }

    ClientStatus getStatus() {
        return status;
    }

    void toDo(Graphics g){

        BattleFieldView.setBackgroundImage();
        BattleFieldView.drawBackground(g);
        try {
            if(draw != null)
                draw.draw(g);
        }
        catch (Exception e){
//            System.out.println("concurrent");
        }
        mouseStatusUpdate();

    }

    void goToScoreList() {

        if(!dead) {
            if(esc != null)
                esc.frame.setVisible(false);
            mainFrame.remove(this);
            new MultiPlayerScore(mainFrame, player, draw.drawInfo);
            mainFrame.getContentPane().setCursor(Cursor.getDefaultCursor());
            dead = true;
        }

    }

    private void mouseStatusUpdate() {

        status.mouseStatus.setRightClick(listener.isRightClick());

        status.mouseStatus.setLeftClick(listener.isLeftClick());

        status.mouseStatus.setMouseLocation(listener.getMouseLocation());

        status.mouseStatus.setPaused(listener.isPaused());

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        toDo(g);

    }

    public void addNotify(){

        super.addNotify();
        requestFocus();

    }

    private void moveMouse(Point p) {

        try {
            Robot robot = new Robot();
            robot.mouseMove(p.x, p.y);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }


    private void hideCursor() {

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

        // Set the blank cursor to the JFrame.
        try {
            mainFrame.getContentPane().setCursor(blankCursor);
        }
        catch (Exception e){
//            System.out.println(e.getMessage());
//            System.out.println(e.getCause());
            //can't hide cursor
        }

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        mainFrame.getContentPane().setCursor(Cursor.getDefaultCursor());
    }

}
