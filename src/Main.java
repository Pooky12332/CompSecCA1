import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
  public static void main(String[] args) {
    startMenu();
  }

  // Main menu class
  public static void startMenu() {
    Scanner kb = new Scanner(System.in);
    System.out.print("\n1. Encrypt a file\n2. Decrypt a file\n3. Exit\n: ");

    int uin = kb.nextInt();

    if (uin == 1) {
      encryptMenu();
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

  // Encrypting the file
  public static void encryptMenu() {
    Scanner kb = new Scanner(System.in);
    String filename = "";

    // Validating the filename
    try {
      System.out.print("Enter a file name: ");
      filename = kb.nextLine();

      if ((filename.isBlank()) || (filename.contains(" ")) || (!filename.contains(".txt"))) {
        System.out.println("Invalid input! Please enter a valid file name.");
        filename = "";
        encryptMenu();
      }
    } catch (InputMismatchException e) {
      System.out.println("Invalid input! Please enter a valid file name.");
    }

    System.out.print(filename);
  }
}
