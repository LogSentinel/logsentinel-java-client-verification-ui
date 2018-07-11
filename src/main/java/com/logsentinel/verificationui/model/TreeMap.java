package com.logsentinel.verificationui.model;

import java.util.List;

public class TreeMap {
    public List<Edge> edges;
    public List<Node> nodes;
    public List<Integer> leaves;

    public TreeMap(List<Edge> edges, List<Node> nodes, List<Integer> leaves) {
        this.edges = edges;
        this.nodes = nodes;
        this.leaves = leaves;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Integer> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<Integer> leaves) {
        this.leaves = leaves;
    }
}
