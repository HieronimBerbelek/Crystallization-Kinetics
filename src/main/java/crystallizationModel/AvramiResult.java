package crystallizationModel;

import java.util.ArrayList;

import dataWrappers.CrystallizationData;
import linearRegression.LinearApprox;

public class AvramiResult {
	private ArrayList<Double> lnTime = new ArrayList<Double>();
	private ArrayList<Double> ys = new ArrayList<Double>();
	private Double coefficient;
	private Double exponent;
	private Double certainity;
	public AvramiResult(
			ArrayList<Double> lnTime, 
			ArrayList<Double> ys, 
			Double coefficient,
			Double exponent,
			Double certainity) {
		this.lnTime = lnTime;
		this.ys = ys;
		this.coefficient = coefficient;
		this.exponent = exponent;
		this.certainity = certainity;
	}
	public ArrayList<Double> getLnTime() {
		return lnTime;
	}
	public ArrayList<Double> getAvramiY() {
		return ys;
	}
	public Double getCoefficient() {
		return coefficient;
	}
	public Double getExponent() {
		return exponent;
	}
	public Double getCertainity() {
		return certainity;
	}
	
}
