package linearRegression;

import java.util.ArrayList;

import exceptions.DataSizeException;

public interface LinearApprox {
	public void calculate (ArrayList<Double> x, ArrayList<Double>y) 
			throws DataSizeException;
	public double getSlope();
	public double getIntercept();
	public double getCertainity();
	public double interpole(int arg,double x1, double y1, double x2, double y2);
	public double interpole(double arg,double x1, double y1, double x2, double y2);
}
