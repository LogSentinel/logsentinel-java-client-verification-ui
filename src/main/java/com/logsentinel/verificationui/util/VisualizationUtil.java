package com.logsentinel.verificationui.util;

import com.logsentinel.verificationui.model.Edge;
import com.logsentinel.verificationui.model.Node;
import com.logsentinel.verificationui.model.TreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisualizationUtil {
    private static Map<String, Integer> map = new HashMap<>();
    private static Map<Integer, Integer> edges = new HashMap<>();

    private static List<Edge> edgesL = new ArrayList<>();

    public static TreeMap generateMap(int treeSize) {
        int id = 0;
        List<Integer> leaves = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();

        List<String> entriesI = new ArrayList<>();

        for (int i = 0; i <= treeSize - 1; i++) {
            leaves.add(id);
            nodes.add(new Node(id, "l" + i));
            entriesI.add("l" + i);
            map.put("l" + i, id);
            id++;
        }

        List<String> concat = new ArrayList<>();
        String leftOutside = "";

        double entries = Math.floor(treeSize / 2);

        while (entries != 0) {
            List<String> addedEntries = new ArrayList<>();
            for (int i = 0; i <= entriesI.size() - 1; i++) {
                if (i % 2 == 0 && i != entriesI.size() - 1) {
                    concat.add(entriesI.get(i) + ":" + entriesI.get(i + 1));
                    map.put(entriesI.get(i) + ":" + entriesI.get(i + 1), id);


                    edgesL.add(new Edge(id, map.get(entriesI.get(i))));
                    edgesL.add(new Edge(id, map.get(entriesI.get(i + 1))));
                    id++;

                    edges.put(id, map.get(entriesI.get(i)));
                    edges.put(id, map.get(entriesI.get(i + 1)));

                    addedEntries.add(entriesI.get(i) + ":" + entriesI.get(i + 1));
                }
                else if (i % 2 == 0 && i == entriesI.size() - 1) {
                    if (!leftOutside.equals("")) {
                        concat.add(entriesI.get(i) + ":" + leftOutside);
                        map.put(entriesI.get(i) + ":" + leftOutside, id);

                        edgesL.add(new Edge(id, map.get(entriesI.get(i))));
                        edgesL.add(new Edge(id, map.get(leftOutside)));

                        id++;
                        addedEntries.add(entriesI.get(i) + ":" + leftOutside);

                        edges.put(id, map.get(entriesI.get(i)));
                        edges.put(id, map.get(leftOutside));


                        leftOutside = "";
                    }
                    else {
                        leftOutside = entriesI.get(i);
                    }
                }
            }

            entriesI.clear();

            entriesI.addAll(addedEntries);
            addedEntries.clear();

            entries = Math.floor(entries / 2);
        }

        if (entriesI.size() == 1 && !leftOutside.equals("")) {
            concat.add(entriesI.get(0) + ":" + leftOutside);
            map.put(entriesI.get(0) + ":" + leftOutside, id);
            edgesL.add(new Edge(id, map.get(entriesI.get(0))));
            edgesL.add(new Edge(id, map.get(leftOutside)));
            id++;
        }

        if (entriesI.size() == 2) {
            concat.add(entriesI.get(0) + ":" + entriesI.get(1));
            map.put(entriesI.get(0) + ":" + entriesI.get(1), id);
            edges.put(map.get(entriesI.get(0)), map.get(entriesI.get(1)));
            edgesL.add(new Edge(id, map.get(entriesI.get(0))));
            edgesL.add(new Edge(id, map.get(entriesI.get(1))));
            id++;
        }

        List<Node> nodesL = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() <= nodes.size() - 1) {
                nodesL.add(new Node(entry.getValue(), "L" + (entry.getValue() + 1)));
            } else if (entry.getValue() + 1 == map.size()) {
                nodesL.add(new Node(entry.getValue(), "MTH"));
            } else {
                nodesL.add(new Node(entry.getValue(), ""));
            }
        }

        return new TreeMap(edgesL, nodesL, leaves);
    }
}
