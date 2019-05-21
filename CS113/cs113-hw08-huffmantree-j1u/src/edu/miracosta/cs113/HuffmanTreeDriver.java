package edu.miracosta.cs113;

import java.io.*;
import java.text.NumberFormat;

public class HuffmanTreeDriver {

    public static void main(String[] args) {

            try {
                TextFileGenerator.makeCleanFile("https://en.wikipedia.org/wiki/Denver_Nuggets", System.getProperty("user.dir") + "/resources/file.txt");
                BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/resources/file.txt"));

                String code = "";
                int intChar;
                char character;
                int count = 0;


                while ((intChar = br.read()) != -1) {
                    character = (char) intChar;
                    code += "" + character;
                    count++;
                }
                double bits = 16 * count;

                br.close();

                System.out.println();

                System.out.println("---Creating HuffmanTree---");
                HuffmanTree tree = new HuffmanTree(code);

                System.out.println("---Encoding Tree---");
                String encoded = tree.encode(code);

                System.out.println("---Decoding Tree---");
                String decoded = tree.decode(encoded);

                double decodedBits = 16 * decoded.toCharArray().length;
                double encodedBits = encoded.toCharArray().length;

                System.out.println("---Writing encoded file---");
                BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/resources/encoded.txt"));
                bw.write(encoded.toCharArray());
                bw.close();

                System.out.println("---Writing decoded file---");
                bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/resources/decoded.txt"));
                bw.write(decoded.toCharArray());
                bw.close();

                System.out.println("Original file bits: " + bits);
                System.out.println("Encoded file bits: " + encodedBits);
                System.out.println("Decoded file bits: " + decodedBits);

                NumberFormat n = NumberFormat.getPercentInstance();

                System.out.println("Compression: " + n.format( bits / encodedBits));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

