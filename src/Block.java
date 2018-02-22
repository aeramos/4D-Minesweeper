public abstract class Block {
    private boolean flag;
    public Block(){
        flag = false;
    }
    public abstract byte click();
    /*
    * 0 = nothing happens
    * 1 = block revealed
    * 2 = mine triggered
    * */

    public abstract boolean isBomb();
    public abstract boolean hasFlag();
}