package graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.siegmar.fastcsv.writer.CsvWriter;
import graph.dataset.DatasetGenerator;
import graph.dataset.Range;
import graph.instrumentation.Runner;
import graph.instrumentation.TimedOutput;
import graph.objects.CondensationGraph;
import graph.objects.DAGSPath;
import graph.objects.Graph;
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
		TimedOutput<DAGSPath> shortestPath = runner.shortestPath(condensationGraphAsGraph, condensationGraphSource);
		TimedOutput<DAGSPath> longestPath = runner.longestPath(condensationGraphAsGraph, condensationGraphSource);

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

	private static void runDatasets() throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		Runner runner = new Runner();

		Stream<Path> directoryStream = Files.list(Path.of("data"));
		List<String> inputFiles = directoryStream.filter(
			file -> !Files.isDirectory(file) && file.getFileName().toString().endsWith(".json")
		).map(Path::getFileName).map(Path::toString).collect(Collectors.toList());
		directoryStream.close();

		Path csvFile = Paths.get("metrics.csv");

		if (Files.exists(csvFile)) {
			Files.delete(csvFile);
		}

		Files.createFile(csvFile);

		try (CsvWriter csv = CsvWriter.builder().build(csvFile)) {
			csv.writeRecord("dataset", "n", "edges", "dag_source", "sccs", "sccs_time", "topo_time", "sp_time", "lp_time", "total_time");

			for (String inputFile : inputFiles) {
				System.out.println(String.format("Processing %s", inputFile));

				FileReader inputReader = new FileReader(String.format("data/%s", inputFile));
				InputJson inputJson = gson.fromJson(inputReader, InputJson.class);
				inputReader.close();

				Graph graph = new Graph(inputJson);

				TimedOutput<List<List<Integer>>> sccs = runner.scc(graph);

				CondensationGraph condensationGraph = new CondensationGraph(graph, sccs.output);
				Graph condensationGraphAsGraph = condensationGraph.toGraph();

				int condensationGraphSource = condensationGraph.vertexToSCC.get(inputJson.source);

				TimedOutput<List<Integer>> topo = runner.topo(condensationGraph, true);
				TimedOutput<DAGSPath> shortestPath = runner.shortestPath(condensationGraphAsGraph, condensationGraphSource);
				TimedOutput<DAGSPath> longestPath = runner.longestPath(condensationGraphAsGraph, condensationGraphSource);

				long totalTime = sccs.time + topo.time + shortestPath.time + longestPath.time;

				csv.writeRecord(String.valueOf(inputFile), String.valueOf(inputJson.n), String.valueOf(inputJson.edges.size()), String.valueOf(inputJson.source), String.valueOf(sccs.output.size()), String.valueOf(sccs.time), String.valueOf(topo.time), String.valueOf(shortestPath.time), String.valueOf(longestPath.time), String.valueOf(totalTime));
			}
		}
	}

    public static void main(String[] args) throws IOException {
        // Main.generateDatasets();
		// Main.runSample();
		Main.runDatasets();
    }
}