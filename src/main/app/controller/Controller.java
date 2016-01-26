package controller;

import view.GuiListener;
import view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

import exceptions.DscDataException;
import input.ProteusFileOpener;
import loader.DataLoader;
import model.DataModel;

public class Controller implements GuiListener, IoListener {
	private View view;
 	private DataModel model;
 	
 	private ProteusFileOpener opener;
 	private DataLoader dataLoader;
 	private Save save;
 	
 	public Controller(DataModel model, View view){
 		setModel(model);
 		setView(view);
 		save=null;
 	}
 	public void setModel(DataModel model){
 		this.model = model;
 	}
 	public void setView(View view){
 		this.view = view;
 	}
	public void addPerformed() {
			File[] files = view.showAddFileChooser();
			if (files == null) return;
			for(int index=0;index<files.length;index++){
				try {
				opener = new ProteusFileOpener(files[index]);
				dataLoader = new DataLoader(opener);
				dataLoader.loadData();
				} catch (IOException e) {
					view.showIOExceptionMessage();
				} catch (DscDataException e) {
					view.showDscExceptionMessage();
				}
				
				if(model.contains(dataLoader.getDataObj())){
					int answear = view.showAlreadyLoadedMessage();
					if(answear==JOptionPane.YES_OPTION) 
						model.overwrite(dataLoader.getDataObj());
					else if(answear==JOptionPane.CANCEL_OPTION)
						break;//breaks adding the files if cancel!
					//just continue if no!
				}
				else{
					model.add(dataLoader.getDataObj());
				}
		
			}
	}
	public void removePerformed(int[] items) {
		if(items.length>0) model.remove(items);		
	}
	public void newPerformed() {
		if(view.showAreUSureMessage()==JOptionPane.YES_OPTION){
			model.clear();		
			save=null;
		}
	}
	public void savePerformed() {
		if(save==null) saveAsPerformed();
		else{
			save.run();
		}
	}
	public void saveAsPerformed() {
		File file = view.showSaveFileChooser();
		if (file == null) return;
		save = new Save(model, file, this);
		save.run();
	}
	public void openPerformed() {
		if(save!=null&&view.showAreUSureMessage()==JOptionPane.NO_OPTION) return;
		File file = view.showOpenFileChooser();
		if (file == null) return;
		ObjectInputStream open = null;
		try {
			open = new ObjectInputStream(new FileInputStream(file));
			model.setData((DataModel)open.readObject());
			view.showOpenComplete();
			save = new Save(model, file, this);
		} catch (FileNotFoundException e) {
			view.showIOExceptionMessage();
		} catch (IOException e) {
			view.showIOExceptionMessage();
		} catch (ClassNotFoundException e) {
			view.showIOExceptionMessage();
			e.printStackTrace();
		} finally{
			try {
				if(open != null) open.close();
			} catch (IOException e) {
				view.showIOExceptionMessage();
			}
		}
	}
	public void exitPerformed() {
		System.exit(0);		
	}
	public void catchFileExc() {
		view.showIOExceptionMessage();
		save = null;
	}
	public void saveCompleted() {
		view.showSaveComplete();		
	}
}
