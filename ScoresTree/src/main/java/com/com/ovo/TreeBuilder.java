package com.com.ovo;

/**
 * Tree builder.
 */
public interface TreeBuilder {

    /**
     * Adds a node to tree to correct place based on value.
     * @param value of node
     * @return TreeBuilder
     */
    TreeBuilder add(int value);

    /**
     * @return root of created tree
     */
    Node build();

}
