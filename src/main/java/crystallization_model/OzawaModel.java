package crystallization_model;


import java.util.ArrayList;
import java.util.HashMap;

import crystallization_model.results.OzawaResults;
import exceptions.DataSizeException;
import exceptions.OzawaModelRangeException;
import linearity.LeastSquaresApprox;
import linearity.LinearApprox;
import wrappers.CrystallizationData;

public class OzawaModel extends LimitedConversionModel {
	private final static int ONE = 1;
	private final static int TWO = 2;
	private final static int THREE = 3;
	private final static int FIVE = 5;
	private final static int EIGHT = 8;
	private final static int TEN = 10;
	
	private int minNumOfLines = 5;
	
	private ArrayList<CrystallizationData> data;
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
		super.putLinearApprox(approx);
	}
	public OzawaModel(CrystallizationData data, LinearApprox approx){
		this.data = new ArrayList<CrystallizationData>();
		this.data.add(data);
		super.putLinearApprox(approx);
	}
	public void putData(CrystallizationData data){
		this.data.add(data);
	}

	public OzawaResults calculate(double...input) throws DataSizeException{
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
		for(int i=0; i<data.size();i++){
			
			//one iterator for each data serie
			int j=0;
			//iterate through data serie, upperTempLimit first because 
			//temperatures are decreasing with time
			for(;j<data.get(i).size();j++){
				if(super.isInBounds(data.get(i).getRelativeX().get(j))
						&&data.get(i).getTemperature().get(j)>upperTempLimit){
					upperTempLimit = data.get(i).getTemperature().get(j);
					break;
				}
			}
			for(;j<data.get(i).size();j++){
				if(super.isAboveUpperLimit(data.get(i).getRelativeX().get(j))
						&&data.get(i).getTemperature().get(j)<lowerTempLimit){
					lowerTempLimit = data.get(i).getTemperature().get(j);
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
		for(int i=0;i<temperatures.size();i++){ //iterate through temperatures
			plot.put(temperatures.get(i), new ArrayList<Double>());
			for(int j=0;j < data.size();j++){  //iterate through data series
				for(int k=0;k < data.get(j).size();k++){ 
					//iterate through single serie
					if(data.get(j).getTemperature().get(k)<temperatures.get(i)){
						double input;
						if (k>0) {
							input=super.approximation.interpole(temperatures.get(i),
									data.get(j).getTemperature().get(k - 1),
									data.get(j).getRelativeX().get(k - 1),
									data.get(j).getTemperature().get(k),
									data.get(j).getRelativeX().get(k));
						}
						else input = data.get(j).getRelativeX().get(k);
						input = (Math.log10(-1 * Math.log(input)));
						plot.get(temperatures.get(i)).add(input);
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

			exponents.add(super.approximation.getSlope());
			avgExponent += super.approximation.getSlope();
			coefficients.add(Math.pow(10, super.approximation.getIntercept()));
			avgCoefficient +=Math.pow(10, approximation.getIntercept());
			certainities.add(super.approximation.getCertainity());
			avgCertainity += super.approximation.getCertainity();
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
