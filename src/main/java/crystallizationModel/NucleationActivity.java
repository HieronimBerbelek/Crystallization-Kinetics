package crystallizationModel;

import java.util.ArrayList;

import dataWrappers.CrystallizationData;
import exceptions.DataSizeException;
import linearRegression.LeastSquaresApprox;
import linearRegression.LinearApprox;

public class NucleationActivity {
	private ArrayList<Double> xOfNeat;
	private ArrayList<Double> xOfNucleated;
	private ArrayList<Double> tempOfNeat;
	private ArrayList<Double> tempOfNucleated;
	private double neatMeltingT;
	private double nucleatedMeltingT;
	private ArrayList<Double> Ys; //ln(coolingRate) list
	private LinearApprox approximation;
	
	public NucleationActivity(
			ArrayList<CrystallizationData> neat,
			ArrayList<CrystallizationData> nucleated) throws DataSizeException{
		if(neat.size()!=nucleated.size()) throw new DataSizeException();
		initYs(neat, nucleated);
		initTemps(neat, nucleated);
		setDefaultApprox();
	}
	public NucleationActivity(
			ArrayList<CrystallizationData> neat,
			ArrayList<CrystallizationData> nucleated,
			LinearApprox approx) throws DataSizeException{
		if(neat.size()!=nucleated.size()) throw new DataSizeException();
		initYs(neat, nucleated);
		initTemps(neat, nucleated);
		putLinearApprox(approx);
	}
	private void initTemps(
			ArrayList<CrystallizationData> neat, 
			ArrayList<CrystallizationData> nucleated) {
		for(int index=0;index<neat.size();index++){
			tempOfNeat.add(neat.get(index).getPeakTemperature());
			tempOfNucleated.add(nucleated.get(index).getPeakTemperature());
		}		
	}
	public void setDefaultApprox(){
		approximation = new LeastSquaresApprox();
	}
	public void putLinearApprox(LinearApprox approx){
		approximation = approx;
	}
	private void initYs(
			ArrayList<CrystallizationData> neat,
			ArrayList<CrystallizationData> nucleated){
		Ys = new ArrayList<Double>();
		for(int index=0;index<neat.size();index++){
			if(neat.get(index).getCoolingRate()
					==nucleated.get(index).getCoolingRate()){
				Ys.add(Math.log(neat.get(index).getCoolingRate()));
			}
		}
	}
	public void calculate(double neat, double nucleated){
		neatMeltingT = neat;
		nucleatedMeltingT = nucleated;
	}
}
