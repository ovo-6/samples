package com.ovo;

import com.com.ovo.Node;
import com.com.ovo.TreeBuilder;
import com.com.ovo.TreeSerializer;
import com.com.ovo.impl.BinarySearchTreeBuilder;
import com.com.ovo.impl.BreathFirstTreeStringSerializer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TreeSerializerTest {

    TreeSerializer serializer = new BreathFirstTreeStringSerializer();
    TreeBuilder builder = new BinarySearchTreeBuilder();

    @Test
    public void testNoRoot() {
        Node root = builder.build();
        String result = serializer.serialize(root);
        assertEquals("", result);
    }

    @Test
    public void testOnlyRoot() {
        Node root = builder.add(5).build();
        String result = serializer.serialize(root);
        assertEquals("5:1", result);
    }

    @Test
    public void test213() {
        Node root = builder.add(2).add(1).add(3).build();
        String result = serializer.serialize(root);
        assertEquals("2:1, 1:1, 3:1", result);
    }

    @Test
    public void test4255614() {
        Node root = builder.add(4).add(2).add(5).add(5).add(6).add(1).add(4).build();
        String result = serializer.serialize(root);
        assertEquals("4:2, 2:1, 5:2, 1:1, , , 6:1", result);
    }

    @Test
    public void test3516402() {
        Node root = builder.add(3).add(5).add(1).add(6).add(4).add(0).add(2).build();
        String result = serializer.serialize(root);
        assertEquals("3:1, 1:1, 5:1, 0:1, 2:1, 4:1, 6:1", result);
    }

    @Test
    public void test3516402_3516402() {
        Node root = builder.add(3).add(5).add(1).add(6).add(4).add(0).add(2)
                           .add(3).add(5).add(1).add(6).add(4).add(0).add(2).build();
        String result = serializer.serialize(root);
        assertEquals("3:2, 1:2, 5:2, 0:2, 2:2, 4:2, 6:2", result);
    }
}
