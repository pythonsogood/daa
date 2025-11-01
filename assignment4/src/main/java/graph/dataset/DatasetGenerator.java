package graph.dataset;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import graph.serialization.InputEdge;
import graph.serialization.InputJson;

public class DatasetGenerator {
	private final Random rand = new Random();
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private Path path = Paths.get("data");

	public DatasetGenerator(Path path) throws IOException {
		this.path = path;

		this.init();
	}

	public DatasetGenerator() throws IOException {
		this.init();
	}

	private void init() throws IOException {
		if (!Files.exists(this.path)) {
			Files.createDirectories(path);
		}
	}

	public void generateCategory(String name, Range<Integer> nodes, int count) throws IOException {
		for (int i=1; i<=count; i++) {
			int n = this.rand.nextInt(nodes.min, nodes.max);
			boolean cyclic = this.rand.nextBoolean();
			float density = this.rand.nextFloat(0.2f, 0.8f);

			List<InputEdge> edges = cyclic ? this.generateCyclic(n, density) : this.generateAcyclic(n, density);
			InputJson inputJson = new InputJson(n, edges, rand.nextInt(n));

			Path file = this.path.resolve(String.format("%s_%d.json", name, i));

			FileWriter fileWriter = new FileWriter(file.toFile());
			DatasetGenerator.gson.toJson(inputJson, fileWriter);
			fileWriter.close();
		}
	}

	private List<InputEdge> generateCyclic(int n, float density) {
		List<InputEdge> edges = new ArrayList<>();

		int maxEdges = (int) (density * n * (n - 1));

		for (int i=0; i<maxEdges; i++) {
			if (i == maxEdges - 1) {
				break;
			}

			int u = rand.nextInt(n);
			int v = rand.nextInt(n);
			while (u == v) {
				u = rand.nextInt(n);
				v = rand.nextInt(n);
			}

			int w = rand.nextInt(1, 10);

			edges.add(new InputEdge(u, v, w));
		}

		return edges;
	}

	private List<InputEdge> generateAcyclic(int n, float density) {
		List<InputEdge> edges = new ArrayList<>();
		List<Integer> nodes = new ArrayList<>();

		for (int i=0; i<n; i++) {
			nodes.add(i);
		}

		Collections.shuffle(nodes, this.rand);

		int maxEdges = (int) (density * n * (n - 1) / 2);

		for (int i=0; i<n; i++) {
			for (int j=i + 1; j<n && edges.size() < maxEdges; j++) {
				int u = nodes.get(i);
				int v = nodes.get(j);

				int w = rand.nextInt(1, 10);

				edges.add(new InputEdge(u, v, w));
			}
		}

		return edges;
	}
}
