package MultiPlayer.Client;


import Model.MyPoint;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ClientListener implements MouseMotionListener, MouseListener, KeyListener {


        private boolean leftClick = false;
        private boolean rightClick = false;
        private boolean isPaused = false;
        private MyPoint mouseLocation;

        ClientListener(){

            mouseLocation = new MyPoint(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y);
            moveMouse(new Point(800 + 50 * new Random().nextInt(10), 900));

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

                //                Frame.getContentPane().removeAll();
//                EscapeMenu EscapeMenu = new EscapeMenu();
//                Frame.add(EscapeMenu);
//                Frame.setVisible(true);
                isPaused = !this.isPaused;


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
                leftClick = true;
            }
            if ( e.getButton() == MouseEvent.BUTTON3) {
                rightClick = true;
            }

            mouseLocation.x = e.getPoint().x;
            mouseLocation.y = e.getPoint().y;

        }

        @Override
        public void mouseReleased(MouseEvent e) {

            if (e.getButton() == MouseEvent.BUTTON1) {
                leftClick = false;
            }
            if ( e.getButton() == MouseEvent.BUTTON3) {
                rightClick = false;
            }

            mouseLocation.x = e.getPoint().x;
            mouseLocation.y = e.getPoint().y;

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

            mouseLocation.x = e.getPoint().x;
            mouseLocation.y = e.getPoint().y;


        }

        @Override
        public void mouseMoved(MouseEvent e) {

            mouseLocation.x = e.getPoint().x;
            mouseLocation.y = e.getPoint().y;


        }

        private void moveMouse(Point p) {

            try {
                Robot robot = new Robot();
                robot.mouseMove(p.x, p.y);
            } catch (AWTException e) {
                e.printStackTrace();
            }

        }

        boolean isLeftClick() {
            return leftClick;
        }

        boolean isRightClick() {
            return rightClick;
        }

        MyPoint getMouseLocation() {
            return mouseLocation;
        }

        boolean isPaused() {
            return isPaused;
        }

        void setPaused(boolean b) {
                isPaused = b;
        }
}
