package crystallizationModel;

import java.util.ArrayList;
import java.util.HashMap;

public class OzawaResults implements ModelOutput {
	private HashMap<Integer, ArrayList<Double>> plot; //multiple Ys
	private ArrayList<Double> xs; //log10(coolingRate) list
	private ArrayList<Double> exponents;
	private double avgExponent=0;
	private ArrayList<Double> coefficients;
	private double avgCoefficient=0;
	private ArrayList<Double> certainities;
	private double avgCertainity=0;
	private String identity;
	
	public OzawaResults(
			HashMap<Integer, ArrayList<Double>> plot, 
			ArrayList<Double> xs, 
			ArrayList<Double> exponents,
			double avgExponent, 
			ArrayList<Double> coefficients, 
			double avgCoefficient, 
			ArrayList<Double> certainities,
			double avgCertainity,
			String identity) {
		this.plot = plot;
		this.xs = xs;
		this.exponents = exponents;
		this.avgExponent = avgExponent;
		this.coefficients = coefficients;
		this.avgCoefficient = avgCoefficient;
		this.certainities = certainities;
		this.avgCertainity = avgCertainity;
		this.identity = identity;
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

	public String getIdentity() {
		return identity;
	}

	public String basicOutput() {
		return null;
	}

	public String extendedOutput() {
		StringBuilder builder = new StringBuilder();
		builder.append(identity+"\n");
		for(Integer temp : plot.keySet()){
			builder.append(temp+"\t\t\n");
			for(int index=0, index2=0;index<xs.size();index++){
				builder.append("\t"+xs.get(index)+"\t"
						+plot.get(temp).get(index2)+"\n");
				if(index2==plot.get(temp).size()-1) index2=0;
				else index2++;
			}
		}
		return builder.toString();
	}
	
}
