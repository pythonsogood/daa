package org.pythonsogood;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.pythonsogood.algorithm.KMP;
import org.pythonsogood.serialization.InputJson;
import org.pythonsogood.serialization.OutputJson;

import com.google.gson.Gson;

import de.siegmar.fastcsv.writer.CsvWriter;

public class Main {
	static Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
		// Warm-up JIT, because first call is slow
		Random rand = new Random();
		for (int i=0; i<1000; i++) {
			String pattern = "";

			for (int j=0; j<rand.nextInt(2, 5); j++) {
				pattern += (char) rand.nextInt(97, 100);
			}

			String text = "";

			for (int j=0; j<rand.nextInt(5, 20); j++) {
				text += (char) rand.nextInt(97, 100);
			}

			KMP.search(pattern, text);
		}

		Path csvFile = Paths.get("metrics.csv");

		if (Files.exists(csvFile)) {
			Files.delete(csvFile);
		}

		Files.createFile(csvFile);

		List<String> samples = new ArrayList<>(List.of("small", "medium", "long"));

		try (CsvWriter csv = CsvWriter.builder().build(csvFile)) {
			csv.writeRecord("dataset", "n", "m", "time");

			for (String sampleName : samples) {
				FileReader inputFileReader = new FileReader(String.format("sample/input_%s.json", sampleName));
				InputJson inputJson = gson.fromJson(inputFileReader, InputJson.class);
				inputFileReader.close();

				FileReader outputFileReader = new FileReader(String.format("sample/output_%s.json", sampleName));
				OutputJson expectedOutputJson = gson.fromJson(outputFileReader, OutputJson.class);
				outputFileReader.close();

				long startTime = System.nanoTime();

				List<Integer> matches = KMP.search(inputJson.pattern, inputJson.text);

				long totalTime = System.nanoTime() - startTime;

				OutputJson outputJson = new OutputJson(inputJson.text, inputJson.pattern, matches);

				if (!outputJson.equals(expectedOutputJson)) {
					throw new RuntimeException("Output does not match expected output");
				}

				csv.writeRecord(sampleName, String.valueOf(inputJson.text.length()), String.valueOf(inputJson.pattern.length()), String.valueOf(totalTime));
			}
		}
    }
}