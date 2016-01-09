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
	Scanner fileScanner;
	
	String identityFlag = "#IDENTITY:";
	String commaDecimalFlag = "#DECIMAL:COMMA";
	String numericDataFlag = "##Temp";
	String delimiters =";|;\\s+|\\n";
	
	ArrayList<DataTuple> data = new ArrayList<DataTuple>();
	DataTupleBuilder builder;
	boolean metaDataLoaded;
	String identity;
	DecimalSeparator decimalSeparator = DecimalSeparator.DOT; 
	// default decimal separator is dot
	
	public DataLoader(DataProvider data){
		fileScanner = data.getData();
		metaDataLoaded = false;
	}
	public void loadMetaData(){
		for(int i =0; i<5; i++){
			fileScanner.nextLine(); //go to identity line
		}
		identity = fileScanner.nextLine().substring(identityFlag.length());
		
		//get proper decimal separator, 
		//in proteus txt files it can be either comma or dot
		if(fileScanner.nextLine()==commaDecimalFlag){
			fileScanner.useLocale(Locale.ENGLISH);
			decimalSeparator = DecimalSeparator.COMMA;
		}
		metaDataLoaded = true;
	}
	public void loadNumericData(){
		if(identity==null){
			loadMetaData();
		}
		if(data.isEmpty()){
			while(fileScanner.hasNextLine()){
				if(fileScanner.nextLine().startsWith(numericDataFlag)) break;
			}
			fileScanner.useDelimiter(delimiters);
			aquireData();
		}
	}
	
	private void aquireData(){
		while (fileScanner.hasNextLine()) {
			builder.setTemperature(Double.parseDouble(fileScanner.next()));
			builder.setTime(Double.parseDouble(fileScanner.next()));
			builder.setDsc(Double.parseDouble(fileScanner.next()));
			builder.setSensitivity(Double.parseDouble(fileScanner.next()));
			builder.setSegment(Double.parseDouble(fileScanner.next()));
		}
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
}
