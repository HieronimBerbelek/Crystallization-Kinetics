package dataLoader;

import java.util.ArrayList;
import java.util.Scanner;

import dataWrappers.CrystExpData;
import dataWrappers.DataTuple;
import dataWrappers.DataTupleBuilder;
import exceptions.DscDataException;
import inputProvider.DataProvider;
/*is there need and way to divide loadMetaData and loadNumericData into shorter methods?
 * TO DO make it more robust, improve input check
 */
public class DataLoader {
	private Scanner fileScanner;
	
	private static String identityFlag = "#IDENTITY:";
	private static String commaDecimalFlag = "#DECIMAL:COMMA";
	private static String numericDataFlag = "##Temp";
	private static String delimiters =";\\s*|\\s+|\\n+|\\t+";
	
	private ArrayList<DataTuple> data = new ArrayList<DataTuple>();
	private DataTupleBuilder builder;
	private String identity;
	private DecimalSeparator decimalSeparator = DecimalSeparator.DOT; 
	
	private boolean metaDataLoaded;
	// default decimal separator is dot
	
	public DataLoader(DataProvider data){
		fileScanner = data.getData();
		metaDataLoaded = false;
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
	public void loadNumericData() throws DscDataException{
		loadMetaData();
		if(data.isEmpty()){
			while(fileScanner.hasNextLine()){
				if(fileScanner.nextLine().startsWith(numericDataFlag)) break;
			}
			builder = new DataTupleBuilder();
			fileScanner.useDelimiter(delimiters);
			if(decimalSeparator == DecimalSeparator.COMMA)aquireCommaData();
			else aquireDotData();
		}
	}
	
	private void aquireDotData() throws DscDataException{
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
	private void aquireCommaData() throws DscDataException{ //using commaNum, to replace commas to dots
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
	public CrystExpData getDataObj(){
		return new CrystExpData(data, identity);
	}
}
