package crystallizationModelTest;

import java.util.ArrayList;

import dataWrappers.CrystallizationData;
import dataWrappers.CrystallizationMode;
import dataWrappers.DataTuple;

public class MockCrystData implements CrystallizationData {
	private ArrayList<Double> relativeX = new ArrayList<Double>();
	private ArrayList<Double> relativeTime = new ArrayList<Double>();
	private ArrayList<Double> temperature = new ArrayList<Double>();
	private double coolingRate;
	private CrystallizationMode mode;
	private String identity;
	private String userComments;
	private double summaricHeat;
	private double peakT; //temperature of crystallization peak
	private int size;
	
	public MockCrystData(){
		identity = "AP52DE55 Mock";
		userComments = "Mock Object";
		summaricHeat = 5.7;
		coolingRate = 10;
		size =8;
		mode = CrystallizationMode.NONISOTHERMAL;
		
		relativeX.add(0.005);
		relativeX.add(0.2);
		relativeX.add(0.5);
		relativeX.add(0.7);
		relativeX.add(0.9);
		relativeX.add(0.99);
		relativeX.add(1.0);
		relativeX.add(1.0);
		
		for(int index=0; index<size;index++){
			relativeTime.add((double)index);
			temperature.add(180-(index*coolingRate));
		}
		peakT = 160;
	}
	public void injectData(ArrayList<DataTuple> data) {
		// DO NOTHING
	}
	public String getIdentity(){
		return identity;
	}
	public void putComment(String input){
		userComments = input;
	}
	public String getComments(){
		return userComments;
	}
	public double getCoolingRate(){
		return coolingRate;
	}
	public double getSummaricHeat(){
		return summaricHeat;
	}
	public ArrayList<Double> getRelativeX(){
		return relativeX;
	}
	public ArrayList<Double> getRelativeTime(){
		return relativeTime;
	}
	public ArrayList<Double> getTemperature(){
		return temperature;
	}
	public double getPeakTemperature(){
		return peakT;
	}
	public CrystallizationMode getMode(){
		return mode;
	}
	public int getSize(){
		return size;
	}
}
