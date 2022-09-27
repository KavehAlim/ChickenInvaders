package Menu;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Button extends JButton {

    private static ImageIcon buttonBackground;

    public Button(String text){

        super(text);
        buttonBackground = new ImageIcon("Images/Menu/ButtonBackgroundImage.png");
        this.setIcon(buttonBackground);
        setForeground(Color.black);
        setFont(new Font("Impact" , Font.PLAIN , 30));
        setBorder(BorderFactory.createEmptyBorder());
        setOpaque(false);
        setHorizontalTextPosition(SwingConstants.CENTER);

    }

}
