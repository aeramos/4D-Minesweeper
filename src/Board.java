public class Board {
    private int length;
    private int width;
    private int height;
    private int time;
    public Block[][][][] board;
    private int numberBombs;
    private int numberHidden;
    private int numberFlagged;
    private boolean hasNotLost = true;

    public Board(int length, int width, int height, int time, int numberBombs) {
        this.board = new Block[length][width][height][time];
        this.length = length;
        this.width = width;
        this.height = height;
        this.time = time;
        this.numberBombs = numberBombs;
        this.numberHidden = length * width * height * time;
        this.numberFlagged = 0;

        int bombsLeftToPlace = numberBombs;
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


    private int findBombNumber(int length, int width, int height, int time) {
        int number = 0;
        for (int l = length - 1; l <= length + 1; l++) {
            for (int w = width - 1; w <= width + 1; w++) {
                for (int h = height - 1; h <= height + 1; h++) {
                    for (int t = time - 1; t <= time + 1; t++) {
                        try {
                            if (board[l][w][h][t].getClass() == Bomb.class) {
                                number++;
                            }
                        } catch (NullPointerException ignored) {
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                    }
                }
            }
        }
        return number;
    }

    public void print4D() {
        for (int t = 0; t < time; t++) {
            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    for (int l = 0; l < length; l++) {
                        System.out.print(getOutput(board[l][w][h][t]) + " ");
                    }
                    System.out.print("  ");
                }
                System.out.println();
            }
            System.out.print("\n");
        }
    }

    private String getOutput(Block block) {
        if (block.isHidden() && hasNotLost) {
            if (block.isFlag()) {
                return "FF";
            } else if (block.isQuestion()) {
                return "??";
            } else {
                return "XX";
            }
        } else {
            if (block.getClass() == Bomb.class) {
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

    public boolean exists(int length, int width, int height, int time) {
        try {
            return board[length][width][height][time] != null;
        } catch (Exception e) {
            return false;
        }
    }

    // returns true if player wins, false if player loses, null otherwise
    public Boolean reveal(int length, int width, int height, int time) {
        Block block = board[length][width][height][time];
        if (!block.isHidden()) {
            return null;
        } else {
            block.setQuestion(false);

            block.setHidden(false);
            numberHidden--;

            if (block.isFlag()) {
                block.setFlag(false);
                numberFlagged--;
            }
        }
        if (block.getClass() == Bomb.class) {
            hasNotLost = false;
            return false;
        } else {
            if (((Empty)block).getNumber() == 0) {
                for (int l = length - 1; l <= length + 1; l++) {
                    for (int w = width - 1; w <= width + 1; w++) {
                        for (int h = height - 1; h <= height + 1; h++) {
                            for (int t = time - 1; t <= time + 1; t++) {
                                if (!(l == length && w == width && h == height && t == time)) {
                                    try {
                                        Boolean result = reveal(l, w, h, t);
                                        if (result != null) {
                                            return result;
                                        }
                                    } catch (IndexOutOfBoundsException ignored) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (numberHidden == numberBombs && numberFlagged == numberBombs) {
            return true;
        } else {
            return null;
        }
    }

    public Boolean flag(int length, int width, int height, int time) {
        if (board[length][width][height][time].isHidden()) {
            if (!board[length][width][height][time].isFlag()) {
                board[length][width][height][time].setFlag(true);
                numberFlagged++;
                if (numberHidden == numberBombs && numberFlagged == numberBombs) {
                    return true;
                }
            } else {
                board[length][width][height][time].setFlag(false);
                numberFlagged--;
            }
        }
        return null;
    }

    // toggle question mark
    public void question(int length, int width, int height, int time) {
        if (board[length][width][height][time].isHidden()) {
            if (!board[length][width][height][time].isQuestion()) {
                board[length][width][height][time].setQuestion(true);
            } else {
                board[length][width][height][time].setQuestion(false);
            }
        }
    }
}
