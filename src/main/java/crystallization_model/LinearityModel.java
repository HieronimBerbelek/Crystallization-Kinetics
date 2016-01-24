package crystallization_model;

import crystallization_model.results.ModelOutput;
import exceptions.DataSizeException;
import linearity.LeastSquaresApprox;
import linearity.LinearApprox;

public abstract class LinearityModel {

	protected LinearApprox approximation;
	
	public void putLinearApprox(LinearApprox approx){
		approximation = approx;
	}
	public void setDefaultApprox(){
		approximation = new LeastSquaresApprox();
	}
	public abstract ModelOutput calculate(double...input) throws DataSizeException;
}
