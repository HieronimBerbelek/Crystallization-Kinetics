package crystallizationModel;

import java.util.ArrayList;

public class NucleationResults {
	private ArrayList<Double> xOfNeat;
	private ArrayList<Double> xOfNucleated;
	private ArrayList<Double> Ys; //ln(coolingRate) list
	
	private double neatCertainity;
	private double nucleatedCertainity;
	private double nucleationActivity;
	
	NucleationResults(
			ArrayList<Double> xNeat,
			ArrayList<Double> xNucl,
			ArrayList<Double> ys,
			double neatCert,
			double nuclCert,
			double nuclAct){
		xOfNeat = xNeat;
		xOfNucleated = xNucl;
		Ys = ys;
		neatCertainity = neatCert;
		nucleatedCertainity = nuclCert;
		nucleationActivity = nuclAct;		
	}
	public double getActivity(){
		return nucleationActivity;
	}
	public double getCertainity(){
		return (neatCertainity+nucleatedCertainity)/2;
	}
}
