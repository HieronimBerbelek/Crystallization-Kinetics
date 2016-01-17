package crystallizationModel;

import java.util.ArrayList;
import java.util.Map;

import dataWrappers.CrystallizationData;
import linearRegression.LinearApprox;

public class EnergyEqResults {
	private Map<Double, ArrayList<Double>> x;
	private Map<Double, ArrayList<Double>> y;
	private Map<Double, Double> energyBarriers;
	private Map<Double, Double> certainities;
	private double avgCertainity;
	
	public EnergyEqResults(
			Map<Double, ArrayList<Double>> x,
			Map<Double, ArrayList<Double>> y,
			Map<Double, Double> energyBarriers,
			Map<Double, Double> certainities,
			double avgCertainity){
		this.x = x;
		this.y = y;
		this.energyBarriers = energyBarriers;
		this.certainities = certainities;
		this.avgCertainity = avgCertainity;
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
	
}
