package crystallizationModel;

import java.util.ArrayList;

import dataWrappers.CrystallizationData;
import exceptions.DataSizeException;
import linearRegression.LeastSquaresApprox;
import linearRegression.LinearApprox;

public class NucleationActivity {
	String identity;
	private ArrayList<Double> xOfNeat;
	private ArrayList<Double> xOfNucleated;
	private ArrayList<Double> tempOfNeat;
	private ArrayList<Double> tempOfNucleated;
	private double neatMeltingT;
	private double nucleatedMeltingT;
	private ArrayList<Double> Ys; //ln(coolingRate) list

	private LinearApprox approximation;
	
	private double neatCertainity;
	private double nucleatedCertainity;
	private double nucleationActivity;
	
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
		tempOfNeat = new ArrayList<Double>();
		tempOfNucleated = new ArrayList<Double>();
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
		double neatUC;
		double nucleatedUC;
		for(int index=0;index<tempOfNeat.size();index++){
			neatUC = neatMeltingT - tempOfNeat.get(index);
			nucleatedUC = nucleatedMeltingT - tempOfNucleated.get(index);
			xOfNeat.add(1/(neatUC*neatUC));
			xOfNucleated.add(1/(nucleatedUC*nucleatedUC));
		}
	}
	public NucleationResults calculate(double neat, double nucleated) throws DataSizeException{
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
		
		approximation.calculate(xOfNeat, Ys);
		neatSlope=approximation.getSlope();
		neatCertainity=approximation.getCertainity();
		
		approximation.calculate(xOfNucleated, Ys);
		nucleatedSlope = approximation.getSlope();
		nucleatedCertainity = approximation.getCertainity();
		
		nucleationActivity = neatSlope/nucleatedSlope;
	}
}
