import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.InputMismatchException;
import java.util.Scanner;

// - Clean up encryption method

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
      decryptMenu();
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
    AES aes = new AES();
    String filename = "";

    // Validating the filename
    try {
      System.out.print("Enter a filename: ");
      filename = kb.nextLine();

      if ((filename.isBlank()) || (filename.contains(" ")) || (!filename.contains(".txt"))) {
        System.out.println("Invalid input! Please enter a valid file name.");
        filename = "";
        encryptMenu();
      }
    } catch (InputMismatchException e) {
      System.out.println("Invalid input! Please enter a valid file name.");
    }

    // Creating a key
    String charList = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    SecureRandom sr = new SecureRandom();
    String key = "";

    for (int i = 0; i < 15; i++) {
      key += charList.charAt(sr.nextInt(charList.length()));
    }

    // Reading the given file
    String fileContents = "";

    try (Scanner f = new Scanner(new File(filename))) {
      while (f.hasNextLine()) {
        fileContents += f.nextLine() + "\n";
      }
    } catch (FileNotFoundException e) {
      System.out.println("Given file not found.");
    }

    // Encrypting the file and placing it into the output
    String enContents = aes.encrypt(fileContents, key);

    try (PrintWriter w = new PrintWriter(new PrintWriter("ciphertext.txt"))) {
      w.println(enContents);
      System.out.println("File has been encrypted successfully\nKey: " + key + "\nOutput is located in 'ciphertext.txt'");
    } catch (FileNotFoundException e) {
      System.out.println("Output file not found.");
    }
  }

  // Decryption the file
  public static void decryptMenu() {
    Scanner kb = new Scanner(System.in);
    AES aes = new AES();
    String filename = "";

    // Validating the file name
    try {
      System.out.print("Enter a filename: ");
      filename = kb.nextLine();

      // IF should go here to catch cases where user inputs wrong
    } catch (InputMismatchException e) {
      System.out.println("Invalid input! Please enter a valid file name.");
    }
  }
}
