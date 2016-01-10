package dataWrappers;

import java.util.ArrayList;

public class DataCrystal {
	private ArrayList<Double> relativeX = new ArrayList<Double>();
	private ArrayList<Double> relativeTime = new ArrayList<Double>();
	private ArrayList<Double> temperature = new ArrayList<Double>();
	
	private String identity;
	private String userComments;
	
	public DataCrystal(ArrayList<DataTuple> data, String id){
		identity = id;
		calculateCrystallizationData(data);
	}
	private void calculateCrystallizationData(ArrayList<DataTuple> data){
		double startTime = data.get(0).getTime();
		double finalTime = data.get(data.size()-1).getTime();
		double startDsc = data.get(0).getDsc();
		double finalDsc = data.get(data.size()-1).getDsc();
		double sumCrystallizationH=0;
		
		ArrayList<Double> crystallizationHeat = new ArrayList<Double>();
		double baselineSlope = 
				(finalDsc-startDsc)/(finalTime-startTime);
		
		for(int i = 0; i < data.size(); i++){
			relativeTime.add(data.get(i).getTime()-startTime);
			temperature.add(data.get(i).getTemperature());
			
			crystallizationHeat
			.add(data.get(i).getDsc()-(startDsc+(baselineSlope*relativeTime.get(i))));
			sumCrystallizationH += crystallizationHeat.get(i);
		}
		//relative crystallization is not calculated for first and last 2 points(edges)
		for(int i = 1; i < data.size()-2;i++){
			double heatSum=0;
			for(int j=0; j< i; j++){
				heatSum+=crystallizationHeat.get(j);
			}
			relativeX.add(heatSum/sumCrystallizationH);
		}
	}
	
	public void putComment(String input){
		userComments = input;
	}
}
