package crystallizationModel;

import java.util.ArrayList;
import java.util.HashMap;

public class MoResult {
	private HashMap<Double, ArrayList<Double>> plot; //multiple Ys
	private ArrayList<Double> Ys; //log10(coolingRate) list
	
	private ArrayList<Double> coefficientsB;
	private double avgCoeffB=0;
	private ArrayList<Double> coefficientsFT;
	private double avgCoeffFT=0;
	private ArrayList<Double> certainities;
	private double avgCertainity=0;
	
	
	public MoResult(
			HashMap<Double, ArrayList<Double>> plot, 
			ArrayList<Double> ys, 
			ArrayList<Double> coefficientsB,
			double avgCoeffB, 
			ArrayList<Double> coefficientsFT, 
			double avgCoeffFT, 
			ArrayList<Double> certainities,
			double avgCertainity) {
		super();
		this.plot = plot;
		Ys = ys;
		this.coefficientsB = coefficientsB;
		this.avgCoeffB = avgCoeffB;
		this.coefficientsFT = coefficientsFT;
		this.avgCoeffFT = avgCoeffFT;
		this.certainities = certainities;
		this.avgCertainity = avgCertainity;
	}
	public HashMap<Double, ArrayList<Double>> getPlot() {
		return plot;
	}
	public ArrayList<Double> getYs(){
		return Ys;
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
