package dataDSCwrappers;

public class DataDSC {
	private double temperature;
	private double time;
	private double dsc;
	private double sensitivity;
	private double segment;
	
	public DataDSC(
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
	
	
}
