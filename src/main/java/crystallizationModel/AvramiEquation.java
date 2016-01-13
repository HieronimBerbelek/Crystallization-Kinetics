package crystallizationModel;

import java.util.ArrayList;

import dataWrappers.CrystExpData;
import exceptions.DataSizeException;
import exceptions.NoDataInputException;
import linearRegression.LeastSquaresApprox;
import linearRegression.LinearApprox;

public class AvramiEquation {
	private CrystExpData data;
	private LinearApprox approximation;
	private Double coefficient;
	private Double exponent;
	private Double certainity;

	public AvramiEquation(CrystExpData input){
		putData(input);
		setDefaultApprox();
	}
	public AvramiEquation(CrystExpData input, LinearApprox approx){
		putData(input);
		putLinearApprox(approx);
	}
	private void putData(CrystExpData input){
		data=input;
	}
	public void putLinearApprox(LinearApprox approx){
		approximation = approx;
	}
	public void setDefaultApprox(){
		approximation = new LeastSquaresApprox();
	}
	
	public void calculate() throws DataSizeException{

		if(exponent == null) return; //returns method if model is already calculated
			
		ArrayList<Double> lnTime = new ArrayList<Double>();
		ArrayList<Double> Ys = new ArrayList<Double>();
		
		for(int index = 0; index<data.getSize();index++){
			lnTime.add(Math.log(data.getRelativeTime().get(index)));
			Ys.add(Math.log10(-Math.log(1-data.getRelativeX().get(index))));
		}
		
		approximation.calculate(lnTime, Ys);
		exponent = approximation.getSlope();
		coefficient = Math.pow(10, approximation.getIntercept());
		certainity = approximation.getCertainity();
	}
	public double getExponent() throws NoDataInputException{
		if(data == null) throw new NoDataInputException();
		return exponent;
	}
	public double getCoefficient() throws NoDataInputException{
		if(data == null) throw new NoDataInputException();
		return coefficient;
	}
	public double getCertainity() throws NoDataInputException{
		if(data == null) throw new NoDataInputException();
		return certainity;
	}
}
