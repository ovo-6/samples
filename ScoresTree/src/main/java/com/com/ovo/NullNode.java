package com.com.ovo;

/**
 * Node representing no value, used for convenience during printing tree to have empty spots
 * e.g. "4:2, 2:1, 5:2, 1:1, , , 6:1".
 */
public class NullNode extends Node {

    private NullNode(int value) {
        super(value);
    }

    public NullNode() {
        super(0);
    }

    @Override
    public String toString() {
        return "";
    }
}
