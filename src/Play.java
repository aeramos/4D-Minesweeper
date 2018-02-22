import java.util.Scanner;

public class Play {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("enter length");
        int l = s.nextInt();
        System.out.println("enter width");
        int w = s.nextInt();
        System.out.println("enter height");
        int h = s.nextInt();
        System.out.println("enter time"); // 4th dimension
        int t = s.nextInt();
        int b;
        while (true) {
            System.out.println("enter number of bombs");
            b = s.nextInt();
            // if there are not more bombs than spaces available
            if (b < l * w * h * t && b >= 0) {
                break;
            } else {
                System.out.println("Invalid number of bombs\n");
            }
        }
        Board x = new Board(l, w, h, t, b);
        x.print2DSample();
        s.close();
    }
}