var network;
var edges;
var nodes;

$('#myModal').on('shown.bs.modal', function () {
    $('#myInput').trigger('focus')
});

$(document).ready(function () {
    if (signedIn) {
        $("#signInButton").text("Signed in");
    }

    if (!etherscanApiEnabled) {
        $('#etherScanApiKeyPanel').hide();
    }

    if (signedIn && authorized && etherscanApiEnabled) {
        var applicationId = $("#applicationIdSelect").val();

        $.getJSON('/ether?applicationId=' + applicationId + '&etherscanApiKey=' + etherscanApiKey, function(mths) {
            if (mths.length == 0) {
                $("#etherMths tbody").append('<tr><td></td><td><i>No data found</i></td><td></td></tr>');
            }
            else {
                $.each(mths, function(i, item) {
                    $("#etherMths tbody").append('<tr><td>' + item.hash + '</td><td>' + item.timestamp +
                        '</td><td><a href="https://ropsten.etherscan.io/txs?block=' + item.blockNumber +
                        '" target="_blank">' + item.blockNumber + '</a></td></tr>');
                });
            }
        });
    }

    if (signedIn && authorized && applicationId != "") {
        $('#applicationId').value=applicationId;
    }

    if (signedIn && authorized && inclusionProof) {
        $('#etherScanApiKeyPanel').hide();
    }

    $('#connectEtherscan').change(function() {
        if ($('#connectEtherscan').prop('checked')) {
            $('#etherScanApiKeyPanel').show();
        } else {
            $('#etherScanApiKeyPanel').hide();
        }
    });

    $("#dashboardLink").attr("href", "?" + filterUrl(["hash", "mth"]));

    $("#inclusionLink").click(function() {
        $("#searchHash").val("");
        $("#searchHash").focus();
    });

    $("#consistencyLink").click(function() {
        $("#mth").focus();
    });

    $("#checkConsistency").click(function() {
        window.location=window.location.protocol + "//" + window.location.host + "/?" + filterUrl(["hash", "mth"]) +
                "&mth=" + $('#mth').val();
    });

    $("#checkHashChain").click(function() {
        window.location=window.location.protocol + "//" + window.location.host + "/?" + filterUrl(["hash", "mth",
                "startHash", "endHash"]) +
                "&startHash=" + $('#startHash').val() + "&endHash=" + $('#endHash').val();
    });

    $('#searchHash').on('keypress', function (e) {
        if (e.which === 13){
            window.location= "/?" + filterUrl(["hash", "mth"]) +
                "&hash=" + $('#searchHash').val();
        }
    });

    $('#applicationIdSelect').change(function() {
        window.location=window.location.protocol + "//" + window.location.host + "/?" + filterUrl(["applicationId"]) +
            "&applicationId=" + $('#applicationIdSelect').find(":selected").text();
    });

    if (signedIn && authorized && applicationId != "") {
        nodes = new vis.DataSet();
        edges = new vis.DataSet();

        var container = document.getElementById('merkleTreeVis');

        var data = {
            nodes: nodes,
            edges: edges
        };

        var options = {
            layout: {
                improvedLayout:true,
                hierarchical: {
                  enabled:true,
                  levelSeparation: 100,
                  nodeSpacing: 100,
                  treeSpacing: 100,
                  blockShifting: true,
                  edgeMinimization: true,
                  parentCentralization: true,
                  direction: 'UD',
                  sortMethod: 'directed'
                }
             }
        };

        var network = new vis.Network(container, data, options);

        network.on('click',  function (params) {
            var nodeId = params['nodes']['0'];

            if(nodeId){
                alert(nodes.get(nodeId).id);
            }
        });
    }

    $('#loadVis').on('click',displayMap);
});

function displayMap() {
    $("#merkleTreeVis").show();
    $('#loadVis').hide();

    var treeSizeOriginal = 0;

    if (merkleTreeSize > 32) {
        var noEntries = merkleTreeSize;

        while(noEntries > 32) {
            noEntries = Math.floor(noEntries / 2);
            console.log(noEntries);
        }

        treeSizeOriginal = noEntries;
    }
    else {
        treeSizeOriginal = merkleTreeSize;
    }

    getMap(treeSizeOriginal);
}

function getMap(treeSize) {
    nodes.clear();
    edges.clear();

    $.getJSON('/map?treeSize=' + treeSize + '', function(mapN) {
        edges.add(mapN.edges);
        nodes.add(mapN.nodes);
        /*
        for(var i in mapN.inclusionProof){
            var node2 = nodes.get(mapN.inclusionProof[i] - 1);

            node2.borderWidth = 3;

            node2.color ={
              border: '#2B7CE9'
            }

            nodes.update(node2);
        }
        */
        for(var i in mapN.leaves){
            var node = nodes.get(mapN.leaves[i]);

            if (merkleTreeSize > 32) {
                var id = "E" + node.id;
                nodes.add({"id":id,"label":"..."});
                edges.add({"from":node.id,"to":id});
            }
            else {
                if (leafIndex == node.id) {
                    node.color ={
                      background: '#a72828',
                      border: '#5d1212'
                    }
                }
                else {
                    node.color ={
                      background: '#0d7cab'
                    }
                }

                node.font ={
                    color: '#ffffff'
                }
            }

            nodes.update(node);
        }
    });
}

function filterUrl(ignoreParams) {
    var urlQuery = "";
    var parameters = window.location.href.replace('#','').slice(window.location.href.indexOf('?') + 1).split('&');

    if (parameters.length > 0) {
        if (parameters[0] == window.location.href) {
            return "";
        }
        else {
            for(var i = 0; i < parameters.length; i++)
            {
                var parameter = parameters[i].split('=');

                if (jQuery.inArray(parameter[0], ignoreParams) == -1) {
                    urlQuery += parameter[0] + "=" + parameter[1] + "&";
                }
            }
        }
    }

    return urlQuery.substring(0, urlQuery.length - 1);
}