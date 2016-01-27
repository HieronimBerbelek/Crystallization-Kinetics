package crystallization;

import java.util.ArrayList;

import crystallization.results.NucleationResults;
import exceptions.DataSizeException;
import linearity.LinearApprox;
import wrappers.CrystallizationData;

public class NucleationActivity extends LinearityModel {
	String identity;
	private ArrayList<Double> xOfNeat;
	private ArrayList<Double> xOfNucleated;
	private ArrayList<Double> tempOfNeat;
	private ArrayList<Double> tempOfNucleated;
	private double neatMeltingT;
	private double nucleatedMeltingT;
	private ArrayList<Double> Ys; //ln(coolingRate) list
	
	private double neatCertainity;
	private double nucleatedCertainity;
	private double nucleationActivity;
	
	public NucleationActivity(
			ArrayList<CrystallizationData> neat,
			ArrayList<CrystallizationData> nucleated) throws DataSizeException{
		if(neat.size()!=nucleated.size()) throw new DataSizeException();
		initYs(neat, nucleated);
		initTemps(neat, nucleated);
		super.setDefaultApprox();
	}
	public NucleationActivity(
			ArrayList<CrystallizationData> neat,
			ArrayList<CrystallizationData> nucleated,
			LinearApprox approx) throws DataSizeException{
		if(neat.size()!=nucleated.size()) throw new DataSizeException();
		initYs(neat, nucleated);
		initTemps(neat, nucleated);
		super.putLinearApprox(approx);
	}
	private void initTemps(
			ArrayList<CrystallizationData> neat, 
			ArrayList<CrystallizationData> nucleated) {
		tempOfNeat = new ArrayList<Double>();
		tempOfNucleated = new ArrayList<Double>();
		for(int index=0;index<neat.size();index++){
			tempOfNeat.add(neat.get(index).getPeakTemperature());
			tempOfNucleated.add(nucleated.get(index).getPeakTemperature());
		}		
	}
	private void initYs(
			ArrayList<CrystallizationData> neat,
			ArrayList<CrystallizationData> nucleated){
		Ys = new ArrayList<Double>();
		for(int index=0;index<nucleated.size();index++){
			if(neat.get(index).getCoolingRate()
					-(int)nucleated.get(index).getCoolingRate()<2){
				double average = (neat.get(index).getCoolingRate()
						+(int)nucleated.get(index).getCoolingRate())/2;
				Ys.add(Math.log(average));
			}
		}
		
		int endIndex = nucleated.get(0).getIdentity().lastIndexOf(" ");
		identity=nucleated.get(0).getIdentity().substring(0, endIndex);
	}
	private void initXs(double neat, double nucleated){
		neatMeltingT = neat;
		nucleatedMeltingT = nucleated;
		xOfNeat =new ArrayList<Double>();
		xOfNucleated = new ArrayList<Double>();
		double neatUC; 		//undercooling
		double nucleatedUC; //undercooling
		for(int index=0;index<tempOfNeat.size();index++){
			neatUC = neatMeltingT - tempOfNeat.get(index);
			nucleatedUC = nucleatedMeltingT - tempOfNucleated.get(index);
			xOfNeat.add(1/(neatUC*neatUC));
			xOfNucleated.add(1/(nucleatedUC*nucleatedUC));
		}
	}
	@Override
	public NucleationResults calculate(double...input) throws DataSizeException{
		double neat = input[0];
		double nucleated = input[1];
		initXs(neat, nucleated);
		initLinearity();
		return new NucleationResults (
				xOfNeat,
				xOfNucleated,
				Ys,
				neatCertainity,
				nucleatedCertainity,
				nucleationActivity,
				identity);
	}
	private void initLinearity() throws DataSizeException {
		double neatSlope;
		double nucleatedSlope;
		
		super.approximation.calculate(xOfNeat, Ys);
		neatSlope=super.approximation.getSlope();
		neatCertainity=super.approximation.getCertainity();
		
		super.approximation.calculate(xOfNucleated, Ys);
		nucleatedSlope = super.approximation.getSlope();
		nucleatedCertainity = super.approximation.getCertainity();
		
		nucleationActivity = nucleatedSlope/neatSlope;
	}
}
