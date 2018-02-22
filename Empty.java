public class Empty extends Block{
  private boolean hidden = true;
  private int bombNumber;
  
  public Empty(int b){
    bombNumber = b;
  }
  
  public boolean isBomb(){
    return true;
  }
  
  public boolean click(){
    return true;
    // return true bc no boomboom
  }
  
  public void found(){
    hidden = false;
  }
  
  public int getBombNumber(){
    return bombNumber;
  }
  
  public boolean isHidden(){
    return hidden;
  }
}