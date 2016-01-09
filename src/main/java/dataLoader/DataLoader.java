package dataLoader;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import dataDSCwrappers.DataTuple;
import dataDSCwrappers.DataTupleBuilder;
import inputProvider.DataProvider;
/*is there need and way to divide loadMetaData and loadNumericData into shorter methods?
 */
public class DataLoader {
	private Scanner fileScanner;
	
	private String identityFlag = "#IDENTITY:";
	private String commaDecimalFlag = "#DECIMAL:COMMA";
	private String numericDataFlag = "##Temp";
	private String delimiters =";\\s*|\\s+|\\n+|\\t+";
	
	private ArrayList<DataTuple> data = new ArrayList<DataTuple>();
	private DataTupleBuilder builder;
	private String identity;
	private DecimalSeparator decimalSeparator = DecimalSeparator.DOT; 
	
	private boolean metaDataLoaded;
	private boolean numericDataLoaded;
	// default decimal separator is dot
	
	public DataLoader(DataProvider data){
		fileScanner = data.getData();
		metaDataLoaded = false;
		numericDataLoaded = false;
	}
	public void loadMetaData(){
		if(!metaDataLoaded){
			for(int i =0; i<4; i++){
				fileScanner.nextLine(); //go to identity line
			}
			identity = fileScanner.nextLine().substring(identityFlag.length());
			
			//get proper decimal separator, 
			//in proteus txt files it can be either comma or dot
			if(fileScanner.nextLine().contains(commaDecimalFlag)){
				decimalSeparator = DecimalSeparator.COMMA;
			}
			metaDataLoaded = true;
		}
	}
	public void loadNumericData(){
		loadMetaData();
		if(data.isEmpty()){
			while(fileScanner.hasNextLine()){
				if(fileScanner.nextLine().startsWith(numericDataFlag)) break;
			}
			builder = new DataTupleBuilder();
			fileScanner.useDelimiter(delimiters);
			if(decimalSeparator == DecimalSeparator.COMMA)aquireCommaData();
			else aquireDotData();
			numericDataLoaded = true;
		}
	}
	
	private void aquireDotData(){
		while (fileScanner.hasNext()) {
			builder.setTemperature(Double.parseDouble(fileScanner.next()));
			builder.setTime(Double.parseDouble(fileScanner.next()));
			builder.setDsc(Double.parseDouble(fileScanner.next()));
			builder.setSensitivity(Double.parseDouble(fileScanner.next()));
			builder.setSegment(Double.parseDouble(fileScanner.next()));
			data.add(builder.buildDataTuple());
			builder.reset();
		}
	}
	private void aquireCommaData(){
		while (fileScanner.hasNext()) {
			builder.setTemperature(Double.parseDouble(commaNum(fileScanner.next())));
			builder.setTime(Double.parseDouble(commaNum(fileScanner.next())));
			builder.setDsc(Double.parseDouble(commaNum(fileScanner.next())));
			builder.setSensitivity(Double.parseDouble(commaNum(fileScanner.next())));
			builder.setSegment(Double.parseDouble(commaNum(fileScanner.next())));
			data.add(builder.buildDataTuple());
			builder.reset();
		}
	}
	private String commaNum(String in){
		return in.replace(",", ".");
	}
	//getters
	public String getIdentity(){
		loadMetaData();
		return identity;
	}
	public DecimalSeparator getDecimalSeparator(){
		loadMetaData();
		return decimalSeparator;
	}
	//flag
	public boolean isDataLoaded(){
		return !data.isEmpty();
	}
	public double dataString(){
		return data.get(data.size()-1).getTemperature();
	}
}
