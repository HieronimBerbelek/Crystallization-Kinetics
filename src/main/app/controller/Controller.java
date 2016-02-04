package controller;

import view.SelectionDialog;
import view.GuiException;
import view.GuiListener;
import view.View;
import wrappers.CrystallizationData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import crystallization.AvramiModel;
import crystallization.EnergyEq;
import crystallization.LinearityModel;
import crystallization.MoModel;
import crystallization.NucleationActivity;
import crystallization.OzawaModel;
import crystallization.results.ModelOutput;
import crystallization.results.Results;
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
					view.iOExceptionMessage();
				} catch (DscDataException e) {
					view.dscExceptionMessage();
				}
				
				if(model.contains(dataLoader.getDataObj())){
					int answear = view.alreadyLoadedMessage();
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
		if(view.areUSureMessage()==JOptionPane.YES_OPTION){
			model.clear();		
			save=null;
		}
	}
	public void savePerformed() {
		if(save==null) saveAsPerformed();
		else{
			save.save();
		}
	}
	public void saveAsPerformed() {
		File file = view.saveFileChooser();
		if (file == null) return;
		save = new SaveWriter(model, file, this);
		save.save();
	}
	public void openPerformed() {
		if(save!=null&&view.areUSureMessage()==JOptionPane.NO_OPTION) return;
		File file = view.openFileChooser();
		if (file == null) return;
		try(ObjectInputStream open = new ObjectInputStream(new FileInputStream(file))) {
			model.setData((DataModel)open.readObject());
			view.openComplete();
			save = new SaveWriter(model, file, this);
		} catch (IOException e) {
			view.iOExceptionMessage();
		} catch (ClassNotFoundException e) {
			view.iOExceptionMessage();
			e.printStackTrace();
		}
	}
	public void exitPerformed() {
		System.exit(0);		
	}
	public void catchFileExc() {
		view.iOExceptionMessage();
		save = null;
	}
	public void processCompleted() {
		view.saveComplete();		
	}
	//mono data
	public void basicPerformed() {
		try {
			Results results;
			ArrayList<ModelOutput> toBasic = new ArrayList<ModelOutput>();
			int[] selection = askForSelection(SelectionDialog.BASIC);
			for(int i =0;i<selection.length;i++){
				toBasic.add((ModelOutput)model.getElementAt(i));
			}
			results = new Results(toBasic);
			writeFile(askForDirectory(), results.getOutput());
		} catch (GuiException e) {
			//do nothign
		}
	}
	public void avramiPerformed() {
		AvramiModel avrami;
		ArrayList<CrystallizationData> toAvrami;
		try {
			toAvrami = model.getElementsAt(askForSelection(SelectionDialog.AVRAMI));
		} catch (GuiException e) {
			return;
		}
		Results results=new Results();
		
		for(int i=0;i<toAvrami.size();i++){
			avrami = new AvramiModel(toAvrami.get(i));
			try {
				results.add(avrami.calculate());				
			} catch (DataSizeException e) {
				view.dataError();
				e.printStackTrace();
			}
		}
		try {
			writeFile(askForDirectory(), results.getOutput());
		} catch (GuiException e) {
			//do nothing
		}
	}
	//list series
	public void ozawaPerformed() {
		OzawaModel ozawa;
		try {
			ozawa = new OzawaModel(model.getElementsAt(askForSelection(SelectionDialog.OZAWA)));
			saveMultiDataModel(askForDirectory(), ozawa);
		} catch (GuiException e) {
			//just do nothing
		}
	}
	public void moPerformed() {
		MoModel mo;
		try {
			mo = new MoModel(model.getElementsAt(askForSelection(SelectionDialog.MO)));
			saveMultiDataModel(askForDirectory(), mo);
		} catch (GuiException e) {
			//just do nothing
		}
		
	}
	public void energyPerformed() {
		EnergyEq energy;
		try {
			energy = new EnergyEq(model.getElementsAt(askForSelection(SelectionDialog.ENERGY)));
			saveMultiDataModel(askForDirectory(), energy);
		} catch (GuiException e) {
			//just do nothing
		}
		
	}
	//double list series
	public void nucleaPerformed() {
		NucleationActivity nuclea;
		double nucl;
		double neat;
		try{
			nuclea = new NucleationActivity(
					model.getElementsAt(askForSelection(SelectionDialog.NUCLEA_NEAT)),
					model.getElementsAt(askForSelection(SelectionDialog.NUCLEA_NUCL)));
			neat =view.askDoubleDialog(
					"NEAT", 
					"Please put neat sample melting temperature");
			nucl =view.askDoubleDialog(
					"NUCLEATED", 
					"Please put nucleated sample melting temperature");
			saveMultiDataModel(askForDirectory(),nuclea, neat, nucl);
		}catch (GuiException e) {
			//just do nothing
		} catch (DataSizeException e) {
			view.dataError();
			e.printStackTrace();
		}
	}
	private void initWriter(){
		if (writer==null) writer = new OutputWriter(this);
	}
	private void writeFile(File file, String string){
		initWriter();
		writer.addString(string);
		writer.writeFile(file);
	}
	private int[] askForSelection(int mode) throws GuiException{
		int[] selection = view.selectionDialog(mode);		
		if(selection==null||selection.length<1) throw new GuiException();
		return selection;
	}
	private File askForDirectory() throws GuiException{
		File file = view.saveFileChooser();
		if (file == null) throw new GuiException();
		return file;
	}
	private void saveMultiDataModel(File file, LinearityModel linear){
		Results results;
		try {
			results = new Results(linear.calculate());
			writeFile(file, results.getOutput());
		} catch (DataSizeException e) {
			view.dataError();
			e.printStackTrace();
		}		
	}
	private void saveMultiDataModel(File file, LinearityModel linear, double neat, double nucl){
		Results results;
		try {
			results = new Results(linear.calculate(neat, nucl));
			writeFile(file, results.getOutput());
		} catch (DataSizeException e) {
			view.dataError();
			e.printStackTrace();
		}		
	}
}
