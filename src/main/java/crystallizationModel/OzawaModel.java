package crystallizationModel;


import java.util.ArrayList;
import java.util.HashMap;

import dataWrappers.CrystallizationData;
import exceptions.DataSizeException;
import exceptions.OzawaModelRangeException;
import linearRegression.LeastSquaresApprox;
import linearRegression.LinearApprox;

public class OzawaModel extends CrystallizationModel {
	private final static int ONE = 1;
	private final static int TWO = 2;
	private final static int THREE = 3;
	private final static int FIVE = 5;
	private final static int EIGHT = 8;
	private final static int TEN = 10;
	
	private int minNumOfLines = 5;
	
	private ArrayList<CrystallizationData> data;
	private LinearApprox approximation;
	private HashMap<Integer, ArrayList<Double>> plot; //multiple Ys
	private ArrayList<Double> Xs; //log10(coolingRate) list
	private ArrayList<Double> exponents;
	private double avgExponent=0;
	private ArrayList<Double> coefficients;
	private double avgCoefficient=0;
	private ArrayList<Double> certainities;
	private double avgCertainity=0;
	private String identity;
	
	private double lowerTempLimit;
	private double upperTempLimit;
	
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

	public OzawaResults calculate() throws DataSizeException{
		initXs();
		initTempLimits();
		initPlot(createSeriesList());
		initLinearity();
		initIdentity();
		return new OzawaResults(
			plot, 
			Xs, 
			exponents,
			avgExponent, 
			coefficients, 
			avgCoefficient, 
			certainities,
			avgCertainity,
			identity);
	}
	private void initIdentity() {
		int endIndex = data.get(0).getIdentity().lastIndexOf(" ");
		identity=data.get(0).getIdentity().substring(0, endIndex);
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
			for(;index2<data.get(index).size();index2++){
				if(super.isInBounds(data.get(index).getRelativeX().get(index2))
						&&data.get(index).getTemperature().get(index2)>upperTempLimit){
					upperTempLimit = data.get(index).getTemperature().get(index2);
					break;
				}
			}
			for(;index2<data.get(index).size();index2++){
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
	private ArrayList<Integer> createSeriesList(){
		ArrayList<Integer> temperatures = new ArrayList<Integer>();
		double range = upperTempLimit - lowerTempLimit;
		
		int startT=0;
		int deltaT=0;
		if((range/TEN)>minNumOfLines) deltaT=TEN;
		else if ((range/EIGHT)>minNumOfLines) deltaT=EIGHT;
		else if ((range/FIVE)>minNumOfLines) deltaT=FIVE;
		else if ((range/THREE)>minNumOfLines) deltaT=THREE;
		else if ((range/TWO)>minNumOfLines) deltaT=TWO;
		else if ((range/ONE)>minNumOfLines) deltaT=ONE;
		else throw new OzawaModelRangeException();
		
		for(int index = (int)lowerTempLimit+1;index<upperTempLimit;index++){
			if((index%2)==0){
				startT=index;
				break;
			}
		}
		for(int index = startT;index<upperTempLimit;index+=deltaT){
			temperatures.add(index);
		}
		return temperatures;
	}
	private void initPlot(ArrayList<Integer> temperatures){
		plot = new HashMap<Integer, ArrayList<Double>>();
		for(int index=0;index<temperatures.size();index++){ //iterate through temperatures
			plot.put(temperatures.get(index), new ArrayList<Double>());
			for(int index2=0;index2 < data.size();index2++){  //iterate through data series
				for(int index3=0;index3 < data.get(index2).size();index3++){ 
					//iterate through single serie
					if(data.get(index2).getTemperature().get(index3)<temperatures.get(index)){
						double input;
						if (index3>0) {
							input = approximation.interpole(temperatures.get(index),
									data.get(index2).getTemperature().get(index3 - 1),
									data.get(index2).getRelativeX().get(index3 - 1),
									data.get(index2).getTemperature().get(index3),
									data.get(index2).getRelativeX().get(index3));
						}
						else input = data.get(index2).getRelativeX().get(index3);
						input = (Math.log10(-1 * Math.log(input)));
						plot.get(temperatures.get(index)).add(input);
						break;
						}
				}
			}
		}
		/*okay, here is note for future myself:
		 * this method iterates through temperatures, because each temperature is
		 * separate data serie, its x values are mutual log10(coolingRate)
		 * plot values are lists of relativeX at these temperatures
		 * which are y of this function
		 * interpole is necessary to find value at exact temperature
		 * then it is put to list in plot for temperature
		**/
	}
	private void initLinearity() throws DataSizeException{
		exponents = new ArrayList<Double>();
		coefficients = new ArrayList<Double>();
		certainities = new ArrayList<Double>();
		for(Integer i : plot.keySet()){
			approximation.calculate(Xs, plot.get(i));

			exponents.add(approximation.getSlope());
			avgExponent += approximation.getSlope();
			coefficients.add(Math.pow(10, approximation.getIntercept()));
			avgCoefficient +=Math.pow(10, approximation.getIntercept());
			certainities.add(approximation.getCertainity());
			avgCertainity += approximation.getCertainity();
		}
		avgExponent /= exponents.size();
		avgCoefficient /= coefficients.size();
		avgCertainity /= certainities.size();
	}
	
	public double getLowerTempLimit(){
		return lowerTempLimit;
	}
	public double getUpperTempLimit(){
		return upperTempLimit;
	}
	public void setMinNumOfLines(int d){
		if(d<upperTempLimit-lowerTempLimit) minNumOfLines=d;
		else throw new OzawaModelRangeException();
	}
}
