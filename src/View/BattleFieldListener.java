package View;

import Controller.MouseStatus;
import Model.MyPoint;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;

public class BattleFieldListener implements /*KeyListener, MouseInputListener*/ MouseMotionListener, MouseListener, KeyListener {

    private MouseStatus mouseStatus = new MouseStatus();
//    private boolean LeftClick = false;
//    private boolean RightClick = false;
//    private boolean isPaused = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            if (!mouseStatus.isPaused()) {

                mouseStatus.setPaused(true);

//                Frame.getContentPane().removeAll();
//                EscapeMenu EscapeMenu = new EscapeMenu();
//                Frame.add(EscapeMenu);
//                Frame.setVisible(true);

            }

            else {

                mouseStatus.setPaused(false);

            }

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseStatus.setLeftClick(true);
        }
        if ( e.getButton() == MouseEvent.BUTTON3) {
            mouseStatus.setRightClick(true);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseStatus.setLeftClick(false);
        }
        if ( e.getButton() == MouseEvent.BUTTON3) {
            mouseStatus.setRightClick(false);
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    MouseStatus getMouseStatus() {
        return mouseStatus;
    }

    void setMouseLocation(Point mouseLocation){

        mouseStatus.setMouseLocation(new MyPoint(mouseLocation.x, mouseLocation.y));
    }

    void setPaused(boolean paused){

        mouseStatus.setPaused(paused);

    }
}
