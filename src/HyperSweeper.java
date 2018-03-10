import javax.swing.*;
import java.awt.*;

public class HyperSweeper {
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int length = 3, width = 3, height = 3, time = 3, bombs = 3;
    public static Board hyperBoard;

    public static void main(String[] args) {
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