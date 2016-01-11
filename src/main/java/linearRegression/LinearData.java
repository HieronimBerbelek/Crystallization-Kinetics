package linearRegression;

public class LinearData {
	private double slope;
	private double intercept;
	private double slopeError;
	private double interceptError;
	
	public LinearData(double s, double i, double se, double ie){
		slope = s;
		intercept = i;
		slopeError = se;
		interceptError = ie;
	}
	public double getSlope(){
		return slope;
	}
	public double getIntercept(){
		return intercept;
	}
	public double getSlopeError(){
		return slopeError;
	}
	public double getInterceptError(){
		return interceptError;
	}
}
