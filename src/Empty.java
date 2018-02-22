public class Empty extends Block {
    private boolean hidden;
    private boolean flag;
    private int bombNumber;

    public Empty(int b) {
        super();
        bombNumber = b;
    }

    public byte click() {
        if(flag)
            return 0;
        hidden = false;
        return 1;
    }
    public boolean isBomb(){
        return false;
    }

    public boolean hasFlag(){
        return flag;
    }

    public int getBombNumber() {
        return bombNumber;
    }

    public boolean isHidden() {
        return hidden;
    }
}