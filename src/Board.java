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
            if (board[getIndex(position)] == null) {
                board[getIndex(position)] = new Bomb();
                bombsLeftToPlace--;
            }
        } while (bombsLeftToPlace > 0);

        // places empty blocks
        for (int i = 0; i < board.length; i++) {
            if (board[i] == null) {
                board[i] = new Empty(getBombNumber(getPosition(i), new int[n], 0));
            }
        }
    }

    // finds the number of bombs touching the given position
    private int getBombNumber(int[] position, int[] counters, int dimension) {
        int number = 0;
        if (dimension == n) {
            try {
                if (board[getIndex(counters)].getClass() == Bomb.class) {
                    number++;
                }
            } catch (NullPointerException ignored) {
            }
        } else {
            // iterate through the +- around the position in each dimension
            for (counters[dimension] = position[dimension] - 1; counters[dimension] <= position[dimension] + 1; counters[dimension]++) {
                // don't waste time on invalid positions that are out of the bounds
                if (counters[dimension] >= 0 && counters[dimension] < dimensions[dimension]) {
                    number += getBombNumber(position, counters, dimension + 1);
                }
            }
        }
        return number;
    }

    // based on Bowen's original method to convert from n-dimensional to 1D array
    private int getIndex(int[] position) {
        int index = 0;   // index in 1d array
        int multiplier = 1;

        // reverse iteration through dimensions
        // calculate value from least significant bit to most significant bit
        // method works like a binary to decimal converter
        for (int i = n - 1; i >=0; i--) {
            index += multiplier * position[i];
            multiplier *= dimensions[i];
        }
        return index;
    }

    private int[] getPosition(int index) {
        int[] position = new int[n];
        int[] multipliers = new int[n];
        multipliers[n-1] = 1;

        // get the multipliers we're going to use
        // like converting from decimal to binary but with variable bases
        for (int i = n - 2; i >= 0; i--) {
            multipliers[i] = multipliers[i+1] * dimensions[i+1];
        }

        // assemble the position. each index has its own multiplier
        for (int i = 0; i < n; i++) {
            position[i] = index / multipliers[i];
            index -= position[i] * multipliers[i];
        }
        return position;
    }

    public static void main(String... args) {
        Board board = new Board(new int[]{3,3,3}, 3);
    }
}
