/**
 * Polynomial.java
 *
 * @author james Lu
 * @version 1.0
 */
package edu.miracosta.cs113;
import java.util.*;

public class Polynomial {

    private LinkedList<Term> polyTerms;

    /**
     * no arg contructor for Polynomial class
     */
    public Polynomial(){
        this.polyTerms = new LinkedList<Term>();
    }

    /**
     * Copy constructor for polynomial class
     * @param p returns the copy of this polynomial
     */
    public Polynomial(Polynomial p){
        this.polyTerms = new LinkedList<Term>();
        for (int i = 0; i < p.getPolyTerms().size(); i++) {
            this.polyTerms.add(new Term(p.getTerm(i)));
        }
    }

    /**
     * gets linked list poly terms
     * @return linkedlist containing the stored terms of current Polynomial
     */
    public LinkedList<Term> getPolyTerms() {
        return polyTerms;
    }

    /**
     * sets linkedList of polyterms to the passed param
     * @param polyTerms linkedlist to be copied over current list
     */
    public void setPolyTerms(LinkedList<Term> polyTerms) {
        this.polyTerms = polyTerms;
    }

    /**
     * adds terms to list while sorting and combining like terms
     * @param term is the Term object to be added
     */
    public void addTerm(Term term) {
        for (Term t : polyTerms) {
            if (t.getExponent() == term.getExponent()) {
                Term addedTerm = combineTerms(t, term);
                if(addedTerm == null){
                    polyTerms.remove(polyTerms.indexOf(t));
                }else{
                    polyTerms.set(polyTerms.indexOf(t), addedTerm);
                }
                sortList();
                return;
            }
        }

        polyTerms.add(term);
        sortList();
    }

    /**
     * clears the polyterms list
     */
    public void clear(){
        this.polyTerms.clear();
    }

    /**
     * gets the number of terms in the list
     * @return number of terms in list in int form
     */
    public int getNumTerms(){
        return this.polyTerms.size();
    }

    /**
     * gets the term at a specified indec
     * @param index desired index
     * @return the Term object at the index
     */
    public Term getTerm(int index) {
        return this.polyTerms.get(index);
    }

    /**
     * sets the term with full parameters
     * @param index the desired index
     * @param term the desired term Object
     * @return true always
     */
    public boolean setTerm(int index, Term term) {
        this.polyTerms.set(index, term);
        return true;
    }

    /**
     * combines like terms
     * @param term1 first term to be combined
     * @param term2 second term to be combined
     * @return
     */
    private Term combineTerms(Term term1, Term term2) {
        if (term1.getExponent() != term2.getExponent()) {
            return null;
        }

        int newCoefficient = term1.getCoefficient() + term2.getCoefficient();
        if(newCoefficient == 0){
            return null;
        }

        return new Term(newCoefficient, term1.getExponent());
    }

    /**
     * adds two polynomials together
     * @param otherPoly is the other polynomial to be added
     */
    public void add(Polynomial otherPoly) {
        for (int i = 0; i < otherPoly.getNumTerms(); i++) {
            this.addTerm(otherPoly.getPolyTerms().get(i)); //need to implement combineTerms
        }
    }

    /*
     * uses comparator to sort through out linked list poly terms.
     */
    private void sortList(){
        Collections.sort(this.polyTerms, Comparator.comparingInt(Term::getExponent).reversed());
    }

    /**
     * translates polynomial object to string containing its data
     * @return: polynomial "x^13 + 11x^12 - 12x^11 + 12x^10 + 2x^8 + 9x^6 + 100x^5 - x^4 + 7x^2 + 4x + 2 - 2x^-1"
     */
    @Override
    public String toString() {
        String term = "";
        String polynomial = "";

        for (Term aTerm : this.polyTerms) {

            term = aTerm.toString();

            if (polynomial.length() == 0) {
                if (term.substring(0, 1).contains("-") || term.substring(0, 1).contains("+")) {
                    term = term.substring(1, term.length());
                }
                polynomial += term;
            } else {
                polynomial += term;
            }
        }

        if(polynomial.length() == 0){
            polynomial = "0";
        }

        return polynomial;
    }

    /**
     * tests for equality between polynomial objects
     * @param o is the object to be compared
     * @return true of equal, false if not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polynomial that = (Polynomial) o;
        return Objects.equals(polyTerms, that.polyTerms);
    }
}
