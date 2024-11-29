import java.io.*;
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
      encryptMenu();
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

    try (Scanner inFile = new Scanner(new File(filename))) {
      while (inFile.hasNextLine()) {
        fileContents += inFile.nextLine() + "\n";
      }
    } catch (FileNotFoundException e) {
      System.out.println("Given file not found.");
      startMenu();
    }

    // Encrypting the file and placing it into the output
    String enContents = aes.encrypt(fileContents, key);
    File outFile = new File("ciphertext.txt");

    if (!outFile.exists()) {
      try {
        outFile.createNewFile();
      } catch (IOException e) {
        System.out.println("Couldnt create new file!");
      }
    }

    try (PrintWriter w = new PrintWriter(new PrintWriter("ciphertext.txt"))) {
      w.println(enContents);
      System.out.println("File has been encrypted successfully\nKey: " + key + "\nOutput is located in 'ciphertext.txt'");
    } catch (FileNotFoundException e) {
      System.out.println("Given file not found.");
      startMenu();
    }

    startMenu();
  }

  // Decryption menu
  public static void decryptMenu() {
    Scanner kb = new Scanner(System.in);
    AES aes = new AES();
    String filename = "";
    String key = "";

    // Get input and validate
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
      decryptMenu();
    }

    // Get key and validate
    try {
      System.out.print("Enter a key: ");
      key = kb.nextLine();
    } catch (InputMismatchException e) {
      System.out.println("Invalid input! Please enter a valid key.");
      decryptMenu();
    }

    // Reading the given file
    String fileContents = "";

    try (Scanner inFile = new Scanner(new File(filename))) {
      while (inFile.hasNextLine()) {
        fileContents = inFile.nextLine();
      }
    } catch (FileNotFoundException e) {
      System.out.println("Given file not found.");
      startMenu();
    }

    // Decrypting the file and placing into
    String deContents = aes.decrypt(fileContents, key);
    File outFile = new File("plaintext.txt");

    if (!outFile.exists()) {
      try {
        outFile.createNewFile();
      } catch (IOException e) {
        System.out.println("Couldnt create new file!");
        startMenu();
      }
    }

    try (PrintWriter w = new PrintWriter(new PrintWriter("plaintext.txt"))) {
      w.println(deContents);
      System.out.println("File has been encrypted successfully\nOutput is located in 'plaintext.txt'");
    } catch (FileNotFoundException e) {
      System.out.println("Given file not found.");
      startMenu();
    }

    startMenu();
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
