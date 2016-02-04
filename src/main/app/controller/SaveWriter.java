package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.DataModel;
//implements runable to keep GUI responsive
public class SaveWriter implements Runnable {
	private File toSave;
	private IoListener listener;
	private DataModel model;
	public SaveWriter(DataModel model, File file, IoListener listener){
		toSave = file;
		this.listener=listener;
		this.model = model;
	}
	public void save(){
		Thread t = new Thread(this);
		t.start();
	}
	public void run() {
		ObjectOutputStream stream = null;
		try {
			stream = new ObjectOutputStream(new FileOutputStream(toSave));
			stream.writeObject(model);
			listener.saveCompleted();
		} catch (FileNotFoundException e) {
			listener.catchFileExc();
			e.printStackTrace();
		} catch (IOException e) {
			listener.catchFileExc();
			e.printStackTrace();
		} finally{
			if(stream != null)
				try {
					stream.close();
				} catch (IOException e) {
					listener.catchFileExc();
				}
		}

	}

}
