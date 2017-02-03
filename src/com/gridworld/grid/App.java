package com.gridworld.grid;

import com.gridworld.exceptions.CoordinateException;

public class App {

	public static void main(String[] args) {
		Grid G = null;
		try {
			G = new Grid();
		} catch (CoordinateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(G.toString());
	}
}
