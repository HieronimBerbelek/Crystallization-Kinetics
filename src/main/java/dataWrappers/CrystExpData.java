package dataWrappers;

import java.util.ArrayList;

public class CrystExpData implements CrystallizationData {
	//data lists
	private ArrayList<Double> relativeX = new ArrayList<Double>();
	private ArrayList<Double> relativeTime = new ArrayList<Double>();
	private ArrayList<Double> temperature = new ArrayList<Double>();
	private ArrayList<Double> crystallizationHeat = new ArrayList<Double>();
	//data for crystallization serie
	private double coolingRate;
	private CrystallizationMode mode;
	private String identity;
	private String userComments;
	private double summaricHeat;
	private double peakT; //temperature of crystallization peak
	private double peakHeat;
	private int size;
	
	public CrystExpData(){
		peakHeat = Double.NEGATIVE_INFINITY;
		summaricHeat=0;
	}
	public CrystExpData(ArrayList<DataTuple> data){
		peakHeat = Double.NEGATIVE_INFINITY;
		size = data.size();
		summaricHeat=0; //crystallization heat t0->t=inf
		injectData(data);
	}
	public CrystExpData(ArrayList<DataTuple> data, String id){
		peakHeat = Double.NEGATIVE_INFINITY;
		identity = id;
		size = data.size();
		summaricHeat=0; //crystallization heat t0->t=inf
		injectData(data);
	}
	
	public void injectData(ArrayList<DataTuple> data){
		//useful numbers for start
		double startTime = data.get(0).getTime();
		double finalTime = data.get(data.size()-1).getTime();
		double startDsc = data.get(0).getDsc();
		double finalDsc = data.get(data.size()-1).getDsc();	
		//baseline slope is used to calculate crystallization heat, 
		//with exclusion of heat capacity of sample
		double baselineSlope = 
				(finalDsc-startDsc)/(finalTime-startTime); 
		
		calculateTimeAndTemp(startTime, data);
		calculateCoolingRate();

		for(int i = 0; i < data.size();i++){
			crystallizationHeat
			.add(data.get(i).getDsc()-(startDsc+(baselineSlope*relativeTime.get(i))));
			
			summaricHeat += crystallizationHeat.get(i);
		}
		calculatePeakTemperature();
		//calculating relative crystallinity
		for(int i = 0; i < data.size();i++){
			double heatSum=crystallizationHeat.get(i);
			for(int j=0; j< i; j++){
				heatSum+=crystallizationHeat.get(j);
			}
			relativeX.add(heatSum/summaricHeat);
		}
	}
	
	private void calculateTimeAndTemp(double startTime, ArrayList<DataTuple> data){
		for(int i = 0; i < data.size(); i++){
			relativeTime.add(data.get(i).getTime()-startTime);
			temperature.add(data.get(i).getTemperature());
		}
	}
	private void calculateCoolingRate(){
		coolingRate = (temperature.get(temperature.size()-1)-temperature.get(0))
				/-relativeTime.get(relativeTime.size()-1);
		if(coolingRate>1) mode=CrystallizationMode.NONISOTHERMAL;
		else mode=CrystallizationMode.ISOTHERMAL;
	}
	private void calculatePeakTemperature(){ 
		for(int i = 0; i < size;i++){
			if (mode == CrystallizationMode.NONISOTHERMAL) {
				if (crystallizationHeat.get(i) > peakHeat){
					peakHeat = crystallizationHeat.get(i);
					peakT = temperature.get(i);
				}
			}
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
		if(Double.isFinite(peakT)) return peakT;
		else{
			calculatePeakTemperature();
			return peakT;
		}
	}
	public CrystallizationMode getMode(){
		return mode;
	}
	public int getSize(){
		return size;
	}
}
