package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import crystallization_model.results.ModelOutput;

public class OutputWriter implements Runnable {
	private String toWrite;
	private String file;
	private IoListener listener;
	
	public OutputWriter(IoListener listener){
		this.listener = listener;
		toWrite="";
	}
	public void addString(String in){
		toWrite +=in;
	}
	public void writeFile(File file){
		this.file=file+".txt";
		run();
	}
	public void run() {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			writer.write(toWrite);
		} catch (IOException e) {
			listener.catchFileExc();
			e.printStackTrace();
		} finally{
				try {
					if(writer != null) writer.close();
					listener.saveCompleted();
				} catch (IOException e) {
					listener.catchFileExc();
					e.printStackTrace();
				}
		}
		
	}
	
}
