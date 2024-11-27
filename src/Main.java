import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    startMenu();
  }

  public static void startMenu() {
    Scanner kb = new Scanner(System.in);

    System.out.print("\n1. Encrypt a file\n2. Decrypt a file\n3. Exit\n: ");
    int uin = kb.nextInt();
    if (uin == 1) {
      System.out.println("Enter a file name: ");
    } else if (uin == 2) {
      System.out.println("Enter a file name: ");
    } else if (uin == 3) {
      System.out.println("Exiting...");
      System.exit(0);
    } else {
      System.out.println("Invalid input. Try again.");
      startMenu();
    }
  }
}
