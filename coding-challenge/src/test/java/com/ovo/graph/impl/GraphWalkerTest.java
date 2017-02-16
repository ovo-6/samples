package com.ovo.graph.impl;

import com.ovo.graph.GNode;
import com.ovo.graph.GraphWalker;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Tests for GraphWalkerImpl.
 */
public class GraphWalkerTest {

    GraphWalker walker = new GraphWalkerImpl();

    @Test
    public void noChildren() {
        GNode node = newNode("noChildren");
        ArrayList<GNode> result = walker.walkGraph(node);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertThat(getNodeNames(result), hasItem("noChildren"));
    }

    @Test
    public void oneChild() {
        GNode parent = newNode("parent", newNode("child"));
        ArrayList<GNode> result = walker.walkGraph(parent);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(getNodeNames(result), hasItems("parent", "child"));
    }

    /**
     *       A
     *     / | \
     *     B C D
     *     \ | /
     *       E
     */
    @Test
    public void diamond() {
        GNode e = newNode("E");
        GNode b = newNode("B", e);
        GNode c = newNode("C", e);
        GNode d = newNode("D", e);
        GNode a = newNode("A", b, c, d);

        ArrayList<GNode> result = walker.walkGraph(a);

        assertNotNull(result);
        assertEquals(5, result.size());
        assertThat(getNodeNames(result), hasItems("A", "B", "C", "D"));
    }

    private static GNode newNode(String name, GNode... children) {
        return new GNodeImpl(name, children);
    }

    private static List<String> getNodeNames(ArrayList<GNode> result) {
        return result.stream().map(n->n.getName()).collect(Collectors.toList());
    }


}
