package dataWrappers;

import java.util.ArrayList;
//using interface to simplify further testing of models etc.
public interface CrystallizationData { 
	
	public void injectData(ArrayList<DataTuple> data);
	public String getIdentity();
	public void putComment(String input);
	public String getComments();
	public double getCoolingRate();
	public double getSummaricHeat();
	public ArrayList<Double> getRelativeX();
	public ArrayList<Double> getRelativeTime();
	public ArrayList<Double> getTemperature();
	public double getPeakTemperature();
	public CrystallizationMode getMode();
	public int getSize();
}
