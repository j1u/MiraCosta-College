/**
 * PolynomialDriver.java
 *
 * ~note that the add method does not discard the second polynomial, so it must be either edited or cleared to add a different polynomial~
 *
 * @author james Lu
 * @version 1.0
 */
package edu.miracosta.cs113;

import java.util.Scanner;

public class PolynomialDriver {

    static Polynomial p1, p2;

        public static void main(String[] args) {
            Scanner kb = new Scanner(System.in);
            displayMainMenu(kb);
        }

        public static void displayMainMenu(Scanner kb) {
            System.out.println(" -------------------------");
            System.out.println("|        Main Menu        |");
            System.out.println(" -------------------------");
            System.out.println("|1 - Create Polynomial    |");
            System.out.println("|-------------------------|");
            System.out.println("|2 - Edit Polynomial      |");
            System.out.println("|-------------------------|");
            System.out.println("|3 - Add Polynomials      |");
            System.out.println("|-------------------------|");
            System.out.println("|4 - Clear                |");
            System.out.println("|-------------------------|");
            System.out.println("|5 - Exit                 |");
            System.out.println(" -------------------------");

            String input = kb.nextLine();
            input = input.trim();
            switch (input) {
                case "1":
                    if (p1 == null) {
                        p1 = new Polynomial();
                        addTerm(kb, p1);
                        displayMainMenu(kb);
                        break;
                    }else if(p2 == null){
                        p2 = new Polynomial();
                        addTerm(kb, p2);
                        displayMainMenu(kb);
                        break;
                    }else{
                        if(p1 != null && p2 != null){
                            System.out.println("You have already created the max number of Polynomials...Please edit or clear one instead");
                            break;
                        }
                    }
                case "2":
                    if(p1 == null && p2 == null){
                        System.out.println("No polynomials have been created...");
                        displayMainMenu(kb);
                        break;
                    }
                    System.out.println("Please select a polynomial to edit...");
                    String editChoice = kb.nextLine();
                    switch (editChoice) {
                        case "1":
                            displayPolynomialMenu(kb, p1);
                            break;
                        case "2":
                            displayPolynomialMenu(kb, p2);
                            break;
                        default:
                            System.out.println("Please select a valid polynomial...(1 or 2)");
                            break;
                    }

                case "3":
                    if(p1 != null && p2 != null){
                        p1.add(p2);
                        System.out.println("New polynomial: " + p1.toString());
                        displayMainMenu(kb);
                        break;
                    }else if(p1 == null || p2 == null){
                        System.out.println("You only have one polynomial!");
                        displayMainMenu(kb);
                        break;
                    }

                case "4":
                    if(p1 == null && p2 == null){
                        System.out.println("You haven't made any polynomials yet!");
                        displayMainMenu(kb);
                        break;
                    }
                    System.out.println("Please select a polynomial to clear...");
                    String clearChoice = kb.nextLine();
                    switch (clearChoice) {
                        case "1":
                            if(p1 != null) {
                                p1.clear();
                                System.out.println("Cleared Polynomial 1");
                                break;
                            }else{
                                System.out.println("No polynomial found!");
                                displayMainMenu(kb);
                                break;
                            }
                        case "2":

                            if(p2 != null) {
                                p2.clear();
                                System.out.println("Cleared Polynomial 2");
                                break;
                            }else{
                                System.out.println("Please create a second polynomial...");
                                break;
                            }
                        default:
                            System.out.println("Please select a valid polynomial...(1 or 2)");
                            break;
                    }

                case "5":
                    System.exit(0);
                    break;
            }
        }

    public static void displayPolynomialMenu(Scanner kb, Polynomial p) {
        System.out.println(" -------------------------");
        System.out.println("|       Polynomial        |");
        System.out.println(" -------------------------");
        System.out.println("|1 - Add Terms            |");
        System.out.println("|-------------------------|");
        System.out.println("|2 - Clear                |");
        System.out.println("|-------------------------|");
        System.out.println("|3 - Return to Main menu  |");
        System.out.println(" -------------------------");

        String input = kb.nextLine();
        input = input.trim();
        switch (input) {
            case "1":
                addTerm(kb, p1);
                displayMainMenu(kb);
                break;
            case "2":
                System.out.println("Please select a polynomial to clear...");
                String clearChoice = kb.nextLine();
                switch (clearChoice) {
                    case "1":
                        if (p1 != null) {
                            p1.clear();
                            System.out.println("Cleared Polynomial 1");
                            break;
                        } else {
                            System.out.println("Please create a polynomial...");
                            break;
                        }
                    case "2":

                        if (p2 != null) {
                            p2.clear();
                            System.out.println("Cleared Polynomial 2");
                            break;
                        } else {
                            System.out.println("Please create a second polynomial...");
                            break;
                        }
                }
                    default:
                        System.out.println("Please select a valid polynomial...(1 or 2)");
                        break;
            case "3":
                System.out.println("Returning to Main Menu...");
                displayMainMenu(kb);
                break;
        }
    }

        public static void addTerm(Scanner kb, Polynomial poly){

            String input = "";
            System.out.println("Enter a term using format \"+3x^7 or -3x^7\":");
            input = kb.nextLine();
            while (!input.substring(0, 1).contains("-") && !input.substring(0, 1).contains("+")){
                System.out.println("You're coefficients don't have signs! Please re-enter using the format specified above...");
                input = kb.nextLine();
            }
            Term t = new Term(input);
            poly.addTerm(t);

            System.out.println("New polynomial: " + poly);

            System.out.println("Would you like to add another term? (Y/N):");
            input = kb.nextLine();
            if (input.equalsIgnoreCase("Y")) {
                addTerm(kb, poly);
            }
        }
    }
