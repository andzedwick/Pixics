package com.pixics.main;

public class Globals {

	private static long nextId = 0L;
	
	
	public static long getNextId() {
		nextId++;
		return nextId;
	}
}
