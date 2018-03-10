import javax.swing.*;
import java.awt.*;

public class HyperSweeper {
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int length, width, height, time, bombs;
    public static Board hyperBoard;
    public static Boolean win;

    public static void main(String[] args) {
        length = Play.sizes[0];
        width = Play.sizes[1];
        height = Play.sizes[2];
        time = Play.sizes[3];
        bombs = Play.sizes[4];
        hyperBoard = new Board(length,width,height,time,bombs);
        JFrame f = new JFrame();
        Screen s = new Screen(length,width,height,time);
        f.add(s);
        f.setUndecorated(true);
        f.setSize(screenSize);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}