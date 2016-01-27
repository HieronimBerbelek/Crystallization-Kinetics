package crystallization_model.results;

import java.util.ArrayList;

public class NucleationResults implements ModelOutput {
	private static final String MODEL_NAME = "NUCLEATION ACTIVITY";
	private static final String BASIC_HEADER=
			"nucleation activity \t neat certainity \t nucleated certainity \n";
	private static final String EXTENDED_HEADER=
			"xNeat[1/dTp] \t xNucleated[1/dTp] \t y[ln(CoolingRate)] \n";
	
	private String identity;
	private ArrayList<Double> xOfNeat;
	private ArrayList<Double> xOfNucleated;
	private ArrayList<Double> Ys; //ln(coolingRate) list
	
	private double neatCertainity;
	private double nucleatedCertainity;
	private double nucleationActivity;
	
	public NucleationResults(
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
	public String basicHeader() {
		return BASIC_HEADER;
	}
	public String extendedHeader() {
		return EXTENDED_HEADER;
	}
}
