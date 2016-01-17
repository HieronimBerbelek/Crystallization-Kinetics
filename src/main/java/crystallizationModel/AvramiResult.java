package crystallizationModel;

import java.util.ArrayList;

import dataWrappers.CrystallizationData;
import linearRegression.LinearApprox;

public class AvramiResult implements ModelOutput {
	private ArrayList<Double> lnTime = new ArrayList<Double>();
	private ArrayList<Double> ys = new ArrayList<Double>();
	private Double coefficient;
	private Double exponent;
	private Double certainity;
	String identity;
	public AvramiResult(
			ArrayList<Double> lnTime, 
			ArrayList<Double> ys, 
			Double coefficient,
			Double exponent,
			Double certainity,
			String identity) {
		this.lnTime = lnTime;
		this.ys = ys;
		this.coefficient = coefficient;
		this.exponent = exponent;
		this.certainity = certainity;
		this.identity = identity;
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
	public String basicOutput() {
		return identity+"\t"+coefficient+"\t"+exponent+"\t"+certainity+"\n";
	}
	public String extendedOutput() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
