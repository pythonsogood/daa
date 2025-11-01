package graph.instrumentation;

public class TimedMetrics extends SimpleMetrics {
	private long start;

	public TimedMetrics() {
		super();
	}

	public void startTimer() {
		this.start = System.nanoTime();
	}

	public long stopTimer() {
		long elapsed = System.nanoTime() - this.start;
		this.start = 0;
		return elapsed;
	}
}
