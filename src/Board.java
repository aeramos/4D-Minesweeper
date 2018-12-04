import java.util.Arrays;

public class Board {
    private Block[] board;

    private int[] dimensions;
    private int n;
    private int size;
    private int numberBombs;
    private int numberHidden;
    private int numberFlagged;
    private boolean hasNotLost;

    public Board(int[] dimensions, int numberBombs) {
        int boardSize = 1;
        for (int dimension : dimensions) {
            boardSize *= dimension;
        }
        board = new Block[boardSize];

        size = boardSize;

        this.dimensions = dimensions;
        this.numberBombs = numberBombs;
        n = dimensions.length;
        numberHidden = 1;
        for (int dimension : dimensions) {
            numberHidden *= dimension;
        }
        numberFlagged = 0;

        // place the bombs randomly in the board
        int bombsLeftToPlace = numberBombs;
        do {
            int[] position = new int[dimensions.length];
            for (int i = 0; i < position.length; i++) {
                position[i] = (int)(Math.random() * dimensions[i]);
            }
            if (get(position) == null) {
                set(position, new Bomb());
                bombsLeftToPlace--;
            }
        } while (bombsLeftToPlace > 0);

        //places empty blocks
        // nestedLoopOperation and performOperation gotten from:
        // https://stackoverflow.com/a/19406536/4644817

        int[] length = new int[n]; // length of each dimension
        int[] counters = new int[n]; // count up to the given length
        Arrays.fill(counters, 0);
        for (int i = 0; i < length.length; i++) {
            length[i] = dimensions[i];
        }
        nestedLoopOperation(counters, length, 0);
        System.out.println("dine!");
    }

    private void nestedLoopOperation(int[] counters, int[] length, int level) {
        if (level == counters.length) { //
            performOperation(counters);
        } else {
            for (counters[level] = 0; counters[level] < length[level]; counters[level]++) {
                nestedLoopOperation(counters, length, level + 1);
            }
        }
    }

    private void performOperation(int counters[]) {
        if (get(counters) == null) {
            set(counters, new Empty(findBombNumber(counters)));
        }
    }

    // finds the number of bombs touching the given position
    private int findBombNumber(int[] position) {
        return 0;
    }

    // get and set use Bowen's method to condense n-d arrays into a 1-d array
    private Block get(int[] D) {
        int index = 0;
        int runningProduct = 1;
        for(int i = 0; i < n; i++){
            runningProduct *= dimensions[i];
            index += D[i] * size / runningProduct;
        }
        return board[index];
    }

    private void set(int[] D, Block block) {
        int index = 0;
        int runningProduct = 1;
        for (int i = 0; i < n; i++) {
            runningProduct *= dimensions[i];
            index += D[i] * size/runningProduct;
        }
        board[index] = block;
    }

    public static void main(String... args) {
        Board board = new Board(new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3}, 3);
    }
}
