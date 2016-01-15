package crystallizationModel;

import exceptions.ConversionLimitException;


public abstract class CrystallizationModel {
	private static double lowerLimit = 0.05;
	private static double upperLimit = 0.9;
	
	public static void setLowerLimit(double d){
		if(d<=1.0 && d<getUpperLimit()) lowerLimit = d;
		else throw new ConversionLimitException();
	}
	public static void setUpperlimit(double d){
		if(d<=1.0 && d>getLowerLimit()) upperLimit = d;
		else throw new ConversionLimitException();
	}
	protected static double getUpperLimit() {
		return upperLimit;
	}
	protected static double getLowerLimit() {
		return lowerLimit;
	}
	protected static boolean isInBounds(double d){
		return (d<upperLimit && d>lowerLimit);
	}
}
