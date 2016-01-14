package crystallizationModel;

import java.util.ArrayList;

import dataWrappers.CrystallizationData;
import exceptions.DataSizeException;
import exceptions.NoDataException;
import linearRegression.LeastSquaresApprox;
import linearRegression.LinearApprox;

public class AvramiEquation {
	private ArrayList<Double> lnTime = new ArrayList<Double>();
	private ArrayList<Double> Ys = new ArrayList<Double>();
	private CrystallizationData data;
	private LinearApprox approximation;
	private Double coefficient;
	private Double exponent;
	private Double certainity;

	public AvramiEquation(CrystallizationData input){
		putData(input);
		setDefaultApprox();
	}
	public AvramiEquation(CrystallizationData input, LinearApprox approx){
		putData(input);
		putLinearApprox(approx);
	}
	private void putData(CrystallizationData input){
		data=input;
	}
	public void putLinearApprox(LinearApprox approx){
		approximation = approx;
	}
	public void setDefaultApprox(){
		approximation = new LeastSquaresApprox();
	}
	
	public void calculate() throws DataSizeException{
		if(exponent != null) return; //returns method if model is already calculated
		double toLogTime;
		double toYs;
		//Avrami plot consist of all points without first(=-INF) and last 2 (=INF)
		for(int index = 0; index<data.getSize();index++){
			toLogTime = (Math.log10(data.getRelativeTime().get(index)));
			toYs = (Math.log10(-1*Math.log(1-data.getRelativeX().get(index))));
			
			if(Double.isInfinite(toLogTime)
					||Double.isInfinite(toYs)
					||Double.isNaN(toYs)
					||Double.isNaN(toLogTime)) 
				continue;
			
			lnTime.add(toLogTime);
			Ys.add(toYs);
		}
		approximation.calculate(lnTime, Ys);
		exponent = approximation.getSlope();
		coefficient = Math.pow(10, approximation.getIntercept());
		certainity = approximation.getCertainity();
	}
	public double getExponent() throws NoDataException{
		if(exponent == null) throw new NoDataException();
		return exponent;
	}
	public double getCoefficient() throws NoDataException{
		if(coefficient == null) throw new NoDataException();
		return coefficient;
	}
	public double getCertainity() throws NoDataException{
		if(certainity == null) throw new NoDataException();
		return certainity;
	}
	public ArrayList<Double> getLogTime(){
		return lnTime;
	}
	public ArrayList<Double> getAvramiY(){
		return Ys;
	}
	public String toString(){
		if(exponent == null) return "Data not calculated yet!";
		else return ("Coefficient k: "+coefficient+", Exponent n: "+exponent
				+", Certainity: "+certainity);
	}
}
