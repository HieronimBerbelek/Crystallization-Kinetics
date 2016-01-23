package controller;

import view.GuiListener;
import view.View;

import java.io.File;
import java.io.IOException;

import dataLoader.DataLoader;
import exceptions.DscDataException;
import inputProvider.ProteusFileOpener;
import model.DataModel;

public class Controller implements GuiListener {
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
		try {
			File file = view.showFileChooser();
			if (file == null) return;
			opener = new ProteusFileOpener(file);
			dataLoader = new DataLoader(opener);
			dataLoader.loadData();
			if(model.contains(dataLoader.getDataObj()))
				view.showAlreadyLoadedMessage();
			else model.add(dataLoader.getDataObj());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			view.showIOExceptionMessage();
		} catch (DscDataException e) {
			// TODO Auto-generated catch block
			view.showDscExceptionMessage();
		}
		
	}
	public void removePerformed() {
		// TODO Auto-generated method stub
		
	}
	public void proceedPerformed() {
		// TODO Auto-generated method stub
		
	}
}
