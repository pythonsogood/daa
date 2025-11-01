package graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import graph.dataset.DatasetGenerator;
import graph.dataset.Range;
import graph.instrumentation.Runner;
import graph.instrumentation.TimedOutput;
import graph.objects.CondensationGraph;
import graph.objects.Graph;
import graph.objects.Path;
import graph.serialization.InputJson;

public class Main {
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final Runner runner = new Runner();

	private static void runSample() throws FileNotFoundException {
		FileReader inputFileReader = new FileReader("sample/tasks.json");
		InputJson inputJson = gson.fromJson(inputFileReader, InputJson.class);

		Graph graph = new Graph(inputJson);

		TimedOutput<List<List<Integer>>> sccs = runner.scc(graph);

		CondensationGraph condensationGraph = new CondensationGraph(graph, sccs.output);
		Graph condensationGraphAsGraph = condensationGraph.toGraph();

		int condensationGraphSource = condensationGraph.vertexToSCC.get(inputJson.source);

		TimedOutput<List<Integer>> topo = runner.topo(condensationGraph, true);
		TimedOutput<Path> shortestPath = runner.shortestPath(condensationGraphAsGraph, condensationGraphSource);
		TimedOutput<Path> longestPath = runner.longestPath(condensationGraphAsGraph, condensationGraphSource);

		System.out.println(sccs);
		System.out.println(topo);
		System.out.println(shortestPath);
		System.out.println(longestPath);

		System.out.println(graph);
	}

	private static void generateDatasets() throws IOException {
		DatasetGenerator generator = new DatasetGenerator();

		generator.generateCategory("small", new Range<Integer>(6, 10), 3);
		generator.generateCategory("medium", new Range<Integer>(10, 20), 3);
		generator.generateCategory("large", new Range<Integer>(20, 50), 3);
	}

    public static void main(String[] args) throws IOException {
        // Main.generateDatasets();
		Main.runSample();
    }
}