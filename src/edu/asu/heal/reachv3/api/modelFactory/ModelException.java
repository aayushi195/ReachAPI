package edu.asu.heal.reachv3.api.modelFactory;

public class ModelException extends Exception{
	
	private static final long serialVersionUID = 7531475887284069158L;

	public ModelException() {
		super();
	}

	public ModelException(String message) {
		super(message);
	}

	public ModelException(Throwable cause) {
		super(cause);
	}

	public ModelException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
