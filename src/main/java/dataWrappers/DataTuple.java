//wrapper class for all 5 variables in Proteus DSC file
//has builder: DataTupleBuilder
package dataWrappers;

public class DataTuple {
	private double temperature;
	private double time;
	private double dsc;
	private double sensitivity;
	private double segment;
	
	
	public DataTuple(
			double temperature,
			double time,
			double dsc,
			double sensitivity,
			double segment){
		this.temperature = temperature;
		this.time = time;
		this.dsc = dsc;
		this.sensitivity = sensitivity;
		this.segment = segment;
	}
	public DataTuple(DataTuple other){
		this.temperature = other.temperature;
		this.time = other.time;
		this.dsc = other.dsc;
		this.sensitivity = other.sensitivity;
		this.segment = other.segment;
	}
	
	public double getTemperature() {
		return temperature;
	}
	public double getTime() {
		return time;
	}
	public double getDsc() {
		return dsc;
	}
	public double getSensitivity() {
		return sensitivity;
	}
	public double getSegment() {
		return segment;
	}
	public String toString(){
		return ("Temperature: "+temperature
				+" Time: "+time
				+" DSC: "+dsc
				+" Sensitivity: "+sensitivity
				+" Segment: "+segment);
	}
	
}
