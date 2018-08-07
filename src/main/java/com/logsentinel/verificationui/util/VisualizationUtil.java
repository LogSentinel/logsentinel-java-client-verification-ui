package com.logsentinel.verificationui.util;

import com.logsentinel.verificationui.LogSentinelClientUiApplication;
import com.logsentinel.verificationui.model.Edge;
import com.logsentinel.verificationui.model.Node;
import com.logsentinel.verificationui.model.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisualizationUtil {
    private static Map<String, Integer> map = new HashMap<>();
    private static List<Edge> edgesL = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(LogSentinelClientUiApplication.class);

    private static int leafIndex = 2;

    public static TreeMap generateMap(int treeSize) {
        int id = 0;

        List<Integer> leaves = new ArrayList<>();

        List<String> entriesI = new ArrayList<>();

        List<Integer> inclusionProof = new ArrayList<>();
        inclusionProof.add(leafIndex);

        for (int i = 0; i <= treeSize - 1; i++) {
            leaves.add(id);
            entriesI.add("l" + i);
            map.put("l" + i, id);
            id++;
        }

        List<String> concat = new ArrayList<>();

        String leftAlone = "";

        double entries = Math.floor(treeSize / 2);

        while (entries != 0) {
            List<String> addedEntries = new ArrayList<>();

            for (int i = 0; i <= entriesI.size() - 1; i++) {
                if (i % 2 == 0 && i != entriesI.size() - 1) {
                    concat.add(entriesI.get(i) + ":" + entriesI.get(i + 1));
                    map.put(entriesI.get(i) + ":" + entriesI.get(i + 1), id);

                    if (inclusionProof.contains(map.get(entriesI.get(i)))) {
                        inclusionProof.add(id);
                    }

                    if (inclusionProof.contains(map.get(entriesI.get(i + 1)))) {
                        inclusionProof.add(id);
                    }

                    edgesL.add(new Edge(id, map.get(entriesI.get(i))));
                    edgesL.add(new Edge(id, map.get(entriesI.get(i + 1))));
                    id++;

                    addedEntries.add(entriesI.get(i) + ":" + entriesI.get(i + 1));
                }
                else if (i % 2 == 0 && i == entriesI.size() - 1) {
                    if (!leftAlone.equals("")) {
                        concat.add(entriesI.get(i) + ":" + leftAlone);
                        map.put(entriesI.get(i) + ":" + leftAlone, id);

                        if (inclusionProof.contains(map.get(entriesI.get(i)))) {
                            inclusionProof.add(id);
                        }

                        if (inclusionProof.contains(map.get(leftAlone))) {
                            inclusionProof.add(id);
                        }

                        edgesL.add(new Edge(id, map.get(entriesI.get(i))));
                        edgesL.add(new Edge(id, map.get(leftAlone)));
                        id++;

                        addedEntries.add(entriesI.get(i) + ":" + leftAlone);

                        leftAlone = "";
                    }
                    else {
                        leftAlone = entriesI.get(i);
                    }
                }
            }

            entriesI.clear();

            entriesI.addAll(addedEntries);
            addedEntries.clear();

            entries = Math.floor(entries / 2);
        }

        if (entriesI.size() == 1 && !leftAlone.equals("")) {
            concat.add(entriesI.get(0) + ":" + leftAlone);
            map.put(entriesI.get(0) + ":" + leftAlone, id);

            if (inclusionProof.contains(map.get(entriesI.get(0)))) {
                inclusionProof.add(id);
            }

            if (inclusionProof.contains(map.get(leftAlone))) {
                inclusionProof.add(id);
            }

            edgesL.add(new Edge(id, map.get(entriesI.get(0))));
            edgesL.add(new Edge(id, map.get(leftAlone)));
            id++;
        }

        if (entriesI.size() == 2) {
            concat.add(entriesI.get(0) + ":" + entriesI.get(1));
            map.put(entriesI.get(0) + ":" + entriesI.get(1), id);

            if (inclusionProof.contains(map.get(entriesI.get(0)))) {
                inclusionProof.add(id);
            }

            if (inclusionProof.contains(map.get(entriesI.get(1)))) {
                inclusionProof.add(id);
            }

            edgesL.add(new Edge(id, map.get(entriesI.get(0))));
            edgesL.add(new Edge(id, map.get(entriesI.get(1))));
            id++;
        }

        List<Node> nodesL = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() <= leaves.size() - 1) {
                nodesL.add(new Node(entry.getValue(), ""));
            } else if (entry.getValue() + 1 == map.size()) {
                nodesL.add(new Node(entry.getValue(), "MTH"));
            } else {
                if (inclusionProof.contains(entry.getValue() + 1)) {
                    nodesL.add(new Node(entry.getValue(), "N"));
                }
                else {
                    nodesL.add(new Node(entry.getValue(), "N"));
                }
            }
        }

        return new TreeMap(edgesL, nodesL, leaves, inclusionProof);
    }
}
