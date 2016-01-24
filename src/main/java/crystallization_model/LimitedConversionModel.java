package crystallization_model;

import exceptions.ConversionLimitException;


public abstract class LimitedConversionModel extends LinearityModel {
	private double lowerLimit = 0.05;
	private double upperLimit = 0.95;
	
	public void setLowerLimit(double d){
		if(d<=1.0 && d<upperLimit) lowerLimit = d;
		else throw new ConversionLimitException();
	}
	public void setUpperlimit(double d){
		if(d<=1.0 && d>lowerLimit) upperLimit = d;
		else throw new ConversionLimitException();
	}
	public boolean isAboveUpperLimit(double d) {
		return (d>=upperLimit);
	}
	public boolean isBelowLowerLimit(double d) {
		return (d<=lowerLimit);
	}
	public boolean isInBounds(double d){
		return (d<upperLimit && d>lowerLimit);
	}
	public double getLowerLimit(){
		return lowerLimit;
	}
	public double getUpperLimit(){
		return upperLimit;
	}
}
