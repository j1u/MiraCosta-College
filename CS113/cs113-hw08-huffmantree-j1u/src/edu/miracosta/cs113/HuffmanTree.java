package edu.miracosta.cs113;

import java.util.*;

public class HuffmanTree extends BinaryTree<String> implements HuffmanInterface{

    final int ASCII_ALPHABET = 256;
    int[] frequencyTable = new int[ASCII_ALPHABET];
    HuffNode root;
    String[] codeStorage;

    public HuffmanTree(String message){
        this.frequencyTable = buildFreqTable(message);
        this.root = buildHuffmanTree(frequencyTable);
        this.codeStorage = codeStorage(this.root);
        encode(message);
    }


    private int[] buildFreqTable(String message){ //builds the frequency table from message
        for (char c:message.toCharArray()) {
            frequencyTable[c]++; //increment each letter in message by it's ascii value
        }
        return frequencyTable;
    }

    private HuffNode buildHuffmanTree(int[] freq){
        PriorityQueue<HuffNode> queue = new PriorityQueue<HuffNode>();

        for(char i = 0; i < frequencyTable.length; i++){
            if(freq[i] > 0){
                queue.add(new HuffNode(i, freq[i]));
            }
        }

        if(queue.size() == 1){
            queue.add(new HuffNode('\0', 1));
        }

        while(queue.size() > 1){
            HuffNode leftNode = queue.poll();
            HuffNode rightNode = queue.poll();

            HuffNode parent = new HuffNode('\0', leftNode.freq + rightNode.freq);

            parent.left = leftNode;
            parent.right = rightNode;

            queue.add(parent);
        }
        return queue.poll();
    }

    /**
     * Decodes a message using the generated Huffman tree, where each character in the given message ('1's and '0's)
     * corresponds to traversals through said tree.
     *
     * @param codedMessage The compressed message based on this Huffman tree's encoding
     * @return The given message in its decoded form
     */
    @Override
    public String decode(String codedMessage) { //decode character by character, 0 left, 1 is right until it hits a leaf node then store and restart for next char, need index to find where char ends

        String decoded = "";

        HuffNode node = (HuffNode) this.root;

        for (char c : codedMessage.toCharArray()) {
            if(c == '0') {
                if(node.left != null) {
                    HuffNode node2 = (HuffNode) node.left;

                    if(!node2.isLeaf()) {
                        node = node2;
                    } else {
                        decoded += (char) node2.data;
                        node = (HuffNode) this.root;
                    }
                }
            }else if  (c == '1') {
                if(node.right != null) {
                    HuffNode node2 = (HuffNode) node.right;

                    if(!node2.isLeaf()) {
                        node = node2;
                    }else {
                        decoded += (char) node2.data;
                        node = (HuffNode) this.root;
                    }
                }
            }else {
                throw new RuntimeException("There is no character that can move left or right!: '" + c + "'");
            }
        }
        return decoded;
    }

    /**
     * Outputs the message encoded from the generated Huffman tree.
     * pre: the Huffman tree has been built using characters by which the message is only comprised.
     *
     * @param message The message to be decoded
     * @return The given message in its specific Huffman encoding of '1's and '0's
     */
    @Override
    public String encode(String message) {
        StringBuilder sb = new StringBuilder();

        for (char c:message.toCharArray()){
            sb.append(codeStorage[c]);
        }
        return sb.toString();
    }

    private String[] codeStorage(HuffNode root){
        String[] codeStorage =  new String[ASCII_ALPHABET];

        buildCodeStorage(root, "", codeStorage);

        return codeStorage;
    }

    private void buildCodeStorage(HuffNode node, String charCode, String[] codeStorage){

        if (!node.isLeaf()){ //traverse until it reaches a leaf node
            buildCodeStorage((HuffNode) node.left, charCode + '0', codeStorage);
            buildCodeStorage((HuffNode) node.right, charCode + '1', codeStorage);
        }else {
            codeStorage[node.data] = charCode;//store code in it's char's ascii code index
        }
    }

    protected static class HuffNode extends Node<Character> implements Comparable<HuffNode>{
        protected int freq;

        public HuffNode(char data, int freq){
            super(data);
            this.freq = freq;
        }

        @Override
        public int compareTo(HuffNode n) {
            return Integer.compare(this.freq, n.freq);
        }

        protected boolean isLeaf(){
            return this.left == null && this.right == null;
        }

        @Override
        public String toString() {
            if(data == '\0'){
                return "";
            }

            return "HuffNode{" +
                    "data=" + data +
                    ", freq=" + freq +
                    '}';
        }
    }
}
