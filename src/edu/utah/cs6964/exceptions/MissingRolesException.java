package edu.utah.cs6964.exceptions;

public class MissingRolesException extends Exception {
	public MissingRolesException (String name) {
		System.out.println(name + " is missing required roles");
	}
}
