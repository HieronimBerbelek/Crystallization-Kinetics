package crystallizationModel;

import java.util.ArrayList;

import dataWrappers.CrystallizationData;
import exceptions.ConversionLimitException;
import exceptions.DataSizeException;
import exceptions.NoDataException;
import linearRegression.LeastSquaresApprox;
import linearRegression.LinearApprox;

public class AvramiModel extends CrystallizationModel {
	//conversion limits, essential for good data fit
	private ArrayList<Double> lnTime = new ArrayList<Double>();
	private ArrayList<Double> Ys = new ArrayList<Double>();
	private CrystallizationData data;
	private LinearApprox approximation;
	private Double coefficient;
	private Double exponent;
	private Double certainity;

	public AvramiModel(CrystallizationData input){
		putData(input);
		setDefaultApprox();
	}
	public AvramiModel(CrystallizationData input, LinearApprox approx){
		putData(input);
		putLinearApprox(approx);
	}
	public void putData(CrystallizationData input){
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
		//Avrami plot consist of points with limited conversion to crystalline phase
		//limits come from super class CrystallizationModel
		for(int index = 0; index<data.size();index++){
			if (super.isInBounds(data.getRelativeX().get(index))) {
				toLogTime = (Math.log10(data.getRelativeTime().get(index)));
				toYs = (Math.log10(-1 * Math.log(1 - data.getRelativeX().get(index))));
				//lower and upper limit should get nonfinite values, but for certainity:
				if (Double.isFinite(toLogTime) && Double.isFinite(toYs)) {
					lnTime.add(toLogTime);
					Ys.add(toYs);
				} 
			}
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
