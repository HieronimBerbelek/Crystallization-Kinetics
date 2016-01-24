//Class used to build DataTuple, which is immutable
//implementing builderPattern
//quite straight-forward

package wrappers;

import exceptions.DscDataException;

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
	//getters
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
	//implementation
	public DataTuple buildDataTuple() throws DscDataException{	
		if(temperature==0||time==0||dsc==0||sensitivity==0||segment==0) throw new DscDataException();
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
