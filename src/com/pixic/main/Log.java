package com.pixic.main;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Log {

	private static PrintStream out = System.out;
	
	public synchronized static void info(String msg) {
		log("INFO", msg);
	}
	
	public synchronized static void debug(String msg) {
		log("DEBUG", msg);
	}
	
	public synchronized static void warning(String msg) {
		log("WARNING", msg);
	}
	
	public synchronized static void error(String msg) {
		log("ERROR", msg);
	}
	
	public synchronized static void error(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		
		log("ERROR", sw.toString());
	}
	
	public synchronized static void error(String msg, Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		
		log("ERROR", msg + "\n" + sw.toString());
	}
	
	private synchronized static void log(String prefix, String msg) {
		String[] splitMsg = msg.split("\n");
		StringBuilder finalMsg = new StringBuilder(((prefix.length() + 1) * splitMsg.length) + msg.length() + splitMsg.length + 10);
		if (splitMsg.length > 0) {
			finalMsg.append(prefix + ":\t" + splitMsg[0]);
			String emptyPrefix = prefix.replaceAll(".", " ") + " \t";
			for (int i = 1; i < splitMsg.length; i++) {
				finalMsg.append(emptyPrefix + splitMsg[i]);
			}
			
			out.println(finalMsg.toString());
		}
	}
}
