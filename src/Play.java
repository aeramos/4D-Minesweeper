import java.util.InputMismatchException;
import java.util.Scanner;

public class Play {

    public static Board board;
    public static int[] sizes;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to 4D Minesweeper");
        System.out.println("Select your game preferences first!\n" +
                "Use WASD keys to move around, \nSpace to go straight upwards, and \nShift to go straight downwards.\n" +
                "Left-click to clear the block; \nRight-click a grey block to flag it (it'll become red); \nRight-click a red block to question mark it (it'll become blue).\n");

        String setupErrorMessage = "Invalid number: Must be an integer greater than 0";
        int l = getInt(scanner, 1, Integer.MAX_VALUE, "Length (left-right): ", setupErrorMessage);
        int w = getInt(scanner, 1, Integer.MAX_VALUE, "Width (up-down): ", setupErrorMessage);
        int h = getInt(scanner, 1, Integer.MAX_VALUE, "Height (cube depth): ", setupErrorMessage);
        int t = getInt(scanner, 1, Integer.MAX_VALUE, "Fourth Dimensional Girth: ", setupErrorMessage);
        int b = getInt(scanner, 1, (l * w * h * t) - 1, "Number of bombs: ", "Invalid number: Must be an integer between 1 and " + ((l * w * h * t) - 1));
        sizes = new int[]{l,w,h,t,b};

        board = new Board(l, w, h, t, b);
        HyperSweeper.main(null);

        boolean firstTime = true;
        while (true) {
            System.out.print("Position: ");
            int chosenLength = -1, chosenWidth = -1, chosenHeight = -1, chosenTime = -1;
            try {
                chosenLength = scanner.nextInt();
                chosenWidth = scanner.nextInt();
                chosenHeight = scanner.nextInt();
                chosenTime = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
            }
            if (board.exists(chosenLength, chosenWidth, chosenHeight, chosenTime)) {
                Boolean gameStatus = null;
                boolean goodInput;
                do {
                    goodInput = true;

                    if (firstTime) {
                        firstTime = false;
                        System.out.println("r = reveal");
                        System.out.println("f = flag");
                        System.out.println("? = question");
                    }
                    System.out.print("Action (r, f, ?): ");
                    String action = scanner.next();

                    if (action.equals("r")) {
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
                System.out.println("Invalid position");
                System.out.println("Length = " + l);
                System.out.println("Width = " + w);
                System.out.println("Height = " + h);
                System.out.println("Time = " + t + '\n');
            }
        }
        scanner.close();
    }

    private static int getInt(Scanner scanner, int minimum, int maximum, String prompt, String error) {
        int number = minimum - 1;
        while (number < minimum || number > maximum) {
            System.out.print(prompt);
            try {
                number = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(error);
            }
        }
        return number;
    }
}
