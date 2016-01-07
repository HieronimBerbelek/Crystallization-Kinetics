//Class used to build DataTuple, which is immutable
//implementing builderPattern
//quite straight-forward

package dataDSCwrappers;

public class DataTupleBuilder {
	//Double wrapper, to prevent setting DataTuple fields to 0
	private Double temperature;
	private Double time;
	private Double dsc;
	private Double sensitivity;
	private Double segment;
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
		temperature = 0.0;
		time = 0.0;
		dsc = 0.0;
		sensitivity = 0.0;
		segment = 0.0;
	}
}
