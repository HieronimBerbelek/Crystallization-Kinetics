package controller;

import view.View;

import model.DataModel;

public class Controller {
	private View view;
 	private DataModel model;
 	
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
}
