package com.gridworld.algorithm;

public class Heuristics {
	// Weight
	private double w1 = 1;

	// Heuristics are stored here
	private double H(Vertex V, int i) {
		if (i == 0) {
			return 0;
		}
		return 1;
	}

	// Get key value
	public void storeNewKey(Vertex V, int i) {
		V.setKey(i, V.getG(i) + w1 * H(V, i));
	}
}
