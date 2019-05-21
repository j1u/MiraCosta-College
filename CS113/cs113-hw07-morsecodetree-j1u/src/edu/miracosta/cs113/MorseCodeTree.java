package edu.miracosta.cs113;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * MorseCodeTree : A BinaryTree, with Nodes of type Character to represent each letter of the English alphabet,
 * and a means of traversal to be used to decipher Morse code.
 *
 * @author James Lu
 * @version 1.0
 */
public class MorseCodeTree extends BinaryTree<Character> {

    File testFile = new File(System.getProperty("user.dir") + "/src/edu/miracosta/cs113/textfile.txt");

    public MorseCodeTree(){
        this.readMorseCodeTree(testFile);
    }

    public MorseCodeTree(File file){
        this.readMorseCodeTree(file);
    }

    /**
     * Non-recursive method for translating a String comprised of morse code values through traversals
     * in the MorseCodeTree.
     *
     * The given input is expected to contain morse code values, with '*' for dots and '-' for dashes, representing
     * only letters in the English alphabet.
     *
     * This method will also handle exceptional cases, namely if a given token's length exceeds that of the tree's
     * number of possible traversals, or if the given token contains a character that is neither '*' nor '-'.
     *
     * @param morseCode The given input representing letters in Morse code
     * @return a String representing the decoded values from morseCode
     */
    public String translateFromMorseCode(String morseCode) throws IllegalArgumentException{

        String[] codes = morseCode.split(" ");

        for (String s:codes) {
            if(s.length() > 4 || !s.matches("[-* ]+")){
                throw new IllegalArgumentException();
            }
        }

        StringBuilder sb = new StringBuilder();

        for (String s : codes) {
            Node<Character> localRoot = this.root;

            for (int i = 0; i < s.length(); i++) {

                if (s.charAt(i) == '*') {
                    localRoot = localRoot.left;
                    if(i == s.length() - 1) {
                        sb.append(localRoot.data.toString());
                    }
                }else if(s.charAt(i) == '-'){
                    localRoot = localRoot.right;
                    if(i == s.length() - 1) {
                        sb.append(localRoot.data.toString());
                    }
                }
            }
        }
        return sb.toString();
    }


    /**
     * Reads in a morse code tree from a text file and creates it in the class with corresponding nodes
     * @param file file to be used for building the tree
     */
    public void readMorseCodeTree(File file) {

        Scanner s = null;
        this.root = new Node<Character>(null);


        try {
            s = new Scanner(new FileInputStream(file));
        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }

        while (s.hasNextLine()) {
            String data = s.nextLine();

            Character letter = data.charAt(0);
            String code = data.substring(2);

            Node<Character> temp = this.root;

            for (int i = 0; i < code.length(); i++){
                if(code.charAt(i) == '*'){
                    if(temp.left == null){
                        temp.left = new Node<Character>(letter);
                    }
                    temp = temp.left;
                }else if (code.charAt(i) == '-'){
                    if(temp.right == null){
                        temp.right = new Node<Character>(letter);
                    }
                    temp = temp.right;
                }
            }
        }
        s.close();
    }

    /**
     * Displays the translations for morse code to english alphabet
     */
    public void displayTranslation(){
        Scanner sc = null;

        try{
             sc = new Scanner(testFile);
        }catch (Exception e){
            System.out.println("Could not locate file... System exiting...");
            System.exit(0);
        }

        ArrayList<String> arr = new ArrayList<String>();

        while (sc.hasNextLine()){
            String line = sc.nextLine();
            arr.add(line);
        }

        Collections.sort(arr);

        for (String s : arr){
            System.out.println(s);
        }
    }



} // End of class MorseCodeTree