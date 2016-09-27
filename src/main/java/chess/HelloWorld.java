package chess;

public class HelloWorld {

  public static void speak() {
      System.out.println("Hello World!");
  }

  public static int five() {
      return 5;
  }

  public static char chess() {
    Board b = new Board();
    b.init();

    return b.getSquare(0, 1);
  }

  public static void main(String[] args) {
    speak();
  }

}
