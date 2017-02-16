package com.ovo.graph.impl;

import com.ovo.graph.GNode;
import com.ovo.graph.PathResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of PathResolver.
 * Depth-first visitor that remembers path to current node in stack.
 * If we encounter leaf we copy path to node to resulting array.
 */
public class PathResolverImpl implements PathResolver {
    /**
     * @param node - starting node
     * @return should return a ArrayList of ArrayLists, representing all
     * possible paths through the graph starting at node. The ArrayList
     * returned can be thought of as a ArrayList of paths, where each path
     * is represented as an ArrayList of GNodes.
     */
    @Override
    public ArrayList<ArrayList<GNode>> paths(GNode node) {

        ArrayList<ArrayList<GNode>> paths = new ArrayList<>();

        visitNodes(node, new ArrayList<>(), paths);

        return paths;
    }

    private static void visitNodes(GNode node, List<GNode> stack, ArrayList<ArrayList<GNode>> paths) {

        stack.add(node);

        if (node.getChildren().length == 0) {
            // we are in the leaf node

            // copy nodes on stack to resulting array
            paths.add(new ArrayList<>(stack));
        }
        else {
            for (GNode child : node.getChildren()) {
                visitNodes(child, stack, paths);
            }
        }
    }
}
