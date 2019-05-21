package edu.miracosta.cs113;

public class AVLTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E> {
    private boolean increase;

    @Override
    public boolean add(E item) {
        increase = false;
        root = add((AVLNode<E>) root, item);
        return addReturn;
    }

    /**
     * Recursive add method to add to AVLTree
     * @param localRoot localRoot to be manipulated in the method
     * @param item Item to be added
     * @return returns AVLNode for recusion
     */
    private AVLNode<E> add(AVLNode<E> localRoot, E item){
        if (localRoot == null) {
            addReturn = true;
            increase = true;
            return new AVLNode<E>(item);
        }

        if (item.compareTo(localRoot.data) == 0) { // Item is already in the tree.
            increase = false;
            addReturn = false;
            return localRoot;
        }else if (item.compareTo(localRoot.data) < 0) {
            localRoot.left = add((AVLNode<E>) localRoot.left, item); // item < data
            if (increase) {
                decrementBalance(localRoot);
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    increase = false;
                    return rebalanceLeft(localRoot);
                }
            }
            return localRoot;
        }else{
            localRoot.right = add((AVLNode<E>) localRoot.right, item); // item > data
            if (increase) {
                incrementBalance(localRoot);
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    increase = false;
                    return rebalanceRight(localRoot);
                }
            }
            return localRoot;
        }
    }

    /**
     * Method to rebalance the nodes of the tree after left rotations
     * @param localRoot the tree or subtree that needs to rebalanced
     * @return returns the new root after rotation
     */
    private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot) {

        AVLNode<E> leftChild = (AVLNode<E>) localRoot.left; // Obtain reference to left child.

        if (leftChild.balance > AVLNode.BALANCED) { // See whether left‐right heavy.
            // Obtain reference to left‐right child.
            AVLNode<E> leftRightChild = (AVLNode<E>) leftChild.right;
            /** Adjust the balances to be their new values after
             the rotations are performed.
             */
            if (leftRightChild.balance < AVLNode.BALANCED) { //left-right-left
                leftChild.balance = AVLNode.BALANCED;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.RIGHT_HEAVY;
            } else { // left-right-right
                leftChild.balance = AVLNode.LEFT_HEAVY;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }

            // Perform left rotation.
            localRoot.left = rotateLeft(leftChild);
        }else { //Left‐Left case
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        // Now rotate the local root right.
        return (AVLNode<E>) rotateRight(localRoot);
    }


    /**
     * Method to rebalance the nodes of the tree after right rotations
     * @param localRoot the tree or subtree that needs to rebalanced
     * @return returns the new root after rotation
     */
    private AVLNode<E> rebalanceRight(AVLNode<E> localRoot) {

        AVLNode<E> rightChild = (AVLNode<E>) localRoot.right; // Obtain reference to right child.

        if (rightChild.balance < AVLNode.BALANCED) { // right-left
            // Obtain reference to left‐right child.
            AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;
            /** Adjust the balances to be their new values after
             the rotations are performed.
             */
            if (rightLeftChild.balance < AVLNode.BALANCED) { // right-left-left
                rightChild.balance = AVLNode.BALANCED;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.LEFT_HEAVY;
            } else { //right-left-right
                rightChild.balance = AVLNode.RIGHT_HEAVY;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }

            // Perform right rotation.
            localRoot.right = rotateRight(rightChild);
        }else { //right-right case
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        // Now rotate the local root left.
        return (AVLNode<E>) rotateLeft(localRoot);
    }

    /**
     * Decrements the balance of the node during recursion
     * @param node node to be decremented
     */
    private void decrementBalance(AVLNode<E> node) {
        node.balance--;
        if (node.balance == AVLNode.BALANCED) {
            increase = false; //If now balanced, overall height has not increased.
        }
    }

    /**
     * Increments the balance of the node during recursion
     * @param node node to be incremented
     */
    private void incrementBalance(AVLNode<E> node) {
        // Decrement the balance.
        node.balance++;
        if (node.balance == AVLNode.BALANCED) {
            increase = false; //If now balanced, overall height has not increased.
        }
    }

    @Override
    public E delete(E item){ //stubbed
        return null;
    }

    /**
     * Class that extends Node, holds constants for leftheavy / rightheavy / balanced values
     * and adds an additional balance variable to track the balance of each subtree
     * @param <E>
     */
    private class AVLNode<E> extends Node<E> {
        public static final int LEFT_HEAVY = -1;
        public static final int RIGHT_HEAVY = 1;
        public static final int BALANCED = 0;
        private int balance;

        public AVLNode(E item){
            super(item);
            balance = BALANCED;
        }

        public String toString(){
            return balance + ": " + super.toString();
        }

    }
}
