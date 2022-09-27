package View;

import Controller.Game;
import Menu.LoginMenu;

import javax.swing.*;
import java.awt.*;

public class View {

    private BattleFieldView BattleFieldView;
    public JFrame frame;
    static Dimension frameSize;

    public View(Game Game, JFrame frame‌‌‌){

        Game.setView(this);
        this.frame = frame‌‌‌;
        frameSize = this.frame.getSize();
        BattleFieldView = new BattleFieldView(frame, Game);
        this.frame.repaint();
        this.frame.revalidate();

    }

    public void remove() {
        frame.remove(BattleFieldView);
        frame.getContentPane().setCursor(Cursor.getDefaultCursor());
    }
}
