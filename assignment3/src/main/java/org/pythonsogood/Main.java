package org.pythonsogood;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.pythonsogood.enums.GraphSize;
import org.pythonsogood.generic.Range;
import org.pythonsogood.graph.Edge;
import org.pythonsogood.graph.Graph;
import org.pythonsogood.graph.Vertex;
import org.pythonsogood.models.GraphJson;
import org.pythonsogood.models.InputJson;
import org.pythonsogood.models.OutputJson;
import org.pythonsogood.models.OutputResultJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.siegmar.fastcsv.writer.CsvWriter;

public class Main {
	static HashMap<GraphSize, Range<Integer>> GRAPH_SIZES = new HashMap<>(Map.of(
		GraphSize.SMALL, new Range<>(4, 6),
		GraphSize.MEDIUM, new Range<>(10, 15),
		GraphSize.LARGE, new Range<>(20, 30)
	));

	private static Graph generateGraph(int vertexCount, int edgeCount) {
		List<String> nodes = new ArrayList<>();

		for (int i=1; i<=vertexCount; i++) {
			String node = "";

			int index = i;

			while (index > 0) {
				index--;

				node = String.format("%s%s", String.valueOf((char) ('A' + (index % 26))), node);

				index /= 26;
			}

			nodes.add(node);
		}

		List<Vertex> vertices = new ArrayList<>();

		for (String node : nodes) {
			vertices.add(new Vertex(node));
		}

		Random rand = new Random();
		Set<String> edgeSet = new HashSet<>();
		List<Edge> edges = new ArrayList<>();

		while (edges.size() < edgeCount) {
			int i = rand.nextInt(vertexCount);
			int j = rand.nextInt(vertexCount);

			if (i == j) {
				continue;
			}

			String key = i < j ? String.format("%s-%s", i, j) : String.format("%s-%s", j, i);

			if (edgeSet.contains(key)) {
				continue;
			}

			edgeSet.add(key);
			int weight = rand.nextInt(1, 10);
			edges.add(new Edge(nodes.get(i), nodes.get(j), weight));
		}

		return new Graph(vertices, edges);
	}

	private static InputJson generateInputData(int graphCount, GraphSize size) {
		List<GraphJson> graphs = new ArrayList<>();

		Random rand = new Random();

		Range<Integer> graphVertexRange = Main.GRAPH_SIZES.get(size);

		for (int i=1; i<=graphCount; i++) {
			int vertices = rand.nextInt(graphVertexRange.min, graphVertexRange.max);
			int edges = rand.nextInt(100) < 90 ? rand.nextInt(vertices - 1, (vertices * (vertices - 1)) / 2) : rand.nextInt(Math.max(vertices - 3, 0), Math.min(vertices + 3, (vertices * (vertices - 1)) / 2));

			graphs.add(new GraphJson(i, Main.generateGraph(vertices, edges)));
		}

		return new InputJson(graphs);
	}

	private static void generateData() throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		Runner runner = new Runner();

		InputJson smallInputJson = Main.generateInputData(5, GraphSize.SMALL);
		FileWriter smallInputWriter = new FileWriter("dataset/input-small.json");
		gson.toJson(smallInputJson, smallInputWriter);
		smallInputWriter.close();

		OutputJson smallOutputJson = runner.runInput(smallInputJson);
		FileWriter smallOutputWriter = new FileWriter("dataset/output-small.json");
		gson.toJson(smallOutputJson, smallOutputWriter);
		smallOutputWriter.close();

		InputJson mediumInputJson = Main.generateInputData(5, GraphSize.MEDIUM);
		FileWriter mediumInputWriter = new FileWriter("dataset/input-medium.json");
		gson.toJson(mediumInputJson, mediumInputWriter);
		mediumInputWriter.close();

		OutputJson mediumOutputJson = runner.runInput(mediumInputJson);
		FileWriter mediumOutputWriter = new FileWriter("dataset/output-medium.json");
		gson.toJson(mediumOutputJson, mediumOutputWriter);
		mediumOutputWriter.close();

		InputJson largeInputJson = Main.generateInputData(5, GraphSize.LARGE);
		FileWriter largeInputWriter = new FileWriter("dataset/input-large.json");
		gson.toJson(largeInputJson, largeInputWriter);
		largeInputWriter.close();

		OutputJson largeOutputJson = runner.runInput(largeInputJson);
		FileWriter largeOutputWriter = new FileWriter("dataset/output-large.json");
		gson.toJson(largeOutputJson, largeOutputWriter);
		largeOutputWriter.close();
	}

	private static void runSample() throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		FileReader inputReader = new FileReader("sample/input.json");
		InputJson input = gson.fromJson(inputReader, InputJson.class);
		inputReader.close();

		Runner runner = new Runner();

		OutputJson output = runner.runInput(input);

		FileWriter outputWriter = new FileWriter("output.json");
		gson.toJson(output, outputWriter);
		outputWriter.close();

		FileReader sampleOutputReader = new FileReader("sample/output.json");
		OutputJson sampleOutput = gson.fromJson(sampleOutputReader, OutputJson.class);
		sampleOutputReader.close();

		System.out.println(String.format("Sample input's output matches sample output: %s", output.equals(sampleOutput, true)));
	}

	private static void runDataset() throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		Runner runner = new Runner();

		Stream<Path> directoryStream = Files.list(Path.of("dataset"));
		List<String> inputFiles = directoryStream.filter(
			file -> !Files.isDirectory(file) && file.getFileName().toString().startsWith("input") && file.getFileName().toString().endsWith(".json")
		).map(Path::getFileName).map(Path::toString).collect(Collectors.toList());
		directoryStream.close();

		for (String inputFile : inputFiles) {
			FileReader inputReader = new FileReader(String.format("dataset/%s", inputFile));
			InputJson inputJson = gson.fromJson(inputReader, InputJson.class);
			inputReader.close();

			OutputJson expectedOutputJson = null;

			try (FileReader expectedOutputReader = new FileReader(String.format("dataset/%s", inputFile.replace("input", "output")))) {
				expectedOutputJson = gson.fromJson(expectedOutputReader, OutputJson.class);
				expectedOutputReader.close();
			} catch (FileNotFoundException e) {}

			OutputJson output = runner.runInput(inputJson);

			if (expectedOutputJson != null) {
				System.out.println(String.format("%s matching with expected output: %s", inputFile, output.equals(expectedOutputJson, true)));
			} else {
				System.out.println(String.format("%s does not have expected output", inputFile));
			}

			Path file = Paths.get(String.format("output/%s", inputFile.replace("input-", "").replace(".json", ".csv")));

			if (Files.exists(file)) {
				Files.delete(file);
			}
			Files.createFile(file);

			try (CsvWriter csv = CsvWriter.builder().build(file)) {
				csv.writeRecord("graph_id", "vertex_count", "edge_count", "prim_cost", "kruskal_cost", "prim_operations", "kruskal_operations", "prim_time", "kruskal_time");

				for (OutputResultJson result : output.results) {
					csv.writeRecord(String.valueOf(result.graph_id), String.valueOf(result.input_stats.vertices), String.valueOf(result.input_stats.edges), String.valueOf(result.prim.total_cost), String.valueOf(result.kruskal.total_cost), String.valueOf(result.prim.operations_count), String.valueOf(result.kruskal.operations_count), String.valueOf(result.prim.execution_time_ms), String.valueOf(result.kruskal.execution_time_ms));
				}
			}
		}
	}

    public static void main(String[] args) throws IOException {
		Main.runSample();
		Main.runDataset();
    }
}