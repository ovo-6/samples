package com.ovo;

import com.com.ovo.Node;
import com.com.ovo.TreeBuilder;
import com.com.ovo.impl.BinarySearchTreeBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TreeBuilderTest {

    TreeBuilder builder = new BinarySearchTreeBuilder();


    @Test
    public void testOnlyRoot() {
        Node root = builder.add(5).build();
        assertNode(root, 5, 1);
    }

    @Test
    public void testTwoSameValues() {
        Node root = builder.add(5).add(5).build();
        assertNode(root, 5, 2);
    }

    @Test
    public void test213() {
        Node root = builder.add(2).add(1).add(3).build();
        assertNode(root, 2, 1);
        assertNode(root.getLeft(), 1, 1);
        assertNode(root.getRight(), 3, 1);
    }

    @Test
    public void test4255614() {
        Node root = builder.add(4).add(2).add(5).add(5).add(6).add(1).add(4).build();
        assertNode(root, 4, 2);
        assertNode(root.getLeft(), 2, 1);
        assertNode(root.getRight(), 5, 2);

        assertNode(root.getLeft().getLeft(), 1, 1);
        assertNull(root.getLeft().getRight());

        assertNull(root.getRight().getLeft());
        assertNode(root.getRight().getRight(), 6, 1);
    }

    private static void assertNode(Node node, int value, int count) {
        assertNotNull(node);
        assertEquals(value, node.getValue());
        assertEquals(count, node.getCount());
    }



}
