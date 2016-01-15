package crystallizationModel;


import java.util.ArrayList;
import java.util.HashMap;

import dataWrappers.CrystallizationData;
import exceptions.DataSizeException;
import linearRegression.LeastSquaresApprox;
import linearRegression.LinearApprox;

public class OzawaModel extends CrystallizationModel {
	private final static int two = 2;
	private final static int three = 3;
	private final static int five = 5;
	private final static int ten = 10;
	private ArrayList<CrystallizationData> data;
	private LinearApprox approximation;
	private HashMap<Double, ArrayList<Double>> plot;
	private ArrayList<Double> Xs; //log10(coolingRate) list
	
	double lowerTempLimit;
	double upperTempLimit;
	
	public OzawaModel(ArrayList<CrystallizationData> data){
		this.data = data;
		setDefaultApprox();
	}
	public OzawaModel(CrystallizationData data){
		this.data = new ArrayList<CrystallizationData>();
		this.data.add(data);
		setDefaultApprox();
	}
	public OzawaModel(ArrayList<CrystallizationData> data, LinearApprox approx){
		this.data = data;
		approximation = approx;
	}
	public OzawaModel(CrystallizationData data, LinearApprox approx){
		this.data = new ArrayList<CrystallizationData>();
		this.data.add(data);
		approximation = approx;
	}
	
	public void putLinearApprox(LinearApprox approx){
		approximation = approx;
	}
	public void putData(CrystallizationData data){
		this.data.add(data);
	}
	public void setDefaultApprox(){
		approximation = new LeastSquaresApprox();
	}

	public void calculate() throws DataSizeException{
		initXs();
		initTempLimits();
		initPlot(createTemperaturesList());
	}
	private void initTempLimits(){
		upperTempLimit = Double.NEGATIVE_INFINITY;
		lowerTempLimit = Double.POSITIVE_INFINITY;
		
		//iterating through all data series
		for(int index=0; index<data.size();index++){
			
			//one iterator for each data serie
			int index2=0;
			//iterate through data serie, upperTempLimit first because 
			//temperatures are decreasing with time
			for(;index2<data.get(index).getSize();index2++){
				if(super.isInBounds(data.get(index).getRelativeX().get(index2))
						&&data.get(index).getTemperature().get(index2)>upperTempLimit){
					upperTempLimit = data.get(index).getTemperature().get(index2);
					break;
				}
			}
			for(;index2<data.get(index).getSize();index2++){
				if(super.isAboveUpperLimit(data.get(index).getRelativeX().get(index2))
						&&data.get(index).getTemperature().get(index2)<lowerTempLimit){
					lowerTempLimit = data.get(index).getTemperature().get(index2);
					break;
				}
			}
		}
	}
	private void initXs(){
		Xs = new ArrayList<Double>();
		for(int index=0;index<data.size();index++){
			Xs.add(Math.log10(data.get(index).getCoolingRate()));
		}
	}
	private ArrayList<Integer> createTemperaturesList(){
		ArrayList<Integer> temperatures = new ArrayList<Integer>();
		double range = upperTempLimit - lowerTempLimit;
		
		return temperatures;
	}
	private void initPlot(ArrayList<Integer> temperatures){
		plot = new HashMap<Double, ArrayList<Double>>();
	}
	public double getLowerTempLimit(){
		return lowerTempLimit;
	}
	public double getUpperTempLimit(){
		return upperTempLimit;
	}
}
