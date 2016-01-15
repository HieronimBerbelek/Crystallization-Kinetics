package crystallizationModel;


import java.util.ArrayList;
import java.util.HashMap;

import dataWrappers.CrystallizationData;
import exceptions.DataSizeException;
import linearRegression.LeastSquaresApprox;
import linearRegression.LinearApprox;

public class OzawaModel extends CrystallizationModel {
	private ArrayList<CrystallizationData> data;
	private LinearApprox approximation;
	private HashMap<Double, ArrayList<Double>> plot;
	
	double lowerTempLimit;
	double upperTempLimit;
	
	public OzawaModel(ArrayList<CrystallizationData> data){
		this.data = data;
		setDefaultApprox();
	}
	public OzawaModel(CrystallizationData data){
		this.data = new ArrayList<CrystallizationData>();
		this.data.add(data);
		setDefaultApprox();
	}
	public OzawaModel(ArrayList<CrystallizationData> data, LinearApprox approx){
		this.data = data;
		approximation = approx;
	}
	public OzawaModel(CrystallizationData data, LinearApprox approx){
		this.data = new ArrayList<CrystallizationData>();
		this.data.add(data);
		approximation = approx;
	}
	
	public void putLinearApprox(LinearApprox approx){
		approximation = approx;
	}
	public void putData(CrystallizationData data){
		this.data.add(data);
	}
	public void setDefaultApprox(){
		approximation = new LeastSquaresApprox();
	}

	public void calculate() throws DataSizeException{
		plot = new HashMap<Double, ArrayList<Double>>();
		setTempLimits();
		
	}
	private void setTempLimits(){
		lowerTempLimit = Double.NEGATIVE_INFINITY;
		upperTempLimit = Double.POSITIVE_INFINITY;
		
		for(int index=0; index<data.size();index++){
			for(int index2=0;index2<data.get(index).getSize();index2++){
				if(super.isInBounds(data.get(index).getRelativeX().get(index2))
						&&data.get(index).getTemperature().get(index2)>lowerTempLimit){
					lowerTempLimit = data.get(index).getTemperature().get(index2);
				}
				if(super.isAboveUpperLimit(data.get(index).getRelativeX().get(index2))
						&&data.get(index).getTemperature().get(index2)<upperTempLimit){
					upperTempLimit = data.get(index).getTemperature().get(index2);
					break;
				}
			}
		}
	}
	public double getLowerTempLimit(){
		return lowerTempLimit;
	}
	public double getUpperTempLimit(){
		return upperTempLimit;
	}
}
