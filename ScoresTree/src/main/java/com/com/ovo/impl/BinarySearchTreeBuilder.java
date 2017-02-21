package com.com.ovo.impl;

import com.com.ovo.Node;
import com.com.ovo.TreeBuilder;

/**
 * Builds binary search tree by adding nodes one by one.
 */
public class BinarySearchTreeBuilder implements TreeBuilder {

    private Node root;

    @Override
    public TreeBuilder add(int value) {
        root = insert(root, value);
        return this;
    }

    @Override
    public Node build() {
        return root;
    }


    private Node insert(Node node, int value) {

        if (node == null) {
            return new Node(value);
        }
        else if (value == node.getValue()) {
            node.increaseCount();
        }
        else if (value < node.getValue()) {
            node.setLeft(insert(node.getLeft(), value));
        }
        else {
            node.setRight(insert(node.getRight(), value));
        }
        return node;
    }
}
