package graph.instrumentation;

import java.util.HashMap;
import java.util.Map;

public class SimpleMetrics implements Metrics<Integer> {
	private Map<String, Integer> metrics = new HashMap<>();

	public SimpleMetrics() {}

	@Override
	public void add(String metric) {
		this.add(metric, 1);
	}

	@Override
	public void add(String metric, Integer value) {
		this.metrics.put(metric, this.metrics.getOrDefault(metric, 0) + value);
	}

	@Override
	public void clear(String metric) {
		this.metrics.remove(metric);
	}

	@Override
	public void reset() {
		this.metrics.clear();
	}

	@Override
	public Integer get(String metric) {
		return this.metrics.getOrDefault(metric, 0);
	}
}
