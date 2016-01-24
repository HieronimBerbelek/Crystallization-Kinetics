package crystallization_model.results;
//TO DO: extended output!
import java.util.ArrayList;

public class AvramiResults implements ModelOutput {
	static final String MODEL_NAME = "AVRAMI";
	
	private ArrayList<Double> lnTime = new ArrayList<Double>();
	private ArrayList<Double> ys = new ArrayList<Double>();
	private Double coefficient;
	private Double exponent;
	private Double certainity;
	String identity;
	public AvramiResults(
			ArrayList<Double> lnTime, 
			ArrayList<Double> ys, 
			Double coefficient,
			Double exponent,
			Double certainity,
			String identity) {
		this.lnTime = lnTime;
		this.ys = ys;
		this.coefficient = coefficient;
		this.exponent = exponent;
		this.certainity = certainity;
		this.identity = identity;
	}
	public ArrayList<Double> getLnTime() {
		return lnTime;
	}
	public ArrayList<Double> getAvramiY() {
		return ys;
	}
	public Double getCoefficient() {
		return coefficient;
	}
	public Double getExponent() {
		return exponent;
	}
	public Double getCertainity() {
		return certainity;
	}
	public String basicOutput() {
		return identity+"\t"+coefficient+"\t"+exponent+"\t"+certainity+"\n";
	}
	public String extendedOutput() {
		StringBuilder builder = new StringBuilder();
		builder.append(identity+"\n");
		builder.append("x[log(t)] \t y[log(-ln(1-X))] \n");
		int numberOfPoints = lnTime.size()/50;
		for(int index =0;index< lnTime.size();index+=numberOfPoints){
			builder.append(lnTime.get(index) + "\t" + ys.get(index) + "\n");
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
