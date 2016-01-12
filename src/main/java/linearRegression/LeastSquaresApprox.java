package linearRegression;

import java.util.ArrayList;

//TO DO - make it generic maybe?
public class LeastSquaresApprox implements LinearApprox {
	private double slope;
	private double intercept;
	private double slopeError;
	private double interceptError;

	
	public void calculate (ArrayList<Double> x, ArrayList<Double>y){
		int size = x.size();
		double sumX=0.0;
		double sumX2=0.0;
		double sumY=0.0;
		double sumY2=0.0;
		double sumXY=0.0;
		for(int index = 0; index<size ;index++){
			sumX+=x.get(index);
			sumY+=y.get(index);
			sumX2+=(x.get(index)*x.get(index));
			sumY2+=(y.get(index)*y.get(index));
			sumXY+=(x.get(index)*y.get(index));
		}
		double sumXsumY = sumX*sumY;
		double squareSumX = sumX*sumX;
		
		slope = ((size*sumXY)-sumXsumY)/((size*sumX2)-squareSumX);
		intercept = (sumY-(slope*sumX))/size;
		slopeError = Math.sqrt((size*(sumY2-(slope*sumXY)-(intercept*sumY))
						/(size-2)*((size*sumX2)-squareSumX)));
		interceptError = Math.sqrt((slopeError*sumX2)/size);
	}


	public double getSlope() {
		return slope;
	}


	public double getSlopeError() {
		return slopeError;
	}


	public double getIntercept() {
		return intercept;
	}


	public double getInterceptError() {
		return interceptError;
	}
}
