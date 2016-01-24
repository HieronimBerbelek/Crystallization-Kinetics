package controller;

import view.DataListListener;
import view.View;

import java.io.File;
import java.io.IOException;

import exceptions.DscDataException;
import input.ProteusFileOpener;
import loader.DataLoader;
import model.DataModel;

public class Controller implements DataListListener {
	private View view;
 	private DataModel model;
 	private ProteusFileOpener opener;
 	private DataLoader dataLoader;
 	
 	public Controller(DataModel model, View view){
 		setModel(model);
 		setView(view);
 	}
 	public void setModel(DataModel model){
 		this.model = model;
 	}
 	public void setView(View view){
 		this.view = view;
 	}
	public void addPerformed() {
			File[] files = view.showFileChooser();
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
					view.showAlreadyLoadedMessage();
				}
				else{
					model.add(dataLoader.getDataObj());
				}
		
			}
	}
	public void removePerformed(int[] items) {
		if(items.length>0) model.remove(items);		
	}
	public void proceedPerformed() {
		// TODO Auto-generated method stub
		
	}
}
