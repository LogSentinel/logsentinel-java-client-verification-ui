var com;
(function (com) {
    var logsentinel;
    (function (logsentinel) {
        var verificationui;
        (function (verificationui) {
            var util;
            (function (util) {
                var VisualizationUtil = (function () {
                    function VisualizationUtil() {
                    }
                    VisualizationUtil.map_$LI$ = function () { if (VisualizationUtil.map == null)
                        VisualizationUtil.map = ({}); return VisualizationUtil.map; };
                    ;
                    VisualizationUtil.edgesL_$LI$ = function () { if (VisualizationUtil.edgesL == null)
                        VisualizationUtil.edgesL = ([]); return VisualizationUtil.edgesL; };
                    ;
                    VisualizationUtil.generateMap = function (treeSize) {
                        var id = 0;
                        var leaves = ([]);
                        var entriesI = ([]);
                        var inclusionProof = ([]);
                        /* add */ (inclusionProof.push(VisualizationUtil.leafIndex) > 0);
                        for (var i = 0; i <= treeSize - 1; i++) {
                            /* add */ (leaves.push(id) > 0);
                            /* add */ (entriesI.push("l" + i) > 0);
                            /* put */ (VisualizationUtil.map_$LI$()["l" + i] = id);
                            id++;
                        }
                        ;
                        var concat = ([]);
                        var leftAlone = "";
                        var entries = Math.floor((treeSize / 2 | 0));
                        while ((entries !== 0)) {
                            var addedEntries = ([]);
                            for (var i = 0; i <= entriesI.length - 1; i++) {
                                if (i % 2 === 0 && i !== entriesI.length - 1) {
                                    /* add */ (concat.push(/* get */ entriesI[i] + ":" + entriesI[i + 1]) > 0);
                                    /* put */ (VisualizationUtil.map_$LI$()[entriesI[i] + ":" + entriesI[i + 1]] = id);
                                    if ((inclusionProof.indexOf(((function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), /* get */ entriesI[i]))) >= 0)) {
                                        /* add */ (inclusionProof.push(id) > 0);
                                    }
                                    if ((inclusionProof.indexOf(((function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), /* get */ entriesI[i + 1]))) >= 0)) {
                                        /* add */ (inclusionProof.push(id) > 0);
                                    }
                                    /* add */ (VisualizationUtil.edgesL_$LI$().push(new VisualizationUtil.Edge(id, /* get */ (function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), /* get */ entriesI[i]))) > 0);
                                    /* add */ (VisualizationUtil.edgesL_$LI$().push(new VisualizationUtil.Edge(id, /* get */ (function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), /* get */ entriesI[i + 1]))) > 0);
                                    id++;
                                    /* add */ (addedEntries.push(/* get */ entriesI[i] + ":" + entriesI[i + 1]) > 0);
                                }
                                else if (i % 2 === 0 && i === entriesI.length - 1) {
                                    if (!(function (o1, o2) { if (o1 && o1.equals) {
                                        return o1.equals(o2);
                                    }
                                    else {
                                        return o1 === o2;
                                    } })(leftAlone, "")) {
                                        /* add */ (concat.push(/* get */ entriesI[i] + ":" + leftAlone) > 0);
                                        /* put */ (VisualizationUtil.map_$LI$()[entriesI[i] + ":" + leftAlone] = id);
                                        if ((inclusionProof.indexOf(((function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), /* get */ entriesI[i]))) >= 0)) {
                                            /* add */ (inclusionProof.push(id) > 0);
                                        }
                                        if ((inclusionProof.indexOf(((function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), leftAlone))) >= 0)) {
                                            /* add */ (inclusionProof.push(id) > 0);
                                        }
                                        /* add */ (VisualizationUtil.edgesL_$LI$().push(new VisualizationUtil.Edge(id, /* get */ (function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), /* get */ entriesI[i]))) > 0);
                                        /* add */ (VisualizationUtil.edgesL_$LI$().push(new VisualizationUtil.Edge(id, /* get */ (function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), leftAlone))) > 0);
                                        id++;
                                        /* add */ (addedEntries.push(/* get */ entriesI[i] + ":" + leftAlone) > 0);
                                        leftAlone = "";
                                    }
                                    else {
                                        leftAlone = entriesI[i];
                                    }
                                }
                            }
                            ;
                            /* clear */ (entriesI.length = 0);
                            /* addAll */ (function (l1, l2) { return l1.push.apply(l1, l2); })(entriesI, addedEntries);
                            /* clear */ (addedEntries.length = 0);
                            entries = Math.floor(entries / 2);
                        }
                        ;
                        if (entriesI.length === 1 && !(function (o1, o2) { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(leftAlone, "")) {
                            /* add */ (concat.push(/* get */ entriesI[0] + ":" + leftAlone) > 0);
                            /* put */ (VisualizationUtil.map_$LI$()[entriesI[0] + ":" + leftAlone] = id);
                            if ((inclusionProof.indexOf(((function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), /* get */ entriesI[0]))) >= 0)) {
                                /* add */ (inclusionProof.push(id) > 0);
                            }
                            if ((inclusionProof.indexOf(((function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), leftAlone))) >= 0)) {
                                /* add */ (inclusionProof.push(id) > 0);
                            }
                            /* add */ (VisualizationUtil.edgesL_$LI$().push(new VisualizationUtil.Edge(id, /* get */ (function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), /* get */ entriesI[0]))) > 0);
                            /* add */ (VisualizationUtil.edgesL_$LI$().push(new VisualizationUtil.Edge(id, /* get */ (function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), leftAlone))) > 0);
                            id++;
                        }
                        if (entriesI.length === 2) {
                            /* add */ (concat.push(/* get */ entriesI[0] + ":" + entriesI[1]) > 0);
                            /* put */ (VisualizationUtil.map_$LI$()[entriesI[0] + ":" + entriesI[1]] = id);
                            if ((inclusionProof.indexOf(((function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), /* get */ entriesI[0]))) >= 0)) {
                                /* add */ (inclusionProof.push(id) > 0);
                            }
                            if ((inclusionProof.indexOf(((function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), /* get */ entriesI[1]))) >= 0)) {
                                /* add */ (inclusionProof.push(id) > 0);
                            }
                            /* add */ (VisualizationUtil.edgesL_$LI$().push(new VisualizationUtil.Edge(id, /* get */ (function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), /* get */ entriesI[0]))) > 0);
                            /* add */ (VisualizationUtil.edgesL_$LI$().push(new VisualizationUtil.Edge(id, /* get */ (function (m, k) { return m[k] ? m[k] : null; })(VisualizationUtil.map_$LI$(), /* get */ entriesI[1]))) > 0);
                            id++;
                        }
                        var nodesL = ([]);
                        {
                            var array258 = (function (o) { var s = []; for (var e in o)
                                s.push({ k: e, v: o[e], getKey: function () { return this.k; }, getValue: function () { return this.v; } }); return s; })(VisualizationUtil.map_$LI$());
                            for (var index257 = 0; index257 < array258.length; index257++) {
                                var entry = array258[index257];
                                {
                                    if (entry.getValue() <= leaves.length - 1) {
                                        /* add */ (nodesL.push(new VisualizationUtil.Node(entry.getValue(), "L" + (entry.getValue() + 1))) > 0);
                                    }
                                    else if (entry.getValue() + 1 === Object.keys(VisualizationUtil.map_$LI$()).length) {
                                        /* add */ (nodesL.push(new VisualizationUtil.Node(entry.getValue(), "MTH")) > 0);
                                    }
                                    else {
                                        if ((inclusionProof.indexOf((entry.getValue() + 1)) >= 0)) {
                                            /* add */ (nodesL.push(new VisualizationUtil.Node(entry.getValue(), "N*")) > 0);
                                        }
                                        else {
                                            /* add */ (nodesL.push(new VisualizationUtil.Node(entry.getValue(), "N")) > 0);
                                        }
                                    }
                                }
                            }
                        }
                        return new VisualizationUtil.TreeMap(VisualizationUtil.edgesL_$LI$(), nodesL, leaves, inclusionProof);
                    };
                    return VisualizationUtil;
                }());
                VisualizationUtil.leafIndex = 2;
                util.VisualizationUtil = VisualizationUtil;
                VisualizationUtil["__class"] = "com.logsentinel.verificationui.util.VisualizationUtil";
                (function (VisualizationUtil) {
                    var Edge = (function () {
                        function Edge(from, to) {
                            this.from = 0;
                            this.to = 0;
                            this.from = from;
                            this.to = to;
                        }
                        Edge.prototype.getFrom = function () {
                            return this.from;
                        };
                        Edge.prototype.setFrom = function (from) {
                            this.from = from;
                        };
                        Edge.prototype.getTo = function () {
                            return this.to;
                        };
                        Edge.prototype.setTo = function (to) {
                            this.to = to;
                        };
                        return Edge;
                    }());
                    VisualizationUtil.Edge = Edge;
                    Edge["__class"] = "com.logsentinel.verificationui.util.VisualizationUtil.Edge";
                    var TreeMap = (function () {
                        function TreeMap(edges, nodes, leaves, inclusionProof) {
                            this.edges = null;
                            this.nodes = null;
                            this.leaves = null;
                            this.inclusionProof = null;
                            this.edges = edges;
                            this.nodes = nodes;
                            this.leaves = leaves;
                            this.inclusionProof = inclusionProof;
                        }
                        TreeMap.prototype.getEdges = function () {
                            return this.edges;
                        };
                        TreeMap.prototype.setEdges = function (edges) {
                            this.edges = edges;
                        };
                        TreeMap.prototype.getNodes = function () {
                            return this.nodes;
                        };
                        TreeMap.prototype.setNodes = function (nodes) {
                            this.nodes = nodes;
                        };
                        TreeMap.prototype.getLeaves = function () {
                            return this.leaves;
                        };
                        TreeMap.prototype.setLeaves = function (leaves) {
                            this.leaves = leaves;
                        };
                        TreeMap.prototype.getInclusionProof = function () {
                            return this.inclusionProof;
                        };
                        TreeMap.prototype.setInclusionProof = function (inclusionProof) {
                            this.inclusionProof = inclusionProof;
                        };
                        return TreeMap;
                    }());
                    VisualizationUtil.TreeMap = TreeMap;
                    TreeMap["__class"] = "com.logsentinel.verificationui.util.VisualizationUtil.TreeMap";
                    var Node = (function () {
                        function Node(id, label) {
                            this.id = 0;
                            this.label = null;
                            this.id = id;
                            this.label = label;
                        }
                        Node.prototype.getId = function () {
                            return this.id;
                        };
                        Node.prototype.setId = function (id) {
                            this.id = id;
                        };
                        Node.prototype.getLabel = function () {
                            return this.label;
                        };
                        Node.prototype.setLabel = function (label) {
                            this.label = label;
                        };
                        return Node;
                    }());
                    VisualizationUtil.Node = Node;
                    Node["__class"] = "com.logsentinel.verificationui.util.VisualizationUtil.Node";
                })(VisualizationUtil = util.VisualizationUtil || (util.VisualizationUtil = {}));
            })(util = verificationui.util || (verificationui.util = {}));
        })(verificationui = logsentinel.verificationui || (logsentinel.verificationui = {}));
    })(logsentinel = com.logsentinel || (com.logsentinel = {}));
})(com || (com = {}));
com.logsentinel.verificationui.util.VisualizationUtil.edgesL_$LI$();
com.logsentinel.verificationui.util.VisualizationUtil.map_$LI$();