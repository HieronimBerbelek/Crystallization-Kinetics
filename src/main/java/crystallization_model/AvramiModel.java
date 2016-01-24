package crystallization_model;

import java.util.ArrayList;

import crystallization_model.results.AvramiResults;
import exceptions.DataSizeException;
import linearity.LeastSquaresApprox;
import linearity.LinearApprox;
import wrappers.CrystallizationData;

public class AvramiModel extends LimitedConversionModel {
	//conversion limits, essential for good data fit
	private ArrayList<Double> lnTime = new ArrayList<Double>();
	private ArrayList<Double> ys = new ArrayList<Double>();
	private CrystallizationData data;
	private Double coefficient;
	private Double exponent;
	private Double certainity;

	public AvramiModel(CrystallizationData input){
		putData(input);
		setDefaultApprox();
	}
	public AvramiModel(CrystallizationData input, LinearApprox approx){
		putData(input);
		putLinearApprox(approx);
	}
	public void putData(CrystallizationData input){
		data=input;
	}	
	public AvramiResults calculate(double...input) throws DataSizeException{
		double toLogTime;
		double toYs;
		//Avrami plot consist of points with limited conversion to crystalline phase
		//limits come from super class CrystallizationModel
		for(int index = 0; index<data.size();index++){
			if (super.isInBounds(data.getRelativeX().get(index))) {
				toLogTime = (Math.log10(data.getRelativeTime().get(index)));
				toYs = (Math.log10(-1 * Math.log(1 - data.getRelativeX().get(index))));
				//lower and upper limit should get nonfinite values, but for certainity:
				if (Double.isFinite(toLogTime) && Double.isFinite(toYs)) {
					lnTime.add(toLogTime);
					ys.add(toYs);
				} 
			}
		}
		super.approximation.calculate(lnTime, ys);
		exponent = super.approximation.getSlope();
		coefficient = Math.pow(10, super.approximation.getIntercept())
				/data.getCoolingRate();
		certainity = super.approximation.getCertainity();
		return new AvramiResults(lnTime, ys, coefficient, exponent, 
				certainity, data.getIdentity());
	}
}
