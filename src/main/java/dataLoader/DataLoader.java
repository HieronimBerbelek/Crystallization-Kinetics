package dataLoader;

import java.util.ArrayList;
import java.util.Scanner;

import dataDSCwrappers.DataTuple;
import dataDSCwrappers.DataTupleBuilder;
import inputProvider.DataProvider;

public class DataLoader {
	Scanner fileScanner;
	
	String identityFlag = "#IDENTITY:";
	
	ArrayList<DataTuple> data = new ArrayList<DataTuple>();
	DataTupleBuilder builder;
	String identity;
	
	public DataLoader(DataProvider data){
		fileScanner = data.getData();
	}
	public void loadData(){
		for(int i =0; i<4; i++) fileScanner.nextLine(); //go to identity line
		identity = fileScanner.nextLine().substring(identityFlag.length());
		
	}
}
