package crystallizationModel;

import java.util.ArrayList;
import java.util.HashMap;

public class OzawaResults {
	private HashMap<Integer, ArrayList<Double>> plot; //multiple Ys
	private ArrayList<Double> xs; //log10(coolingRate) list
	private ArrayList<Double> exponents;
	private double avgExponent=0;
	private ArrayList<Double> coefficients;
	private double avgCoefficient=0;
	private ArrayList<Double> certainities;
	private double avgCertainity=0;
	
	public OzawaResults(
			HashMap<Integer, ArrayList<Double>> plot, 
			ArrayList<Double> xs, 
			ArrayList<Double> exponents,
			double avgExponent, 
			ArrayList<Double> coefficients, 
			double avgCoefficient, 
			ArrayList<Double> certainities,
			double avgCertainity) {
		this.plot = plot;
		this.xs = xs;
		this.exponents = exponents;
		this.avgExponent = avgExponent;
		this.coefficients = coefficients;
		this.avgCoefficient = avgCoefficient;
		this.certainities = certainities;
		this.avgCertainity = avgCertainity;
	}

	public HashMap<Integer, ArrayList<Double>> getPlot() {
		return plot;
	}

	public ArrayList<Double> getXs() {
		return xs;
	}

	public ArrayList<Double> getExponents() {
		return exponents;
	}

	public double getAvgExponent() {
		return avgExponent;
	}

	public ArrayList<Double> getCoefficients() {
		return coefficients;
	}

	public double getAvgCoefficient() {
		return avgCoefficient;
	}

	public ArrayList<Double> getCertainities() {
		return certainities;
	}

	public double getAvgCertainity() {
		return avgCertainity;
	}
	
}
