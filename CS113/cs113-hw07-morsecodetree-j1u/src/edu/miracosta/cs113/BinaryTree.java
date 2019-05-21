package edu.miracosta.cs113;

import java.io.Serializable;
import java.util.Scanner;

public class BinaryTree<E>{
    protected Node<E> root;

    public BinaryTree(){
        this.root = null;
    }

    protected BinaryTree(Node<E> root){
        this.root = root;
    }

    public BinaryTree(E data, BinaryTree<E> left, BinaryTree<E> right){
        this.root = new Node<E>(data);
        if(left != null) {
            this.root.left = left.root;
        }else{
            this.root.left = null;
        }
        if(right != null) {
            this.root.right = right.root;
        }else{
            this.root.right = null;
        }

    }

    public BinaryTree<E> getLeftSubtree(){
        if(this.root != null && this.root.left != null) {
            return new BinaryTree<E>(this.root.left);
        }else{
            return null;
        }
    }

    public BinaryTree<E> getRightSubtree(){
        if(this.root != null && this.root.right != null) {
            return new BinaryTree<E>(this.root.right);
        }else{
            return null;
        }
    }

    public E getData(){
        return this.root.data;
    }

    public boolean isLeaf(){
        return (this.root.left == null && this.root.right == null);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(this.root, 1, sb);
        return sb.toString();
    }

    private void preOrderTraverse(Node<E> node, int depth, StringBuilder sb){
        for (int i = 1; i < depth; i++){
            sb.append(" ");
        }
        if(node == null){
            sb.append("null\n");
        }else{
            sb.append(node.toString() + "\n");
            preOrderTraverse(node.left, depth + 1, sb);
            preOrderTraverse(node.right, depth + 1, sb);
        }

    }

    public static BinaryTree<String> readBinaryTree(Scanner s){
        String data = s.next();
        if(data.equals("null")){
            return null;
        } else {
            BinaryTree<String> leftTree = readBinaryTree(s);
            BinaryTree<String> rightTree = readBinaryTree(s);
            return new BinaryTree<String>(data, leftTree, rightTree);
        }
    }
    protected static class Node<E> implements Serializable {
        protected E data;
        protected Node<E> left;
        protected Node<E> right;

        public Node(){
            this.data = null;
            this.left = null;
            this.right = null;
        }

        public Node(E data){
            this.data = data;
            this.left = null;
            this.right = null;
        }

        public String toString(){
            if(this.data == null){
                return "null";
            }else {
                return data.toString();
            }
        }
    }
}
