package dataWrappers;

import java.util.ArrayList;

public class DataCrystal {
	private ArrayList<Double> relativeX;
	private ArrayList<Double> relativeTime;
	private ArrayList<Double> temperature;
	
	private String identity;
	private String userComments;
	
	public DataCrystal(ArrayList<DataTuple> data, String id){
		identity = id;
		calculateCrystallizationData(data);
	}
	private void calculateCrystallizationData(ArrayList<DataTuple> data){
		
	}
	
	public void putComment(String input){
		userComments = input;
	}
}
