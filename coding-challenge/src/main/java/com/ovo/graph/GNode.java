package com.ovo.graph;


/**
 * Observe that this GNode can be thought of as defining a graph.
 * In implementing the functions below, you can assume that any
 * graph defined by a GNode is acyclic (no cycles).
 */
public interface GNode {

    public String getName();

    /**
     * Assume that when a GNode has no children, getChildren() returns a array of size 0, and *not* null.
     * You can also assume that all children returned by getChildren() are not null.
     */
    public GNode[] getChildren();

}
