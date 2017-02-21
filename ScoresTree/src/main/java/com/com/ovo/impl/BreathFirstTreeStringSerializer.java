package com.com.ovo.impl;

import com.com.ovo.Node;
import com.com.ovo.NullNode;
import com.com.ovo.TreeSerializer;

import java.util.LinkedList;
import java.util.List;

/**
 * Prints tree represented by root node by breath-first algorithm.
 * If there is no child it prints empty string for node (except the case that all nodes would be empty).
 */
public class BreathFirstTreeStringSerializer implements TreeSerializer {

    /**
     * Serialize tree represented by root node to string.
     * @param root root node of the tree.
     * @return string with nodes separated by comma.
     */
    @Override
    public String serialize(Node root) {

        StringBuilder sb = new StringBuilder();

        boolean added = false;

        // store nodes on current level to be printed together
        List<Node> stack = new LinkedList<>(); //we only iterate so LinkedList is better than ArrayList
        if (root != null) {
            stack.add(root);
            added = true;
        }

        boolean first = true;

        // loop while we add any not NullNode
        // (if all nodes are NullNodes we are stop)
        while (added) {
            added = false;

            List<Node> newNodes = new LinkedList<>();
            for (Node node: stack) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(node);

                added |= addChild(node, node.getLeft(), newNodes);
                added |= addChild(node, node.getRight(), newNodes);

                first = false;
            }
            stack = newNodes;
        }

        return sb.toString();
    }

    private static boolean addChild(Node parent, Node child, List<Node> newNodes) {
        boolean added = false;
        if (child != null) {
            newNodes.add(child);
            added = true;
        }
        else if (!(parent instanceof NullNode)) { //we don't want to continue down through NullNodes
            newNodes.add(new NullNode()); //we add it just to print empty string in correct place
        }
        return added;
    }
}
