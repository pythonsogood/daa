package org.pythonsogood;

public class Point {
	public int x;
	public int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public double distanceTo(Point point) {
        return Math.sqrt(Math.pow(this.x - point.x, 2) + Math.pow(this.y - point.y, 2));
    }

	@Override
	public String toString() {
		return String.format("(%s, %s)", this.x, this.y);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Point)) {
			return false;
		}

		Point point = (Point) o;

		return point.x == this.x && point.y == this.y;
	}
}