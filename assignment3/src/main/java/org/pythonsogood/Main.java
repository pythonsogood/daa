package org.pythonsogood;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.pythonsogood.models.InputJson;
import org.pythonsogood.models.OutputJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) throws IOException {
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
}