package crystallization_model;

import java.util.ArrayList;

public class NucleationResults implements ModelOutput {
	static final String MODEL_NAME = "NUCLEATION ACTIVITY";
	
	private String identity;
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
			double nuclAct,
			String identity){
		xOfNeat = xNeat;
		xOfNucleated = xNucl;
		Ys = ys;
		neatCertainity = neatCert;
		nucleatedCertainity = nuclCert;
		nucleationActivity = nuclAct;		
		this.identity = identity;
	}
	public double getActivity(){
		return nucleationActivity;
	}
	public double getCertainity(){
		return (neatCertainity+nucleatedCertainity)/2;
	}
	public String basicOutput() {
		StringBuilder builder = new StringBuilder();
		builder.append(identity+"\n");
		builder.append(nucleationActivity+"\t"+neatCertainity+"\t"
						+nucleatedCertainity+"\n\n");
		return builder.toString();
	}
	public String extendedOutput() {
		StringBuilder builder = new StringBuilder();
		builder.append(identity+"\n");
		builder.append("xNeat[1/dTp] \t xNucleated[1/dTp] \t y[ln(CoolingRate)] \n");
		for(int index=0;index<Ys.size();index++){
			builder.append(xOfNeat.get(index)+"\t"+xOfNucleated.get(index)
							+"\t"+Ys.get(index)+"\n");
		}
		return builder.toString();
	}
	public String getIdentity() {
		return identity;
	}
	public String getModelName() {
		return MODEL_NAME;
	}
}
