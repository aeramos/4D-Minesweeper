public class Bomb extends Block {
    private boolean flag;
    public Bomb(){
        super();
    }
    public byte click() {
        if(flag)
            return 0;
        return 2;
        //return 2 bc big boomboom
    }

    public boolean isBomb(){
        return true;
    }
    public boolean hasFlag(){
        return flag;
    }
}
