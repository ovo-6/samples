package com.ovo.graph.impl;

import com.ovo.graph.GNode;
import com.ovo.graph.PathResolver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Tests for PathResolverImpl.
 */
public class PathResolverTest {

    PathResolver resolver = new PathResolverImpl();

    @Test
    public void noChildren() {
        GNode node = newNode("A");

        ArrayList<ArrayList<GNode>> result = resolver.paths(node);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertThat(getNodeNames(result.get(0)), hasItems("A"));
    }

    @Test
    public void oneChild() {
        GNode parent = newNode("A", newNode("B"));

        ArrayList<ArrayList<GNode>> result = resolver.paths(parent);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertThat(getNodeNames(result.get(0)), hasItems("A", "B"));
    }

    @Test
    public void givenExample() {

        GNode e = newNode("E");
        GNode f = newNode("F");
        GNode g = newNode("G");
        GNode h = newNode("H");
        GNode i = newNode("I");
        GNode j = newNode("J");

        GNode b = newNode("B", e, f);
        GNode c = newNode("C", g, h, i);
        GNode d = newNode("D", j);

        GNode a = newNode("A", b, c, d);

        ArrayList<ArrayList<GNode>> result = resolver.paths(a);
        assertNotNull(result);
        assertEquals(6, result.size());

        assertThat(getNodeNames(result.get(0)), hasItems("A", "B", "E"));
        assertThat(getNodeNames(result.get(1)), hasItems("A", "B", "F"));

        assertThat(getNodeNames(result.get(2)), hasItems("A", "C", "G"));
        assertThat(getNodeNames(result.get(3)), hasItems("A", "C", "H"));
        assertThat(getNodeNames(result.get(4)), hasItems("A", "C", "I"));

        assertThat(getNodeNames(result.get(5)), hasItems("A", "D", "J"));

    }

    /**
     *        A
     *       / \
     *       B C
     *       \ /
     *        D
     *        |
     *        E
     */
    @Test
    public void diamond() {

        GNode e = newNode("E");
        GNode d = newNode("D", e);

        GNode b = newNode("B", d);
        GNode c = newNode("C", d);

        GNode a = newNode("A", b, c);

        ArrayList<ArrayList<GNode>> result = resolver.paths(a);
        assertNotNull(result);
        assertEquals(2, result.size());

        assertThat(getNodeNames(result.get(0)), hasItems("A", "B", "D", "E"));
        assertThat(getNodeNames(result.get(1)), hasItems("A", "C", "D", "E"));
    }

    private static GNode newNode(String name, GNode... children) {
        return new GNodeImpl(name, children);
    }

    private static List<String> getNodeNames(ArrayList<GNode> result) {
        return result.stream().map(n->n.getName()).collect(Collectors.toList());
    }
}
