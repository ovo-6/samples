package com.ovo.graph;

import java.util.ArrayList;

public interface GraphWalker {

    /**
     *
     * @param node - start
     * @return should return a ArrayList containing every GNode in the
     *         graph. Each node should appear in the ArrayList exactly once (i.e. no duplicates).
     */
    public ArrayList<GNode> walkGraph(GNode node);
}
