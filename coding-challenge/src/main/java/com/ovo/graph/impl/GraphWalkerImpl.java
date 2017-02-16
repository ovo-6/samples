package com.ovo.graph.impl;

import com.ovo.graph.GNode;
import com.ovo.graph.GraphWalker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation for GraphWalker.
 * Depth-first visitor that visits all nodes and marks
 * already visited nodes to avoid going the same path again.
 */
public class GraphWalkerImpl implements GraphWalker {


    /**
     * @param node
     * @return should return a ArrayList containing every GNode in the
     * graph. Each node should appear in the ArrayList exactly once (i.e. no duplicates).
     */
    @Override
    public ArrayList walkGraph(GNode node) {

        Set<GNode> visitedNodes = new HashSet<>();

        visitNodes(node, visitedNodes);

        return new ArrayList(visitedNodes);
    }

    private static void visitNodes(GNode node, Set<GNode> visitedNodes) {

        // don't go through already visited nodes again
        if (!visitedNodes.contains(node)) {
            visitedNodes.add(node);
            for (GNode child : node.getChildren()) {
                visitNodes(child, visitedNodes);
            }
        }
    }
}
