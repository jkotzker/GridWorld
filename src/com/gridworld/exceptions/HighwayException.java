package com.gridworld.exceptions;

/**
 * HighwayException Class
 * @author josephkotzker
 *
 */
public class HighwayException extends Exception {

	private static final long serialVersionUID = 1L;
	
	String message = "";
	
	public HighwayException(String message){
		this.message = message;
	}
	
	@Override
	public String getMessage(){
		return this.message;
	}

}
