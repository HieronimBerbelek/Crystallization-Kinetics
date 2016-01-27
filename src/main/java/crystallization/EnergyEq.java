package crystallization;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import crystallization.results.EnergyEqResults;
import exceptions.DataSizeException;
import linearity.LinearApprox;
import wrappers.CrystallizationData;

public class EnergyEq extends LinearityModel {
	static final int range = 15;
	static final double DELTA_REL = 0.02; //delta between data series
	static final double constantR = 8.3144598;
	private ArrayList<CrystallizationData> data;
	private Map<Double, ArrayList<Double>> x;
	private Map<Double, ArrayList<Double>> y;
	private Map<Double, Double> energyBarriers;
	private Map<Double, Double> certainities;
	private Map<Double, ArrayList<Double>> temperatures;
	private Map<Double, Double> avgTemp;
	private double avgCertainity;
	String identity;
	
	public EnergyEq(ArrayList<CrystallizationData> data){
		this.data = data;
		super.setDefaultApprox();
	}
	public EnergyEq(CrystallizationData data){
		this.data = new ArrayList<CrystallizationData>();
		this.data.add(data);
		super.setDefaultApprox();
	}
	public EnergyEq(ArrayList<CrystallizationData> data, LinearApprox approx){
		this.data = data;
		super.putLinearApprox(approx);
	}
	public EnergyEq(CrystallizationData data, LinearApprox approx){
		this.data = new ArrayList<CrystallizationData>();
		putData(data);
		super.putLinearApprox(approx);
	}
	public void putData(CrystallizationData data){
		this.data.add(data);
	}
	
	@Override
	public EnergyEqResults calculate(double...input) throws DataSizeException{
		initSeries();
		initIdentity();
		initData();
		initBarriers();
		return new EnergyEqResults(x, y, energyBarriers, 
				certainities, avgCertainity, avgTemp, identity);
	}
	private void initIdentity(){
		int endIndex = data.get(0).getIdentity().lastIndexOf(" ");
		identity=data.get(0).getIdentity().substring(0, endIndex);
	}
	private void initBarriers() throws DataSizeException {
		avgCertainity =0;
		energyBarriers = new LinkedHashMap<Double, Double>();
		certainities = new LinkedHashMap<Double, Double>();
		for(double rel = DELTA_REL;rel<1.0;rel+=DELTA_REL){
			super.approximation.calculate(x.get(rel), y.get(rel));
			energyBarriers.put(rel,(constantR*super.approximation.getSlope()/-1000));
			certainities.put(rel, super.approximation.getCertainity());
			avgCertainity += super.approximation.getCertainity();
			avgTemp.put(rel, average(temperatures.get(rel)));
		}
		avgCertainity /=certainities.size();
	}
	private void initSeries() {
		x = new LinkedHashMap<Double, ArrayList<Double>>();
		y = new LinkedHashMap<Double, ArrayList<Double>>();
		temperatures = new LinkedHashMap<Double, ArrayList<Double>>();
		avgTemp = new LinkedHashMap<Double, Double>();
		//no calculations for 0 and 1, because physically there is  
		//no crystallization, and certainities are too low
		for(double rel=DELTA_REL;rel<1.0;rel+=DELTA_REL){
			x.put(rel, new ArrayList<Double>());
			y.put(rel, new ArrayList<Double>());
			temperatures.put(rel, new ArrayList<Double>());
		}
	}
	private void initData(){
		//iterate through series
		for(CrystallizationData data : data ){
			double threshold = DELTA_REL;
			//iterate through single data serie
			//j as index because nested loop
			for(int j=0;j<data.size()&&threshold<1.0;j++){
				if(data.getRelativeX().get(j)>threshold){
					temperatures.get(threshold)
					.add(data.getTemperature().get(j));
					
					x.get(threshold).add(toX(data.getTemperature().get(j)));
					y.get(threshold).add(toY(
							data.getRelativeTime().get(j-range),
							data.getRelativeX().get(j-range),
							data.getRelativeTime().get(j),
							data.getRelativeX().get(j)));
					threshold +=DELTA_REL;
				}
			}
		}
	}
	private double toX(double temp){
		return (1.0/(temp+273.15));
	}
	private double toY(double time1, double x1, double time2, double x2){
		return Math.log((x2-x1)/(time2-time1));
	}
	private double average (ArrayList<Double> input){
		double toReturn=0;
		for(Double d : input){
			toReturn +=d;
		}
		return toReturn/input.size();
	}
}
