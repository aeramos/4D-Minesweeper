import java.util.Scanner;

public class Play {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter length");
        int l = scanner.nextInt();
        System.out.println("enter width");
        int w = scanner.nextInt();
        System.out.println("enter height");
        int h = scanner.nextInt();
        System.out.println("enter time"); // 4th dimension
        int t = scanner.nextInt();
        int b;
        while (true) {
            System.out.println("enter number of bombs");
            b = scanner.nextInt();
            // if there are not more bombs than spaces available
            if (b < l * w * h * t && b > 0) {
                break;
            } else {
                System.out.println("Invalid number of bombs\n");
            }
        }
        Board board = new Board(l, w, h, t, b);
        board.print2DSample();
        scanner.close();
    }
}
