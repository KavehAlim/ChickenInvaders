package Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EscapeMenu extends JPanel {


    private Button continueBtn;
    private Button quit;
    private Button addBoss;
    private Button addChickenGroup;
    public JFrame frame;

    protected EscapeMenu(){


        frame = new JFrame();
        frame.add(this);
        this.frame.setBounds(440,240,880,480);
        this.frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setBackground(Color.lightGray);
        this.frame.setVisible(true);
//        setBackGroundImages();
        prepareButtons();
        this.addKeyListener(new EscapeListener());

        this.revalidate();
        this.frame.revalidate();

    }


    public EscapeMenu(JFrame frame){

        this.frame = frame;
        this.setLayout(null);
        this.setBounds(440,240,880,480);
        this.frame.add(this);
        this.frame.setBounds(440,240,880,480);
        this.setBackground(Color.RED);
//        setBackGroundImages();
        prepareButtons();

        this.revalidate();
        this.frame.revalidate();

    }


    class EscapeListener implements KeyListener {


        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){

                continueBtn();

            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    }

    private void prepareButtons() {

        prepareContinue();
        prepareQuit();
        prepareAddChicken();
        prepareAddBoss();

    }

    private void prepareContinue(){

        continueBtn = new Button("Continue");

        continueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                continueBtn();
            }
        });

        continueBtn.setBounds(330 , 0 ,220 , 120);

        this.add(continueBtn);
    }

    private void prepareQuit(){


        quit = new Button("Quit");

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });

        quit.setBounds(330 , 360 ,220 , 120);

        this.add(quit);


    }

    private void prepareAddChicken(){


        addChickenGroup = new Button("Add Chicken");

        addChickenGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addChickenGroup();
            }
        });

        addChickenGroup.setBounds(330 , 240 ,220 , 120);

        this.add(addChickenGroup);

    }

    private void prepareAddBoss(){

        addBoss = new Button("Add Boss");

        addBoss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBoss();
            }
        });

        addBoss.setBounds(330 , 120 ,220 , 120);

        this.add(addBoss);

    }

    public void continueBtn(){


    }

    public void quit(){

        frame.setVisible(false);

    }


    public void addChickenGroup(){


    }

    public void addBoss(){


    }

}
