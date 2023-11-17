package com.taohansen.dslearn.services.exceptions;

public class MoveNotAllowedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MoveNotAllowedException(String msg) {
		super(msg);
	}
}
