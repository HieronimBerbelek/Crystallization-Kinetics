package crystallization_model;

import java.util.ArrayList;
import java.util.HashMap;

public class MoResults implements ModelOutput {
	static final String MODEL_NAME = "MO";
	private HashMap<Double, ArrayList<Double>> plot; //multiple Xs
	private ArrayList<Double> Ys; //log10(coolingRate) list
	
	String identity;
	private ArrayList<Double> coefficientsB;
	private double avgCoeffB=0;
	private ArrayList<Double> coefficientsFT;
	private double avgCoeffFT=0;
	private ArrayList<Double> certainities;
	private double avgCertainity=0;
	
	
	public MoResults(
			HashMap<Double, ArrayList<Double>> plot, 
			ArrayList<Double> ys, 
			ArrayList<Double> coefficientsB,
			double avgCoeffB, 
			ArrayList<Double> coefficientsFT, 
			double avgCoeffFT, 
			ArrayList<Double> certainities,
			double avgCertainity,
			String identity) {
		this.plot = plot;
		Ys = ys;
		this.coefficientsB = coefficientsB;
		this.avgCoeffB = avgCoeffB;
		this.coefficientsFT = coefficientsFT;
		this.avgCoeffFT = avgCoeffFT;
		this.certainities = certainities;
		this.avgCertainity = avgCertainity;
		this.identity = identity;
	}
	public String getIdentity() {
		return identity;
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
	public String basicOutput() {
		StringBuilder builder = new StringBuilder();
		builder.append(identity+"\n");
		builder.append("conversion \t coefficientB \t coefficientFT \t certainity \n");
		int index=0;
		for(double conv : plot.keySet()){
			builder.append(conv+"\t"+coefficientsB.get(index)+"\t"
					+coefficientsFT.get(index)+"\t"+certainities.get(index)+"\n");
			index++;
		}
		builder.append("\nAverages:\n\t"+avgCoeffB+"\t"+avgCoeffFT
				+"\t"+avgCertainity+"\n\n");
		return builder.toString();
	}
	public String extendedOutput() {
		StringBuilder builder = new StringBuilder();
		builder.append(identity+"\n");
		builder.append("conversion \t x[ln(t)] \t y[ln[CoolingRate] \n");
		for(double conv : plot.keySet()){
			builder.append(conv+"\t\t\n");
			for(int index=0, index2=0;index<Ys.size();index++){
				builder.append("\t"+plot.get(conv).get(index)
						+"\t"+Ys.get(index2)+"\n");
				if(index2==plot.get(conv).size()-1) index2=0;
				else index2++;
			}
		}
		return builder.toString();
	}
	public String getModelName() {
		return MODEL_NAME;
	}
}
