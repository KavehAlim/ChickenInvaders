import DataBase.PlayerDataBase;
import Menu.*;

import javax.swing.*;
import java.awt.*;

public class ChickenInvaders {

    public static void main(String[] args) {

        PlayerDataBase.initialWork();
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setSize(new Dimension(1680, 975));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        new LoginMenu(frame);

    }

}
