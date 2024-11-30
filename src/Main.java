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
    System.out.print("\n[1] Encrypt a file\n[2] Decrypt a file\n[3] Exit\n[-] : ");

    int uin = kb.nextInt();

    if (uin == 1) {
      encryptMenu();
    } else if (uin == 2) {
      decryptMenu();
    } else if (uin == 3) {
      System.out.println("[✔] Exiting...");
      System.exit(0);
    } else {
      System.out.println("[!] Invalid input. Try again.");
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
      System.out.print("[-] Enter a filename: ");
      filename = kb.nextLine();

      if ((filename.isBlank()) || (filename.contains(" ")) || (!filename.contains(".txt"))) {
        System.out.println("[!] Invalid input! Please enter a valid file name.");
        filename = "";
        encryptMenu();
      }
    } catch (InputMismatchException e) {
      System.out.println("[!] Invalid input! Please enter a valid file name.");
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
    String fileContents = readFile(filename);

    // Encrypting the file and placing it into the output
    String enContents = aes.encrypt(fileContents, key);
    File outFile = new File("ciphertext.txt");

    if (!outFile.exists()) {
      try {
        outFile.createNewFile();
      } catch (IOException e) {
        System.out.println("[!] Couldnt create output file! Create 'ciphertext.txt' in the correct directory.");
      }
    }

    // Write to the given filename
    if (writeFile("ciphertext.txt", enContents)) {
      System.out.println("[✓] File has been encrypted successfully\n[├] Key: " + key + "\n[└] Output is located in 'ciphertext.txt'");
    } else {
      System.out.println("[!] Couldnt write to file! Make sure theres a 'ciphertext.txt' file in the directory.");
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
      System.out.print("[-] Enter a file name: ");
      filename = kb.nextLine();

      if ((filename.isBlank()) || (filename.contains(" ")) || (!filename.contains(".txt"))) {
        System.out.println("[!] Invalid input! Please enter a valid file name.");
        filename = "";
        encryptMenu();
      }
    } catch (InputMismatchException e) {
      System.out.println("[!] Invalid input! Please enter a valid file name.");
      decryptMenu();
    }

    // Get key and validate
    try {
      System.out.print("[-] Enter a key: ");
      key = kb.nextLine();
    } catch (InputMismatchException e) {
      System.out.println("[!] Invalid input! Please enter a valid key.");
      decryptMenu();
    }

    // Reading the given file
    String fileContents = readFile(filename);

    // Decrypting the file and placing into
    String deContents = aes.decrypt(fileContents, key);
    File outFile = new File("plaintext.txt");

    if (!outFile.exists()) {
      try {
        outFile.createNewFile();
      } catch (IOException e) {
        System.out.println("[!] Couldnt create new file!");
        startMenu();
      }
    }

    // Write to the file
    if (writeFile("plaintext.txt", deContents)) {
      System.out.println("[✓] File has been encrypted successfully\n[└] Output is located in 'plaintext.txt'");
    } else {
      System.out.println("[!] Couldn't write to file! Make sure theres a 'plaintext.txt' file in the directory.'");
    }

    startMenu();
  }

  // Function to read the contents of the given file
  public static String readFile(String filename) {
    String fileContent = "";

    try (Scanner file = new Scanner(new File(filename))) {
      while (file.hasNextLine()) {
        fileContent = file.nextLine();
      }
    } catch (FileNotFoundException e) {
      fileContent = "[!] Given file not found. Please check your filename and try again.";
    }

    return fileContent;
  }

  // Function to write to a given file
  public static boolean writeFile(String filename, String content) {
    try (PrintWriter w = new PrintWriter(filename)) {
      w.println(content);
      return true;
    } catch (FileNotFoundException e) {
      return false;
    }
  }
}
