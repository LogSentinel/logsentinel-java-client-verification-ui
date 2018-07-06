var network ;
var nodes;
var edges;

$(document).ready(function () {
// create an array with nodes
    nodes = new vis.DataSet([
        {id: 1, label: 'O', hash: '_XtRdD98Zq2SK8lfcEKZDwOkEFxobbyxQ4NUgMuI1-G6qu6LeMFul-uOGE1Sx9ecRHZRtGlT0Te-Z6ierliQNg=='},
        {id: 2, label: '2', hash: 'lpbhvoiyCS5M4NhO_qnCLNE064gLm_iw7vxx678WGFfbv8ThL9D9SBUUzRk1swizeEYhKSyHr0O8ZMbtME6SYQ=='},
        {id: 3, label: '3', hash: '6RK_IpNKUuDEaTXxcDfX3FG6Okg5SGfNFZysBEsPUErKycQrtDIz3use1p8edZ4tzX_9JRim8mnGTjbt12BZ0A=='},
        {id: 4, label: '-4', hash: '-D5KjVY7d6njTrKU5C-LE3d0SXepKQnffbonkGpXFtNP9-TnaynUS2vTYgyAotZMN-8f5q63LJYG7k8nIg6TJw=='},
		{id: 5, label: '5', hash: '_XtRdD98Zq2SK8lfcEKZDwOkEFxobbyxQ4NUgMuI1-G6qu6LeMFul-uOGE1Sx9ecRHZRtGlT0Te-Z6ierliQNg=='},
		{id: 6, label: '6', hash: '_XtRdD98Zq2SK8lfcEKZDwOkEFxobbyxQ4NUgMuI1-G6qu6LeMFul-uOGE1Sx9ecRHZRtGlT0Te-Z6ierliQNg=='},
		{id: 7, label: '7-', hash: '_XtRdD98Zq2SK8lfcEKZDwOkEFxobbyxQ4NUgMuI1-G6qu6LeMFul-uOGE1Sx9ecRHZRtGlT0Te-Z6ierliQNg=='},
		{id: 8, label: '8-', hash: '_XtRdD98Zq2SK8lfcEKZDwOkEFxobbyxQ4NUgMuI1-G6qu6LeMFul-uOGE1Sx9ecRHZRtGlT0Te-Z6ierliQNg=='}
    ]);

    // create an array with edges
    edges = new vis.DataSet([
        {from: 1, to: 3},
        {from: 1, to: 2},
        {from: 2, to: 4},
        {from: 2, to: 5},
		{from: 3, to: 6},
		{from: 3, to: 7},
		{from: 1, to: 8}
    ]);

    // create a network
    var container = document.getElementById('mynetwork');

    // provide the data in the vis format
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
              direction: 'UD',        // UD, DU, LR, RL
              sortMethod: 'directed'   // hubsize, directed
            }
         }
	};

    // initialize your network!
    var network = new vis.Network(container, data, options);

	network.on('click',  function (params) {

        // parse node id
        var nodeId = params['nodes']['0'];
		if(nodeId){
			alert(nodes.get(nodeId).hash);
		}
	});

}

function colorNodes(ids){
		for(var i in ids){
			var node = nodes.get(ids[i]);
			node.color ={
			  background: 'red',
			  highlight: {
				background: 'red'
			  }

			}
			nodes.update(node);
		}

	}