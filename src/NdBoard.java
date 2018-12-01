import java.util.ArrayList;

public class NdBoard {
    private ArrayList<Integer> dimensions;
    private ArrayList<Block[]> board;
    private int numberBombs;
    private int numberHidden;
    private int numberFlagged;
    private boolean hasNotLost;

    public NdBoard(ArrayList<Integer> dimensions, int numberBombs) {
        this.dimensions = dimensions;
        this.board = new ArrayList<Block[]>();
        this.numberBombs = numberBombs;
        numberHidden = 1;
        for (int dimension : dimensions) {
            numberHidden *= dimension;
        }
        numberFlagged = 0;

        for (int dimension : dimensions) {
            board.add(new Block[dimension]);
        }

        // place the bombs in the board
        int bombsLeftToPlace = numberBombs;
        do {
            int dimensionIndex = (int)(Math.random() * board.size());
            int positionIndex = (int)(Math.random() * board.get(dimensionIndex).length);
            if (board.get(dimensionIndex)[positionIndex] == null) {
                board.get(dimensionIndex)[positionIndex] = new Bomb();
                bombsLeftToPlace--;
            }
        } while (bombsLeftToPlace > 0);

        // places empty blocks
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).length; j++) {
                if (board.get(i)[j] == null) {
                    board.get(i)[j] = new Empty(findBombNumber(i, j));
                }
            }
        }
    }

    private int findBombNumber(int dimension, int index) {
        int bombNumber = 0;
        for (int i = dimension - 1; i <= dimension + 1; i++) {
            for (int j = index - 1; j <= index + 1; j++) {
                try {
                    if (board.get(dimension)[index].getClass() == Bomb.class) {
                        bombNumber++;
                    }
                } catch (NullPointerException ignored) {
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }
        return bombNumber;
    }
}
