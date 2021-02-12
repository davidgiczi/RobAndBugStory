package com.topdesk.cases.toprob.yoursolution;

public class RobCannotBeSteppedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	
	public RobCannotBeSteppedException(String message) {
		this.message = message;
	}


	public String getMessage() {
		return message;
	}
	
	
}
