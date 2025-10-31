package graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import graph.dagsp.DAGPath;
import graph.objects.CondensationGraph;
import graph.objects.Graph;
import graph.objects.Path;
import graph.scc.Kosaraju;
import graph.serialization.InputJson;
import graph.topo.DFSTopo;

public class Main {
	private static void runSample() throws FileNotFoundException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		FileReader inputFileReader = new FileReader("sample/tasks.json");
		InputJson inputJson = gson.fromJson(inputFileReader, InputJson.class);

		Graph graph = new Graph(inputJson);

		List<List<Integer>> sccs = Kosaraju.scc(graph);
		List<Integer> topo = DFSTopo.sort(graph);
		Path shortestPath = DAGPath.shortestPath(graph, inputJson.source);
		CondensationGraph condensationGraph = new CondensationGraph(graph, sccs);
		List<Integer> condensationGraphTopo = DFSTopo.sort(condensationGraph);
		List<Integer> condensationGraphTopoDecompressed = DFSTopo.sort(condensationGraph, true);
		Path condensationGraphShortestPath = DAGPath.shortestPath(condensationGraph.toGraph(), condensationGraph.vertexToSCC.get(inputJson.source));

		System.out.println(sccs);
		System.out.println(topo);
		System.out.println(shortestPath.construct());

		System.out.println(condensationGraph);
		System.out.println(condensationGraphTopo);
		System.out.println(condensationGraphTopoDecompressed);
		System.out.println(condensationGraphShortestPath.construct());

		System.out.println(graph);
	}

    public static void main(String[] args) throws FileNotFoundException {
        Main.runSample();
    }
}