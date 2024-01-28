package com.pixics.main;

import java.io.PrintStream;

public class Logger {

	private PrintStream outStream = System.out;
	
	public void setOutStream(PrintStream os) {
		this.outStream = os;
	}
	
	public PrintStream getStream() {
		return outStream;
	}
	
	public void debug(String msg) {
		log("DEBUG", msg);
	}
	
	public void info(String msg) {
		log("INFO", msg);
	}
	
	public void warning(String msg) {
		log("WARNING", msg);
	}
	
	public void error(String msg) {
		log("ERROR", msg);
	}
	
	public void error(Exception e) {
		log("ERROR", e.getMessage());
		e.printStackTrace(outStream);
	}
	
	public void error(String msg, Exception e) {
		log("ERROR", msg + "\n" + e.getMessage());
		e.printStackTrace(outStream);
	}
	
	private void log(String prefix, String msg) {
		String[] lines = msg.split("\n");
		String emptyPrefix = prefix.replaceAll(".", " ");
		
		for (int i = 0; i < lines.length; i++) {
			if (i == 0) {
				outStream.println(prefix + ":\t" + lines[i]);
			} else {
				outStream.println(emptyPrefix + " \t" + lines[i]);
			}
		}
	}
	
}
