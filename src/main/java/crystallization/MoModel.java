package crystallization;

import java.util.ArrayList;
import java.util.HashMap;

import crystallization.results.MoResults;
import exceptions.DataSizeException;
import linearity.LinearApprox;
import wrappers.CrystallizationData;

public class MoModel extends LimitedConversionModel {
	private final int deltaConversionTen = 10;
	private final int deltaConversionFive = 5;
	
	private ArrayList<CrystallizationData> data;
	private HashMap<Double, ArrayList<Double>> plot; //multiple Xs
	private ArrayList<Double> Ys; //log10(coolingRate) list
	
	String identity;
	private ArrayList<Double> coefficientsB;
	private double avgCoeffB=0;
	private ArrayList<Double> coefficientsFT;
	private double avgCoeffFT=0;
	private ArrayList<Double> certainities;
	private double avgCertainity=0;
	public MoModel(ArrayList<CrystallizationData> data){
		this.data = data;
		setDefaultApprox();
	}
	public MoModel(CrystallizationData data){
		this.data = new ArrayList<CrystallizationData>();
		this.data.add(data);
		setDefaultApprox();
	}
	public MoModel(ArrayList<CrystallizationData> data, LinearApprox approx){
		this.data = data;
		super.putLinearApprox(approx);
	}
	public MoModel(CrystallizationData data, LinearApprox approx){
		this.data = new ArrayList<CrystallizationData>();
		this.data.add(data);
		super.putLinearApprox(approx);
	}
	public void putData(CrystallizationData data){
		this.data.add(data);
	}
	@Override
	public MoResults calculate(double...input) throws DataSizeException{
		initYs();
		initPlot(createSeriesList());
		initLinearity();
		initIdentity();
		return new MoResults(
				plot,
				Ys,
				coefficientsB,
				avgCoeffB,
				coefficientsFT,
				avgCoeffFT,
				certainities,
				avgCertainity,
				identity);
	}
	private void initIdentity() {
		int endIndex = data.get(0).getIdentity().lastIndexOf(" ");
		identity=data.get(0).getIdentity().substring(0, endIndex);
	}
	private void initLinearity() throws DataSizeException {
		coefficientsB = new ArrayList<Double>();
		coefficientsFT = new ArrayList<Double>();
		certainities = new ArrayList<Double>();
		
		for(Double i: plot.keySet()){
			super.approximation.calculate(plot.get(i), Ys);
			coefficientsB.add(-super.approximation.getSlope());
			avgCoeffB += -super.approximation.getSlope();
			coefficientsFT.add(Math.pow(Math.E, super.approximation.getIntercept()));
			avgCoeffFT += Math.pow(Math.E, super.approximation.getIntercept());
			certainities.add(super.approximation.getCertainity());
			avgCertainity += super.approximation.getCertainity();
		}
		avgCoeffB /= coefficientsB.size();
		avgCoeffFT /= coefficientsFT.size();
		avgCertainity /= certainities.size();
	}
	private void initYs(){
		Ys = new ArrayList<Double>();
		for(int index=0;index<data.size();index++){
			Ys.add(Math.log10(data.get(index).getCoolingRate()));
		}
	}
	private ArrayList<Double> createSeriesList(){
		ArrayList<Double> series = new ArrayList<Double>();
		int delta;
		int percentLowerLimit = (int)(super.getLowerLimit()*100);
		int percentUpperLimit = (int)(super.getUpperLimit()*100);
		if(percentUpperLimit-percentLowerLimit>50)
			delta = deltaConversionTen;
		else delta = deltaConversionFive;
		
		for(int i=percentLowerLimit;i<=percentUpperLimit;i++){
			if(i%delta==0){
				series.add((double)i/100);
			}
		}
		return series;
	}
	private void initPlot(ArrayList<Double> series){
		plot = new HashMap<Double, ArrayList<Double>>();
		for(int i=0;i<series.size();i++){	//iterate through plot series
			plot.put(series.get(i), new ArrayList<Double>());
			for(int j = 0;j<data.size();j++){	//iterate through data series
				//iterate through single serie
				for(int k=0;k<data.get(j).size();k++){
					if(data.get(j).getRelativeX().get(k)>series.get(i)){
						double input = super.approximation.interpole(series.get(i), 
								data.get(j).getRelativeX().get(k), 
								data.get(j).getRelativeTime().get(k), 
								data.get(j).getRelativeX().get(k-1), 
								data.get(j).getRelativeTime().get(k-1));
						plot.get(series.get(i)).add(Math.log(input));
						break;
					}
				}
			}
		}
		/*okay, here is note for future myself:
		 * this method iterates through RelativeCrystallinity serie
		 *  because each RelativeX value is separate data serie, 
		 *  its x values are mutual log10(coolingRate)
		 * plot values are lists of relativeX at these temperatures
		 * which are y of this function
		 * interpole is necessary to find value at exact temperature
		 * then it is put to list in plot for temperature
		**/
	}
	
}
