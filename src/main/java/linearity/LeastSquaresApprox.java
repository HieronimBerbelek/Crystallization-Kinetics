package linearity;

import java.util.ArrayList;

import exceptions.DataSizeException;

public class LeastSquaresApprox implements LinearApprox {
	private Double slope;
	private Double intercept;
	private Double certainity;


	public LeastSquaresApprox(){
	}
	public LeastSquaresApprox(ArrayList<Double> x, ArrayList<Double>y) throws DataSizeException{
		calculate(x,y);
	}
	public void calculate (ArrayList<Double> x, ArrayList<Double>y) throws DataSizeException{
		if(x.size()==0||x.size()!=y.size()) throw new DataSizeException();
		int size = x.size();
		double sumX=0.0;
		double sumX2=0.0;
		double sumY=0.0;
		double sumXY=0.0;
		
		double sumSSe=0.0;
		double sumSSt=0.0;
		double meanY=0.0;
		
		for(int index = 0; index<size ;index++){
			sumX+=x.get(index);
			sumY+=y.get(index);
			sumX2+=(x.get(index)*x.get(index));
			sumXY+=(x.get(index)*y.get(index));
			meanY += y.get(index); // just summing y's here
		}
		double sumXsumY = sumX*sumY;
		double squareSumX = sumX*sumX;
		meanY = meanY/size; 	//now it's y's mean value
		
		slope = ((size*sumXY)-sumXsumY)/((size*sumX2)-squareSumX);
		intercept = (sumY-(slope*sumX))/size;
		
		//uncertainity calculation
		for(int index = 0; index<size ;index++){
			sumSSe += Math.pow((y.get(index)-((x.get(index)*slope)+intercept)), 2);
			sumSSt += Math.pow((y.get(index)-meanY), 2);
		}
		certainity = (sumSSt-sumSSe)/sumSSt;
	}

	public double getSlope() {
		return slope;
	}

	public double getIntercept() {
		return intercept;
	}
	public double getCertainity() {
		return certainity;
	}
	@Override
	public String toString(){
		if(slope == null) return "Not calculated yet!";
		else
			return "Slope: "+slope+", Intercept: "+intercept+", certainity: "+certainity;
	}
	public double interpole(int arg,double x1, double y1, double x2, double y2){
		return (y1+((arg-x1)*((y2-y1)/(x2-x1))));
	}
	public double interpole(double arg,double x1, double y1, double x2, double y2){
		return (y1+((arg-x1)*((y2-x1)/(y2-x1))));
	}
}
