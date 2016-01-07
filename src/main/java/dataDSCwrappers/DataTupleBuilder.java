//Class used to build DataTuple, which is immutable
//implementing builderPattern
//quite straight-forward
//TO DO - builder can set variable at 0, when it is not initialized, how to avert this?
package dataDSCwrappers;

public class DataTupleBuilder {
	private double temperature;
	private double time;
	private double dsc;
	private double sensitivity;
	private double segment;
	//setters
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
	//implementation
	public DataTuple getData(){	
		return new DataTuple(
				temperature,
				time,
				dsc,
				sensitivity,
				segment);
	}
	public void reset(){
		temperature = 0;
		time = 0;
		dsc = 0;
		sensitivity = 0;
		segment = 0;
	}
}
