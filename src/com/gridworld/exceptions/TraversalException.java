package com.gridworld.exceptions;

public class TraversalException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	String message = "";
	
	public TraversalException(String message){
		this.message = message;
	}
	
	@Override
	public String getMessage(){
		return this.message;
	}

}
