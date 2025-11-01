package graph.instrumentation;

public class TimedOutput<T> {
	public final T output;
	public final long time;

	public TimedOutput(T output, long time) {
		this.output = output;
		this.time = time;
	}

	@Override
	public String toString() {
		return String.format("%s (%s ms)", output, time / 1000);
	}
}
