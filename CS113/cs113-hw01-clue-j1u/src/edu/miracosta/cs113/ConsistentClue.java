package edu.miracosta.cs113;

/**
 * ConsistentClue.java : Your job is to ask your AssistantJack and get the correct
 * answer in <= 20 tries.
 *
 * @author James Lu
 * @version 1.0
 *
 */

import java.util.Scanner;
import model.Theory;
import model.AssistantJack;

public class ConsistentClue {

    /*
     * ALGORITHM:
     *
     * PROMPT "Which theory to test? (1, 2, 3[random]): "
     * READ answerSet
     * INSTANTIATE jack = new AssistantJack(answerSet)
     *
     * WHILE solution != 0
     * IF solution == 1 THEN
     *      loop through the rest of possible weapons until check answer is no longer 1, then update solution
     * END IF
     *
     * IF solution == 2 THEN
     *      loop through the rest of possible locations until check answer is no longer 2, then update solution
     * END IF
     *
     * IF solution == 3 THEN
     *      loop through the rest of possible persons until check answer is no longer 3, the update solution
     * END IF
     *
     * OUTPUT "Total checks = " + jack.getTimesAsked()
     * IF jack.getTimesAsked() is greater than 20 THEN
     *      OUTPUT "FAILED"
     * ELSE
     *      OUTPUT "PASSED"
     * END IF
     *
     */

    /**
     * Driver method for random guessing approach
     *
     * @param args not used for driver
     */
    public static void main(String[] args) {
        int answerSet, solution;
        int weapon = 1;
        int location = 1;
        int murder = 1;

        Theory answer;
        AssistantJack jack;
        Scanner kb = new Scanner(System.in);

        System.out.println("Which theory would you like to test? (1, 2, 3[Random]): ");
        answerSet = kb.nextInt();
        kb.close();

        jack = new AssistantJack(answerSet);

        solution = jack.checkAnswer(weapon, location, murder);


        while(solution != 0){
            if(solution == 1){
                for(int i = 2; i < 7; i++){
                    weapon = i;
                    if((jack.checkAnswer(weapon, location, murder) != 1)){
                        solution = jack.checkAnswer(weapon, location, murder);
                        break;
                    }
                }
            }

            if(solution == 2){
                for(int i = 2; i < 11; i++){
                    location = i;
                    if((jack.checkAnswer(weapon, location, murder) != 2)){
                        solution = jack.checkAnswer(weapon, location, murder);
                        break;
                    }
                }
            }

            if(solution == 3){
                for(int i = 2; i < 7; i++){
                    murder = i;
                    if((jack.checkAnswer(weapon, location, murder) != 3)){
                        solution = jack.checkAnswer(weapon, location, murder);
                        break;
                    }
                }
            }
        }

        answer = new Theory(weapon, location, murder);

        System.out.println("Total Checks = " + jack.getTimesAsked() + ", Solution " + answer);

        if (jack.getTimesAsked() > 20) {
            System.out.println("FAILED!! You're a horrible Detective...");
        } else {
            System.out.println("WOW! You might as well be called Batman!");
        }
    }
}
