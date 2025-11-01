package graph.instrumentation;

public interface Metrics<T> {
	void add(String metric);
	void add(String metric, T value);
	void clear(String metric);
	void reset();
	T get(String metric);
}
