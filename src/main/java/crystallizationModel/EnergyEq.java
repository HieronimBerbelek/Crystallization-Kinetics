package crystallizationModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import dataWrappers.CrystallizationData;
import exceptions.DataSizeException;
import linearRegression.LeastSquaresApprox;
import linearRegression.LinearApprox;

public class EnergyEq {
	static final int range = 15;
	static final double DELTA_REL = 0.05; //delta between data series
	static final double constantR = 8.3144598;
	private ArrayList<CrystallizationData> data;
	private LinearApprox approximation;
	private Map<Double, ArrayList<Double>> x;
	private Map<Double, ArrayList<Double>> y;
	private Map<Double, Double> energyBarriers;
	private Map<Double, Double> certainities;
	private double avgCertainity;
	String identity;
	
	public EnergyEq(ArrayList<CrystallizationData> data){
		this.data = data;
		setDefaultApprox();
	}
	public EnergyEq(CrystallizationData data){
		this.data = new ArrayList<CrystallizationData>();
		this.data.add(data);
		setDefaultApprox();
	}
	public EnergyEq(ArrayList<CrystallizationData> data, LinearApprox approx){
		this.data = data;
		putLinearApprox(approx);
	}
	public EnergyEq(CrystallizationData data, LinearApprox approx){
		this.data = new ArrayList<CrystallizationData>();
		putData(data);
		putLinearApprox(approx);
	}
	public void putLinearApprox(LinearApprox approx){
		approximation = approx;
	}
	public void putData(CrystallizationData data){
		this.data.add(data);
	}
	public void setDefaultApprox(){
		approximation = new LeastSquaresApprox();
	}
	
	public EnergyEqResults calculate() throws DataSizeException{
		initSeries();
		initIdentity();
		initData();
		initBarriers();
		return new EnergyEqResults(x, y, energyBarriers, 
				certainities, avgCertainity, identity);
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
			approximation.calculate(x.get(rel), y.get(rel));
			energyBarriers.put(rel, (constantR*approximation.getSlope()/-1000));
			certainities.put(rel, approximation.getCertainity());
			avgCertainity += approximation.getCertainity();
		}
		avgCertainity /=certainities.size();
	}
	private void initSeries() {
		x = new LinkedHashMap<Double, ArrayList<Double>>();
		y = new LinkedHashMap<Double, ArrayList<Double>>();
		//no calculations for 0 and 1, because physically there is  
		//no crystallization, and certainities are too low
		for(double rel=DELTA_REL;rel<1.0;rel+=DELTA_REL){
			x.put(rel, new ArrayList<Double>());
			y.put(rel, new ArrayList<Double>());
		}
	}
	private void initData(){
		//iterate through series
		for(CrystallizationData data : data ){
			double threshold = DELTA_REL;
			//iterate through single data serie
			for(int index2=0;index2<data.size()&&threshold<1.0;index2++){
				if(data.getRelativeX().get(index2)>threshold){
					x.get(threshold).add(toX(data.getTemperature().get(index2)));
					y.get(threshold).add(toY(
							data.getRelativeTime().get(index2-range),
							data.getRelativeX().get(index2-range),
							data.getRelativeTime().get(index2),
							data.getRelativeX().get(index2)));
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
}
