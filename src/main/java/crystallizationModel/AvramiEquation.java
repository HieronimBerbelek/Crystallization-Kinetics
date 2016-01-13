package crystallizationModel;

import java.util.ArrayList;

import dataWrappers.CrystExpData;
import linearRegression.LeastSquaresApprox;
import linearRegression.LinearApprox;

public class AvramiEquation {
	private CrystExpData data;
	private LinearApprox approximation;
	private double coefficient;
	private double exponent;
	
	public AvramiEquation(){
		setDefaultApprox();
	}
	public AvramiEquation(CrystExpData input){
		putData(input);
		setDefaultApprox();
	}
	public AvramiEquation(CrystExpData input, LinearApprox approx){
		putData(input);
		putLinearApprox(approx);
	}
	public void putData(CrystExpData input){
		data=input;
	}
	public void putLinearApprox(LinearApprox approx){
		approximation = approx;
	}
	public void setDefaultApprox(){
		approximation = new LeastSquaresApprox();
	}
	
	private void calculate(){
		ArrayList<Double> lnTime = new ArrayList<Double>();
		ArrayList<Double> Ys = new ArrayList<Double>();
		
		for(int index = 0; index<data.getSize();index++){
			lnTime.add(Math.log(data.getRelativeTime().get(index)));
			Ys.add(Math.log10(-Math.log(1-data.getRelativeX().get(index))));
		}
	}
}
