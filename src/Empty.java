public class Empty extends Block {
    private boolean hidden = true;
    private int bombNumber;

    public Empty(int b) {
        bombNumber = b;
    }

    public boolean click() {
        return true;
        // return true bc no boomboom
    }

    public boolean isBomb() {
        return true;
    }

    public void found() {
        hidden = false;
    }

    public int getBombNumber() {
        return bombNumber;
    }

    public boolean isHidden() {
        return hidden;
    }
}