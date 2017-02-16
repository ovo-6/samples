package com.ovo.graph.impl;

import com.ovo.graph.GNode;

/**
 * Immutable implementation for GNode that only stores name and array of children.
 */
public class GNodeImpl implements GNode {

    private final String name;
    private final GNode[] children;

    public GNodeImpl(String name, GNode[] children) {

        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }

        if (children == null) {
            throw new IllegalArgumentException("children array is null");
        }

        this.name = name;
        this.children = children;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Assume that when a GNode has no children, getChildren() returns a array of size 0, and *not* null.
     * You can also assume that all children returned by getChildren() are not null.
     */
    @Override
    public GNode[] getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GNodeImpl gNode = (GNodeImpl) o;

        return name.equals(gNode.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
