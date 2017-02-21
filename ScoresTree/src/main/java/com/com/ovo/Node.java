package com.com.ovo;

/**
 * Node in tree with max. two children.
 */
public class Node {

    private Node left;
    private Node right;

    private final int value;
    private int count = 1;

    public Node(int value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    public void increaseCount() {
        count++;
    }

    @Override
    public String toString() {
        return value + ":" + count;
    }
}
