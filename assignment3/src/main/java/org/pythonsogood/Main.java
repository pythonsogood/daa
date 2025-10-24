package org.pythonsogood;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.pythonsogood.graph.Edge;
import org.pythonsogood.graph.Graph;
import org.pythonsogood.graph.Vertex;
import org.pythonsogood.models.GraphJson;
import org.pythonsogood.models.InputJson;
import org.pythonsogood.models.OutputJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
	private static boolean sample() throws IOException {
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

		return output.equals(sampleOutput, true);
	}

	private static Graph generateGraph(int vertexCount, int edgeCount) {
		List<String> nodes = new ArrayList<>();

		for (int i=0; i<vertexCount; i++) {
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

	private static InputJson generateInputData(int graphCount) {
		List<GraphJson> graphs = new ArrayList<>();

		for (int i=0; i<graphCount; i++) {
			graphs.add(new GraphJson(i, Main.generateGraph(5, 5)));
		}

		return new InputJson(graphs);
	}

	private static void generateData() throws IOException {
		InputJson inputJson = Main.generateInputData(5);
	}

    public static void main(String[] args) throws IOException {
		// System.out.println(String.format("Sample input's output matches sample output: %s", Main.sample()));
    }
}