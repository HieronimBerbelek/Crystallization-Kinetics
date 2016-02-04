package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputWriter implements Runnable {
	private String toWrite;
	private File argumentFile;
	private IoListener listener;
	
	public OutputWriter(IoListener listener){
		this.listener = listener;
		toWrite="";
	}
	public void addString(String in){
		toWrite +=in;
	}
	public void writeFile(File file){
		this.argumentFile=file;
		run();
	}
	public void run() {
		FileWriter writer = null;
		if(argumentFile.toString().endsWith(".txt"));//then do nothing;
		else argumentFile = new File(argumentFile+".txt");
		try {
			writer = new FileWriter(argumentFile, false);
			writer.write(toWrite);
		} catch (IOException e) {
			listener.catchFileExc();
			e.printStackTrace();
		} finally{
				try {
					if(writer != null){
						writer.flush();
						writer.close();
					}
					listener.processCompleted();
				} catch (IOException e) {
					listener.catchFileExc();
					e.printStackTrace();
				}
		}
		
	}
	
}
