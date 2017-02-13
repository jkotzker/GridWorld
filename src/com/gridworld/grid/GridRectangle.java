package com.gridworld.grid;

import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextArea;

public class GridRectangle extends Rectangle {
	
	public GridSquare square;
	public int index;
	public TextArea infoText;
	
	public GridRectangle(GridSquare sq, int index, TextArea infoText) {
		super();
		this.square = sq;
		this.index = index;
		this.infoText = infoText;
	}

}
