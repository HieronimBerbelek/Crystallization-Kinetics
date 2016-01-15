package linearRegression;

import java.util.ArrayList;

import exceptions.DataSizeException;

public interface LinearApprox {
	public void calculate (ArrayList<Double> x, ArrayList<Double>y) 
			throws DataSizeException;
	public double getSlope();
	public double getIntercept();
	public double getCertainity();
	public double interpole(int arg,double t1, double rel1, double t2, double rel2);
}
