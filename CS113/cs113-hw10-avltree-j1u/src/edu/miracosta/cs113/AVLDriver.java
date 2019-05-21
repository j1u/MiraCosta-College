package edu.miracosta.cs113;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

public class AVLDriver {
    public static void main(String[] args) {
        Integer[] arr = generateNumbers();

        BinarySearchTree<Integer> unbalanced = new BinarySearchTree<>();

        AVLTree<Integer> balanced = new AVLTree<>();


        for (Integer n : arr){
            unbalanced.add(n);
            balanced.add(n);
        }

        System.out.println("Unbalanced: ");
        System.out.println("String visualization: ");
        System.out.println(unbalanced.toString());
        System.out.println("Polymorphic visualization: ");
        System.out.println(unbalanced.toString2());
        System.out.println("------------------------------------------------------------------");
        System.out.println("Balanced: ");
        System.out.println("String visualization: ");
        System.out.println(balanced.toString());
        System.out.println("Polymorphic visualization: ");
        System.out.println(balanced.toString2());

    }

    /**
     * Generates a set of unique random numbers from 0 - 100
     * @return An array of Integer objects
     */
    public static Integer[] generateNumbers(){
        Random randGenerator = new Random();
        Set set = new HashSet<Integer>();

        while (set.size() < 25){
            set.add(randGenerator.nextInt(100));
        }

        return (Integer[]) set.toArray(new Integer[25]);
    }
}
