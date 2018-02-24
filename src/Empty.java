public class Empty extends Block {
    private int number;

    public Empty(int number) {
        super();
        this.number = number;
    }

    @Override
    public boolean isBomb() {
        return false;
    }

    public int getNumber() {
        return number;
    }
}
