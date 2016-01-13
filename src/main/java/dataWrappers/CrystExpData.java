package dataWrappers;

import java.util.ArrayList;

public class CrystExpData implements CrystallizationData {
	//data lists
	private ArrayList<Double> relativeX = new ArrayList<Double>();
	private ArrayList<Double> relativeTime = new ArrayList<Double>();
	private ArrayList<Double> temperature = new ArrayList<Double>();
	//data for crystallization serie
	private double coolingRate;
	private CrystallizationMode mode;
	private String identity;
	private String userComments;
	private double summaricHeat;
	private double peakT; //temperature of crystallization peak
	private int size;
	
	public CrystExpData(){
	}
	public CrystExpData(ArrayList<DataTuple> data, String id){
		identity = id;
		size = data.size();
		summaricHeat=0; //crystallization heat t0->t=inf
		putData(data);
	}
	public void putData(ArrayList<DataTuple> data){
		//heat in crystallization peak, used later
		double peakHeat = Double.NEGATIVE_INFINITY; 
		//useful numbers for start
		double startTime = data.get(0).getTime();
		double finalTime = data.get(data.size()-1).getTime();
		double startDsc = data.get(0).getDsc();
		double finalDsc = data.get(data.size()-1).getDsc();		
		ArrayList<Double> crystallizationHeat = new ArrayList<Double>();
		
		//baseline slope is used to calculate crystallization heat, 
		//with exclusion of heat capacity of sample
		double baselineSlope = 
				(finalDsc-startDsc)/(finalTime-startTime); 

		//copying time and temperature
		for(int i = 0; i < data.size(); i++){
			relativeTime.add(data.get(i).getTime()-startTime);
			temperature.add(data.get(i).getTemperature());
		}
		
		//calculating cooling rate and mode of crystallization
		coolingRate = (temperature.get(temperature.size()-1)-temperature.get(0))
				/-relativeTime.get(relativeTime.size()-1);
		if(coolingRate>1) mode=CrystallizationMode.NONISOTHERMAL;
		else mode=CrystallizationMode.ISOTHERMAL;
		
		//creating crystallization heat list, calculating sumaric heat, 
		//finding crystallization peak if nonisothermal
		for(int i = 0; i < data.size();i++){
			crystallizationHeat
			.add(data.get(i).getDsc()-(startDsc+(baselineSlope*relativeTime.get(i))));
			
			summaricHeat += crystallizationHeat.get(i);
			
			if (mode == CrystallizationMode.NONISOTHERMAL) {
				if (crystallizationHeat.get(i) > peakHeat){
					peakHeat = crystallizationHeat.get(i);
					peakT = temperature.get(i);
				}
			}
		}
		
		//calculating relative crystallinity
		for(int i = 0; i < data.size();i++){
			double heatSum=0;
			for(int j=0; j< i; j++){
				heatSum+=crystallizationHeat.get(j);
			}
			relativeX.add(heatSum/summaricHeat);
		}
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
