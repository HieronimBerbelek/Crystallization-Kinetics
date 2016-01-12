package linearRegression;

import java.util.ArrayList;

public interface LinearApprox {
	public void calculate (ArrayList<Double> x, ArrayList<Double>y);
	public double getSlope();
	public double getSlopeError();
	public double getIntercept();
	public double getInterceptError();
}
