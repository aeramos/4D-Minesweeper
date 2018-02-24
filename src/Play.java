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
        board.print4D();
        while (true) {
            System.out.print("Position: ");
            int chosenLength = scanner.nextInt();
            int chosenWidth = scanner.nextInt();
            int chosenHeight = scanner.nextInt();
            int chosenTime = scanner.nextInt();
            if (board.exists(chosenLength, chosenWidth, chosenHeight, chosenTime)) {
                Boolean gameStatus = null;
                boolean goodInput;
                do {
                    goodInput = true;

                    System.out.print("Action (c, f, ?): ");
                    String action = scanner.next();

                    if (action.equals("c")) {
                        gameStatus = board.reveal(chosenLength, chosenWidth, chosenHeight, chosenTime);
                    } else if (action.equals("f")) {
                        gameStatus = board.flag(chosenLength, chosenWidth, chosenHeight, chosenTime);
                    } else if (action.equals("?")) {
                        board.question(chosenLength, chosenWidth, chosenHeight, chosenTime);
                        gameStatus = null;
                    } else {
                        System.out.println("Invalid action");
                        goodInput = false;
                    }
                } while (!goodInput);
                board.print4D();
                if (gameStatus != null) {
                    if (gameStatus) {
                        System.out.println("You win!");
                    } else {
                        System.out.println("You lose!");
                    }
                    break;
                }
            } else {
                System.out.println("Invalid position\n");
            }
        }
        scanner.close();
    }
}
