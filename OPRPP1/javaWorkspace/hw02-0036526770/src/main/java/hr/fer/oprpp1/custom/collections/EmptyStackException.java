package hr.fer.oprpp1.custom.collections;

public class EmptyStackException extends RuntimeException{
	public EmptyStackException (String e) {
		super();
		System.err.println(e);
	}
}
