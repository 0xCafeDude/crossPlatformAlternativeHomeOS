package edu.utah.cs6964.exceptions;

public class ModuleException extends Exception {
	public ModuleException(String moduleName) {
		super(moduleName+" caused an exception.");
	}
}
