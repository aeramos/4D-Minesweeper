public class Board {
    private int length;
    private int width;
    private int height;
    private int time;
    private Block[][][][] board;
    private int bombs;

    public Board(int length, int width, int height, int time, int bombs) {
        this.board = new Block[length][width][height][time];
        this.length = length;
        this.width = width;
        this.height = height;
        this.time = time;
        this.bombs = bombs;

        int bombsLeftToPlace = bombs;
        do {
            int lengthIndex = (int)(Math.random() * length);
            int widthIndex = (int)(Math.random() * width);
            int heightIndex = (int)(Math.random() * height);
            int timeIndex = (int)(Math.random() * time);
            if (board[lengthIndex][widthIndex][heightIndex][timeIndex] == null) {
                board[lengthIndex][widthIndex][heightIndex][timeIndex] = new Bomb();
                bombsLeftToPlace--;
            }
        } while (bombsLeftToPlace > 0);

        //places all empty blocks
        for (int l = 0; l < length; l++) {
            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    for (int t = 0; t < time; t++) {
                        if (board[l][w][h][t] == null) {
                            board[l][w][h][t] = new Empty(findBombNumber(l, w, h, t));
                        }
                    }
                }
            }
        }
    }

    public int findBombNumber(int length, int width, int height, int time) {
        int number = 0;
        for (int l = length - 1; l <= length + 1; l++) {
            for (int w = width - 1; w <= width + 1; w++) {
                for (int h = height - 1; h <= height + 1; h++) {
                    for (int t = time - 1; t <= time + 1; t++) {
                        try {
                            if (board[l][w][h][t].isBomb()) {
                                number++;
                            }
                        } catch (NullPointerException | IndexOutOfBoundsException ignored) {
                        }
                    }
                }
            }
        }
        return number;
    }

    public void print1D(int width, int height, int time) {
        for (int l = 0; l < length; l++) {
            System.out.print(getOutput(board[l][width][height][time]));
        }
    }

    public void print2D(int height, int time) {
        for (int l = 0; l < length; l++) {
            for (int w = 0; w < width; w++) {
                System.out.print(getOutput(board[l][w][height][time]));
            }
            System.out.println();
        }
    }

    public void print3D(int time) {
        for (int l = 0; l < length; l++) {
            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    System.out.print(getOutput(board[l][w][h][time]) + " ");
                }
                System.out.print("   ");
            }
            System.out.println();
        }
    }

    public void print4D() {
        for (int t = 0; t < time; t++) {
            print3D(t);
            System.out.print("\n\n");
        }
    }

    public String getOutput(Block block) {
        if (block.isHidden()) {
            return "XX";
        } else {
            if (block.isFlag()) {
                return "FF";
            } else if (block.isQuestion()) {
                return "??";
            } else if (block.isBomb()) {
                return "BB";
            } else {
                if (((Empty)block).getNumber() >= 10) {
                    return String.valueOf(((Empty)block).getNumber());
                } else {
                    return "0" + String.valueOf(((Empty)block).getNumber());
                }
            }
        }
    }
}
