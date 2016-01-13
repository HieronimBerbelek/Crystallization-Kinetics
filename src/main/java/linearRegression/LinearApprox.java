package linearRegression;

import java.util.ArrayList;

import exceptions.DataSizeException;

public interface LinearApprox {
	public void calculate (ArrayList<Double> x, ArrayList<Double>y) 
			throws DataSizeException;
	public double getSlope();
	public double getIntercept();
	public double getCertainity();
}
