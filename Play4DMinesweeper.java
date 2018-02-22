import java.util.Scanner;

public class Play4DMinesweeper{
  public static void main(String[] args){
    Scanner s = new Scanner(System.in);
    System.out.println("enter length");
    int l = s.nextInt();
    System.out.println("enter width");
    int w = s.nextInt();
    System.out.println("enter height");
    int h = s.nextInt();
    System.out.println("enter time");
    int t = s.nextInt();
    int b;
    while(true){
      System.out.println("enter number of bombs");
      b = s.nextInt();
      if(b < l*w*h*t && b >=0)
        break;
      else
        System.out.println("enter a valid number of bombs\n");
    }
    Board x = new Board(l,w,h,t,b);
    x.print2DSample();
    s.close();
  }
}