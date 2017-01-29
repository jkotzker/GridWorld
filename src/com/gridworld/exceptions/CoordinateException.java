package com.gridworld.exceptions;

/**
 * CoordinateException Class
 * @author josephkotzker
 *
 */
public class CoordinateException extends Exception {

	private static final long serialVersionUID = 1L;
	
	String message = "";
	
	public CoordinateException(String message){
		this.message = message;
	}
	
	@Override
	public String getMessage(){
		return this.message;
	}

}
