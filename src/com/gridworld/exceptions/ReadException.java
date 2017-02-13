package com.gridworld.exceptions;

/**
 * ReadExceptopn Class
 * @author josephkotzker
 *
 */
public class ReadException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	String message = "";
	
	public ReadException(String message){
		this.message = message;
	}
	
	@Override
	public String getMessage(){
		return this.message;
	}

}