package gui;

import javax.swing.*;
import java.awt.*;

public class HyperSweeper {
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void main(String[] args) {
        JFrame f = new JFrame();
        Screen s = new Screen();
        f.add(s);
        f.setUndecorated(true);
        f.setSize(screenSize);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
