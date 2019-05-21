package edu.miracosta.cs113;

public class BinarySearchTreeWithRotate<E extends Comparable<E>> extends BinarySearchTree<E> {

    /**
     * Rotates a tree right
     * @param root original root of the tree or subtree
     * @return returns the new root
     */
    protected Node<E> rotateRight(Node<E> root){
        Node<E> temp = root.left;
        root.left = temp.right;
        temp.right = root;
        return temp;
    }

    /**
     * Rotates a tree left
     * @param root original root of the tree or subtree
     * @return returns the new root
     */
    protected Node<E> rotateLeft(Node<E> root){
        Node<E> temp = root.right;
        root.right = temp.left;
        temp.left = root;
        return temp;
    }
}
