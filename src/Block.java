public abstract class Block {
    private boolean hidden;
    private boolean flag;
    private boolean question;

    public Block() {
        hidden = true;
        flag = false;
        question = false;
    }

    public abstract boolean isBomb();

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
        if (flag) {
            this.question = false;
        }
    }

    public boolean isQuestion() {
        return question;
    }

    public void setQuestion(boolean question) {
        this.question = question;
        if (question) {
            this.flag = false;
        }
    }
}
