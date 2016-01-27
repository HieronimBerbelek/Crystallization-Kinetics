package crystallization.results;

import java.util.ArrayList;
import java.util.List;

public class Results {
	private static final String MANY_MODELS = "MANY MODELS! HEADER MAY BE INVALID";
	private static final String EMPTY = "THIS RESULTS ARE EMPTY!";
	private List<ModelOutput> data;
	private String model;
	
	public Results(){
		model = "";
		data= new ArrayList<ModelOutput>();
		model="";
	}
	public Results(ModelOutput output){
		model = "";
		data = new ArrayList<ModelOutput>();
		add(output);
	}
	public Results(ArrayList<ModelOutput> in){
		model = "";
		data = in;
	}
	public String getModelName(){
		return model;
	}
	public void add(ModelOutput output){
		data.add(output);
		checkIdentity();
	}
	public void checkIdentity(){
		for(ModelOutput out : data){
			if(model.contains(out.getModelName())) continue;
			else if(model.equals("")) model=out.getModelName();
			else model = MANY_MODELS;
		}
	}
	public String getOutput(){
		if(data.isEmpty()) return EMPTY;
		StringBuilder builder = new StringBuilder();
		builder.append(getModelName()+System.lineSeparator());
		builder.append(data.get(0).basicHeader()+System.lineSeparator());
		for(ModelOutput out : data){
			builder.append(out.basicOutput().replace("\n", System.lineSeparator()));
		}
		builder.append(System.lineSeparator());
		builder.append(data.get(0).extendedHeader()+System.lineSeparator());
		for(ModelOutput out : data){
			builder.append(out.extendedOutput().replace("\n", System.lineSeparator()));
		}
		return builder.toString();
	}
}
