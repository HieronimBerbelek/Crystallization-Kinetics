package controller;

import view.SelectionDialog;
import view.GuiListener;
import view.View;
import wrappers.CrystallizationData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import crystallization_model.AvramiModel;
import crystallization_model.EnergyEq;
import crystallization_model.LinearityModel;
import crystallization_model.MoModel;
import crystallization_model.OzawaModel;
import crystallization_model.results.ModelOutput;
import crystallization_model.results.Results;
import exceptions.DataSizeException;
import exceptions.DscDataException;
import input.ProteusFileOpener;
import loader.DataLoader;
import model.DataModel;

public class Controller implements GuiListener, IoListener {
	private View view;
 	private DataModel model;
 	
 	private ProteusFileOpener opener;
 	private DataLoader dataLoader;
 	private SaveWriter save;
 	private OutputWriter writer;
 	
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
		save = new SaveWriter(model, file, this);
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
			save = new SaveWriter(model, file, this);
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
	//mono data
	public void basicPerformed() {
		try {
			Results results;
			results =new Results(
					(ModelOutput) model.getElementsAt(askForSelection(SelectionDialog.BASIC)));
			writeFile(askForDirectory(), results.getOutput());
		} catch (Exception e) {
			//do nothing
		}
	}
	public void avramiPerformed() {
		AvramiModel avrami;
		ArrayList<CrystallizationData> toAvrami;
		try {
			toAvrami = model.getElementsAt(askForSelection(SelectionDialog.AVRAMI));
		} catch (Exception e1) {
			return;
		}
		Results results=new Results();
		
		for(int i=0;i<toAvrami.size();i++){
			avrami = new AvramiModel(toAvrami.get(i));
			try {
				results.add(avrami.calculate());				
			} catch (DataSizeException e) {
				view.showDataError();
				e.printStackTrace();
			}
		}
		try {
			writeFile(askForDirectory(), results.getOutput());
		} catch (Exception e) {
			//do nothing
		}
	}
	//list series
	public void ozawaPerformed() {
		OzawaModel ozawa;
		try {
			ozawa = new OzawaModel(model.getElementsAt(askForSelection(SelectionDialog.OZAWA)));
			saveMultiDataModel(askForDirectory(), ozawa);
		} catch (Exception e) {
			//just do nothing
		}
	}
	public void moPerformed() {
		MoModel mo;
		try {
			mo = new MoModel(model.getElementsAt(askForSelection(SelectionDialog.MO)));
			saveMultiDataModel(askForDirectory(), mo);
		} catch (Exception e) {
			//just do nothing
		}
		
	}
	public void energyPerformed() {
		EnergyEq energy;
		try {
			energy = new EnergyEq(model.getElementsAt(askForSelection(SelectionDialog.ENERGY)));
			saveMultiDataModel(askForDirectory(), energy);
		} catch (Exception e) {
			//just do nothing
		}
		
	}
	//double list series
	public void nucleaPerformed() {
		// TODO Auto-generated method stub
		
	}
	private void initWriter(){
		if (writer==null) writer = new OutputWriter(this);
	}
	private void writeFile(File file, String string){
		initWriter();
		writer.addString(string);
		writer.writeFile(file);
	}
	private int[] askForSelection(int mode) throws Exception{
		int[] selection = view.showSelectionDialog(mode);		
		if(selection.length<1) throw new Exception();
		return selection;
	}
	private File askForDirectory() throws Exception{
		File file = view.showSaveFileChooser();
		if (file == null) throw new Exception();
		return file;
	}
	private void saveMultiDataModel(File file, LinearityModel linear){
		Results results;
		try {
			results = new Results(linear.calculate());
			writeFile(file, results.getOutput());
		} catch (DataSizeException e) {
			view.showDataError();
			e.printStackTrace();
		}		
	}
}
