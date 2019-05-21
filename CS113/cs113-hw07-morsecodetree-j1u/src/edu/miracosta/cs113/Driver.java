package edu.miracosta.cs113;

import java.io.File;
import java.util.Scanner;

/**
 * Driver: driver class for morse code tree class which prompts user to either display morse code translations to console,
 * translate a text file from morse code and print to console, or translate morse code from console and print to console
 *
 * @author James Lu
 * @version 1.0
 */
public class Driver {
    static String defaultFile = System.getProperty("user.dir") + "/src/edu/miracosta/cs113/textfile.txt";
    static MorseCodeTree tree;
    static Scanner kb;

    public static void main(String[] args) {

        System.out.println("Initiating Morse Code Translator...");

        kb = new Scanner(System.in);

        initialize(new File(defaultFile));

        displayMenu();


    }

    /**
     * Initalizes file, print path to file and build morse code tree from file
     * @param file
     */
    public static void initialize(File file) {
        System.out.println("Initialized file: " + file.getAbsolutePath());
        tree = new MorseCodeTree(file);
    }

    /**
     * Displays menu for user, allows them to choose one of four options
     */
    public static void displayMenu() {

        System.out.println("1. Display morse code translations.");
        System.out.println("2. Translate text file.");
        System.out.println("3. Translate morse code from console");
        System.out.println("4. Exit.");


        String line = kb.nextLine();
        switch (line) {
            case "1":
                displayOutput();
                break;
            case "2":
                translateFile();
                break;
            case "3":
                translateText();
                break;
            case "4":
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid. Try again.");
                displayMenu();
                break;
        }


    }

    /**
     * Displays the letter translations using displaytranslations method from morsecodetree class
     */
    public static void displayOutput() {
        System.out.println("-----------LETTER TRANSLATIONS-----------");
        tree.displayTranslation();
        System.out.println("-----------------------------------------");
        displayMenu();
    }

    /**
     * Reads in a new file and translates the morse code accordingly, then print to console
     */
    public static void translateFile() {
        System.out.println("----------New File----------");

        boolean isValid = true;
        File file;
        Scanner sc = null;
        do {
            if (!isValid) {
                System.out.println("There was an error reading the file. Try again.");
            }
            System.out.println("Enter absolute/full file path: ");
            String line = kb.nextLine();
            file = new File(line);

            isValid = file.exists();

        } while (!isValid);

        try{
            sc = new Scanner(file);
        }catch (Exception e){
            System.out.println("Could not locate file... System exiting...");
            System.exit(0);
        }

        while (sc.hasNextLine()){
            String line = sc.nextLine();
            System.out.println(tree.translateFromMorseCode(line));
        }

        System.out.println("----------------------------");
        displayMenu();

    }

    public static void translateText() {
        System.out.println("----------TRANSLATE TEXT----------");
        boolean isValid = true;
        while (isValid) {
            try {
                System.out.println("Enter morse code to translate into English: ");
                String line = kb.nextLine();
                System.out.println(tree.translateFromMorseCode(line));
                isValid = false;
            } catch (Exception e) {
                System.out.println("There was an error translating morse code. \n\t " + e.getMessage());
            }
        }
        System.out.println("----------------------------------");
        displayMenu();
    }
}