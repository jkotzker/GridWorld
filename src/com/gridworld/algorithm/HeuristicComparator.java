package com.gridworld.algorithm;

import java.util.Comparator;

public class HeuristicComparator implements Comparator<Vertex> {
	// StringLengthComparator.java

	@Override
	public int compare(Vertex v1, Vertex v2) {
		// Assume neither string is null. Real code should
		// probably be more robust
		// You could also just return x.length() - y.length(),
		// which would be more efficient.
		if (v1.g + v1.h < v2.h + v2.h) {
			return -1;
		}
		if (v1.g + v1.h > v2.g + v2.h) {
			return 1;
		}
		return 0;
	}
}
