package dataDSCwrappers;

public class DataTupleBuilder {
	private double temperature;
	private double time;
	private double dsc;
	private double sensitivity;
	private double segment;
	
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public void setDsc(double dsc) {
		this.dsc = dsc;
	}
	public void setSensitivity(double sensitivity) {
		this.sensitivity = sensitivity;
	}
	public void setSegment(double segment) {
		this.segment = segment;
	}
	
	public DataTuple getData(){
		return new DataTuple(
				temperature,
				time,
				dsc,
				sensitivity,
				segment);
	}
}
