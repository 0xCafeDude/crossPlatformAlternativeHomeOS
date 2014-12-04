package edu.utah.cs6964.exceptions;

public class ModuleNotStartedException extends Exception {
	public ModuleNotStartedException(String moduleName) {
		super(moduleName+" has not been started.");
	}
}
