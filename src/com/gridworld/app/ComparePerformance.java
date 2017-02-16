package com.gridworld.app;

import com.gridworld.exceptions.CoordinateException;
import com.gridworld.grid.FiftyGrids;

public class ComparePerformance {
	
	static FiftyGrids gridList;
	
	public static void comparePerformance() {
		
		// Set search type, Uniform-Cost, A*, Weighted A*.
		String type ="A*";
		String heur = "1";
		//String weight = "";
		
		System.out.println("Computing performance for search of type " + type + " and heuristic " + heur/* + " and weight " + weight*/);
		
		long heapSizeBefore = Runtime.getRuntime().totalMemory(); 
		long startMil = System.currentTimeMillis();
		try {
			gridList = new FiftyGrids();
			//System.out.println("gridlist generated");
		} catch (CoordinateException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
		long endMil = System.currentTimeMillis();
		long avgSearchTime = (endMil - startMil)/50;
		long heapSizeAfter = Runtime.getRuntime().totalMemory(); 
		
		System.out.println("Average search time is: "+avgSearchTime);
		
		int totLength = 0;
		for(int i = 0; i < gridList.gridsList.size(); i++) {
			for (int j = 0; j < gridList.gridsList.get(i).pathPoints.size(); j++) {
				totLength += gridList.gridsList.get(i).pathPoints.get(j).path.size();
			}
		}
		int avgLength = totLength / 50;
		System.out.println("Average path length is: "+ avgLength);
		
		long memCost = heapSizeAfter - heapSizeBefore;
		long avgMemCost = memCost / 50;
		System.out.println("Average memory cost is: "+ avgMemCost);
		
		int totNodesExpanded = 0;
		for(int i = 0; i < gridList.gridsList.size(); i++) {
			totNodesExpanded += gridList.gridsList.get(i).nodesExpanded;
		}
		int avgNodesExpanded = totNodesExpanded / 50;
		System.out.println("Average nodes expanded: "+avgNodesExpanded);
		
	}

}
