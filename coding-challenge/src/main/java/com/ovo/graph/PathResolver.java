package com.ovo.graph;

import java.util.ArrayList;

public interface PathResolver {

    /**
     * @node start
     * @return
     * should return a ArrayList of ArrayLists, representing all
     * possible paths through the graph starting at node. The ArrayList
     * returned can be thought of as a ArrayList of paths, where each path
     * is represented as an ArrayList of GNodes.
     *
     */
    public ArrayList paths(GNode node);

}
