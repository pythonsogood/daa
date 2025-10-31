package org.pythonsogood;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.pythonsogood.algorithms.DFSTopo;
import org.pythonsogood.algorithms.Kosaraju;
import org.pythonsogood.algorithms.Tarjan;
import org.pythonsogood.graph.Graph;
import org.pythonsogood.models.InputJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
	private static void runSample() throws FileNotFoundException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		FileReader inputFileReader = new FileReader("sample/tasks.json");
		InputJson inputJson = gson.fromJson(inputFileReader, InputJson.class);

		Graph graph = new Graph(inputJson);
		System.out.println(Tarjan.SCC(graph));
		System.out.println(Kosaraju.SCC(graph));

		System.out.println(DFSTopo.sort(graph));
	}

    public static void main(String[] args) throws FileNotFoundException {
        Main.runSample();
    }
}