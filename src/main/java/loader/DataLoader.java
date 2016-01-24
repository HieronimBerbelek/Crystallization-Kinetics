package loader;

import java.util.ArrayList;
import java.util.Scanner;

import exceptions.DscDataException;
import input.DataProvider;
import wrappers.ExperimentalData;
import wrappers.DataTuple;
import wrappers.DataTupleBuilder;
/* Class which loads data from proteus export txt file to data object
 * is there need and way to divide loadMetaData and loadNumericData into shorter methods?
 * TO DO make it more robust, improve input check
 */
public class DataLoader {
	private Scanner fileScanner;
	//flags for checking dsc data file
	private final static String exportFlag = "#EXPORTTYPE:DATA SINGLE";
	private final static String fileFlag = "#FILE:";
	private final static String formatFlag = "#FORMAT:";
	private final static String fTypeFlag = "#FTYPE:";
	private final static String identityFlag = "#IDENTITY:";
	
	private final static String commaDecimalFlag = "#DECIMAL:COMMA";
	private final static String numericDataFlag = "##Temp";
	private final static String delimiters =";\\s*|\\s+|\\n+|\\t+";
	
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
	public void loadMetaData() throws DscDataException{
		if(!metaDataLoaded){
			//NEGATION! of checkHead
			if(!checkHead()) throw new DscDataException();
			//getting identity
			String id = fileScanner.nextLine();
			if(id.startsWith(identityFlag)){
				identity = id.substring(identityFlag.length());
			}
			else throw new DscDataException();
			
			//get proper decimal separator, 
			//in proteus txt files it can be either comma or dot
			if(fileScanner.nextLine().contains(commaDecimalFlag)){
				decimalSeparator = DecimalSeparator.COMMA;
			}
			metaDataLoaded = true;
		}
	}
	public void loadData() throws DscDataException{
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
	private boolean checkHead() throws DscDataException{
		boolean toReturn;
		if (fileScanner.hasNextLine()){
			toReturn= fileScanner.nextLine().startsWith(exportFlag)
					&&fileScanner.nextLine().startsWith(fileFlag)
					&&fileScanner.nextLine().startsWith(formatFlag)
					&&fileScanner.nextLine().startsWith(fTypeFlag);
		}
		else throw new DscDataException();
		return toReturn;
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
	public String getIdentity() throws DscDataException{
		loadMetaData();
		return identity;
	}
	public DecimalSeparator getDecimalSeparator() throws DscDataException{
		loadMetaData();
		return decimalSeparator;
	}
	//flag
	public boolean isDataLoaded(){
		return !data.isEmpty();
	}
	public ExperimentalData getDataObj(){
		return new ExperimentalData(data, identity);
	}
}
