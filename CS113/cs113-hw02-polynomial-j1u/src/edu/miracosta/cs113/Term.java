/**
 * Term.java
 *
 * @author james Lu
 * @version 1.0
 */
package edu.miracosta.cs113;

public class Term implements Comparable<Term>, Cloneable{

    private int coefficient, exponent;

    /**
     * no arg constructor for Term class
     */
    public Term(){
        this.coefficient = 1;
        this.exponent = 1;
    }

    /**
     * full constructor for Term class
     * @param coefficient desired coefficient of term
     * @param exponent desired exponent of term
     */
    public Term(int coefficient, int exponent){
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    /**
     * Copy constructor for Term class
     * @param term is the term object to be copied
     */
    public Term(Term term){
        this.coefficient = term.getCoefficient();
        this.exponent = term.getExponent();
    }


    /**
     * String constructor for term class
     * @param term term written in string format to be converted to Term object
     */
    public Term(String term){
        if(term.contains("x")){ // captures strings to the left of x

            if(term.length() == 2){ // captures coefficient 1
                if(term.contains("+")){
                    this.coefficient = 1;
                    this.exponent = 1;
                    return;
                }

                if(term.contains("-")){
                    this.coefficient = -1;
                    this.exponent = 1;
                    return;
                }
            }

            if(term.contains("+x")){
                if(term.contains("^")){ //captures strings to the right of the carrot
                    int expLocation = term.indexOf('^');
                    String exp = term.substring(expLocation + 1, term.length());

                    if(exp.contains("-")){
                        this.coefficient = 1;
                        this.exponent = Integer.parseInt(exp);
                        return;
                    }else {
                        //String expNoOperator = exp.substring(1,exp.length());
                        this.coefficient = 1;
                        this.exponent = Integer.parseInt(exp);
                        return;
                    }
                }
            }

            if(term.contains("-x")){
                if(term.contains("^")){ //captures strings to the right of the carrot
                    int expLocation = term.indexOf('^');
                    String exp = term.substring(expLocation + 1, term.length());

                    if(exp.contains("-")){
                        this.coefficient = -1;
                        this.exponent = Integer.parseInt(exp);
                        return;
                    }else {
                        this.coefficient = -1;
                        this.exponent = Integer.parseInt(exp);
                        return;
                    }
                }
            }

            int coeffLocation = term.indexOf('x');

            String coeff = term.substring(0, coeffLocation);

            if(coeff.contains("+")){
                String coeffNoOperator = coeff.substring(1,coeff.length());
                this.coefficient = Integer.parseInt(coeffNoOperator);
                this.exponent = 1;
            }

            if(coeff.contains("-")){
                this.coefficient = Integer.parseInt(coeff);
                this.exponent = 1;
            }

            if(term.contains("^")){ // //captures strings to the right of the carrot
                int expLocation = term.indexOf('^');
                String exp = term.substring(expLocation + 1, term.length());

                if(exp.contains("-")){
                    this.exponent = Integer.parseInt(exp);
                }else {
                    this.exponent = Integer.parseInt(exp);
                }
            }
        }else{ //no x's
            if(term.contains("+")){
                this.coefficient = Integer.parseInt(term);
            }
            if(term.contains("-")){
                this.coefficient = Integer.parseInt(term);
            }
        }
    }

    /**
     * gets coefficient of term object
     * @return the coefficient of the term object in int form
     */
    public int getCoefficient() {
        return coefficient;
    }

    /**
     * sets the coefficient of the term object
     * @param coefficient
     */
    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    /**
     * sets the exponent of the term object
     */
    public int getExponent() {
        return exponent;
    }

    /**
     * sets the exponent of the term object
     * @param exponent
     */
    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

    /**
     * sets both exponent and coefficient of Term object
     * @param coefficient the desired coefficient
     * @param exponent the desired exponent
     */
    public void setAll(int coefficient, int exponent){
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    /**
     * tests equality between two objects
     * @param o object to be compared to
     * @return true if equal, false if not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        return coefficient == term.coefficient &&
                exponent == term.exponent;
    }

    /**
     * Converts term object to string
     * @return string in the format "9x^7"
     */
    @Override
    public String toString() {
        if(coefficient < 0){
            if(coefficient == -1 && exponent == 1){
                return "-x";
            }
            if(coefficient == -1){
                return "-x^" + exponent;
            }
            if(exponent == 1){
                return coefficient + "x";
            }
            if(exponent == 0){
                return coefficient + "";
            }
            return coefficient + "x^" + exponent;
        }

        if(coefficient > 0){
            if(coefficient == 1 && exponent == 1){
                return "+x";
            }
            if(coefficient == 1){
                return "+x^" + exponent;
            }
            if(exponent == 1){
                return "+" + coefficient + "x";
            }
            if(exponent == 0){
                return "+" + coefficient;
            }
            return "+" + coefficient + "x^" + exponent;
        }

        return "";
    }

    /**
     * compares the exponent between two Term objects
     * @param otherExponent the other Term object to be compared to
     * @return -1 if this is lower than otherExponent, 0 if equal, 1 is this is greater than otherExponent
     */
    @Override
    public int compareTo(Term otherExponent) {
        return Integer.compare(this.getExponent(), otherExponent.getExponent());
    }

    @Override
    public Object clone(){
        return new Term(this);
    }
}
