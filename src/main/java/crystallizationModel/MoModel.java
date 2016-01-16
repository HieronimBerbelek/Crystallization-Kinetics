package crystallizationModel;

import java.util.ArrayList;
import java.util.HashMap;

import dataWrappers.CrystallizationData;
import exceptions.DataSizeException;
import linearRegression.LeastSquaresApprox;
import linearRegression.LinearApprox;

public class MoModel extends CrystallizationModel {
	private final int deltaConversionTen = 10;
	private final int deltaConversionFive = 5;
	
	private ArrayList<CrystallizationData> data;
	private LinearApprox approximation;
	private HashMap<Double, ArrayList<Double>> plot; //multiple Ys
	private ArrayList<Double> Ys; //log10(coolingRate) list
	
	private ArrayList<Double> coefficientsB;
	private double avgCoeffB=0;
	private ArrayList<Double> coefficientsFT;
	private double avgCoeffFT=0;
	private ArrayList<Double> certainities;
	private double avgCertainity=0;
	public MoModel(ArrayList<CrystallizationData> data){
		this.data = data;
		setDefaultApprox();
	}
	public MoModel(CrystallizationData data){
		this.data = new ArrayList<CrystallizationData>();
		this.data.add(data);
		setDefaultApprox();
	}
	public MoModel(ArrayList<CrystallizationData> data, LinearApprox approx){
		this.data = data;
		approximation = approx;
	}
	public MoModel(CrystallizationData data, LinearApprox approx){
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
		initYs();
		initPlot(createSeriesList());
		initLinearity();
	}
	private void initLinearity() throws DataSizeException {
		coefficientsB = new ArrayList<Double>();
		coefficientsFT = new ArrayList<Double>();
		certainities = new ArrayList<Double>();
		
		for(Double i: plot.keySet()){
			approximation.calculate(plot.get(i), Ys);
			coefficientsB.add(-approximation.getSlope());
			avgCoeffB += -approximation.getSlope();
			coefficientsFT.add(Math.pow(Math.E, approximation.getIntercept()));
			avgCoeffFT += Math.pow(Math.E, approximation.getIntercept());
			certainities.add(approximation.getCertainity());
			avgCertainity += approximation.getCertainity();
		}
		avgCoeffB /= coefficientsB.size();
		avgCoeffFT /= coefficientsFT.size();
		avgCertainity /= certainities.size();
	}
	private void initYs(){
		Ys = new ArrayList<Double>();
		for(int index=0;index<data.size();index++){
			Ys.add(Math.log10(data.get(index).getCoolingRate()));
		}
	}
	public ArrayList<Double> createSeriesList(){
		ArrayList<Double> series = new ArrayList<Double>();
		int delta;
		int percentLowerLimit = (int)(super.getLowerLimit()*100);
		int percentUpperLimit = (int)(super.getUpperLimit()*100);
		if(percentUpperLimit-percentLowerLimit>50)
			delta = deltaConversionTen;
		else delta = deltaConversionFive;
		
		for(int i=percentLowerLimit;i<=percentUpperLimit;i++){
			if(i%delta==0){
				series.add((double)i/100);
			}
		}
		return series;
	}
	private void initPlot(ArrayList<Double> series){
		plot = new HashMap<Double, ArrayList<Double>>();
		for(int index=0;index<series.size();index++){	//iterate through plot series
			plot.put(series.get(index), new ArrayList<Double>());
			for(int index2 = 0;index2<data.size();index2++){	//iterate through data series
				//iterate through single serie
				for(int index3=0;index3<data.get(index2).getSize();index3++){
					if(data.get(index2).getRelativeX().get(index3)>series.get(index)){
						double input = approximation.interpole(series.get(index), 
								data.get(index2).getRelativeX().get(index3), 
								data.get(index2).getRelativeTime().get(index3), 
								data.get(index2).getRelativeX().get(index3-1), 
								data.get(index2).getRelativeTime().get(index3-1));
						plot.get(series.get(index)).add(Math.log(input));
						break;
					}
				}
			}
		}
		/*okay, here is note for future myself:
		 * this method iterates through RelativeCrystallinity serie
		 *  because each RelativeX value is separate data serie, 
		 *  its x values are mutual log10(coolingRate)
		 * plot values are lists of relativeX at these temperatures
		 * which are y of this function
		 * interpole is necessary to find value at exact temperature
		 * then it is put to list in plot for temperature
		**/
	}
	public ArrayList<Double> getYs(){
		return Ys;
	}
	public void printPlot(){
		System.out.println(plot);
	}
	public ArrayList<Double> getCoefficientsB() {
		return coefficientsB;
	}
	public double getAvgCoeffB() {
		return avgCoeffB;
	}
	public ArrayList<Double> getCoefficientsFT() {
		return coefficientsFT;
	}
	public double getAvgCoeffFT() {
		return avgCoeffFT;
	}
	public ArrayList<Double> getCertainities() {
		return certainities;
	}
	public double getAvgCertainity() {
		return avgCertainity;
	}
}
