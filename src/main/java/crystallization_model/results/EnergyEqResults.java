package crystallization_model.results;

import java.util.ArrayList;
import java.util.Map;

public class EnergyEqResults implements ModelOutput {
	private static final String MODEL_NAME = "ENERGY BARRIER";
	private static final String BASIC_HEADER="conversion \t EnergyBarrier[kJ/mol] \t certainity \n";
	private static final String EXTENDED_HEADER="conversion \t x[1/T] \t y[ln(dX/dT] \n";
	
	private Map<Double, ArrayList<Double>> x;
	private Map<Double, ArrayList<Double>> y;
	private Map<Double, Double> energyBarriers;
	private Map<Double, Double> certainities;
	private Map<Double, Double> avgTemp;
	private double avgCertainity;
	String identity;
	
	public EnergyEqResults(
			Map<Double, ArrayList<Double>> x,
			Map<Double, ArrayList<Double>> y,
			Map<Double, Double> energyBarriers,
			Map<Double, Double> certainities,
			double avgCertainity,
			Map<Double, Double> avgTemp,
			String identity){
		this.x = x;
		this.y = y;
		this.energyBarriers = energyBarriers;
		this.certainities = certainities;
		this.avgCertainity = avgCertainity;
		this.avgTemp = avgTemp;
		this.identity = identity;
	}

	public Map<Double, ArrayList<Double>> getX() {
		return x;
	}

	public Map<Double, ArrayList<Double>> getY() {
		return y;
	}

	public Map<Double, Double> getEnergyBarriers() {
		return energyBarriers;
	}

	public Map<Double, Double> getCertainities() {
		return certainities;
	}

	public double getAvgCertainity() {
		return avgCertainity;
	}

	public String basicOutput() {
		StringBuilder builder = new StringBuilder();
		builder.append(identity+"\n");
		for(Double conv : energyBarriers.keySet()){
			builder.append(
					String.format("%.2f", (double)conv)+"\t"
					+energyBarriers.get(conv)+"\t"
					+String.format("%.2f", avgTemp.get(conv))
					+"\t"+certainities.get(conv)+"\n");
		}
		builder.append("\nAverage Certainity:"+"\t"+avgCertainity+"\n\n");
		return builder.toString();
	}

	public String extendedOutput() {
		StringBuilder builder = new StringBuilder();
		builder.append(identity+"\n");
		for(double conv : x.keySet()){
			builder.append(String.format("%.2f", conv)+"\n");
			for(int index=0;index<x.get(conv).size();index++){
				builder.append("\t"+x.get(conv).get(index)
						+"\t"+y.get(conv).get(index)+"\n");
			}
		}
		return builder.toString();
	}

	public String getIdentity() {
		return identity;
	}

	public String getModelName() {
		return MODEL_NAME;
	}

	public String basicHeader() {
		return BASIC_HEADER;
	}

	public String extendedHeader() {
		return EXTENDED_HEADER;
	}
	
}
