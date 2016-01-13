package dataDSCTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import dataLoader.DataLoader;
import dataWrappers.DataCrystal;
import exceptions.DscDataException;
import inputProvider.ProteusFileOpener;

public class DataCrystalTest {
	static ProteusFileOpener opener;
	static DataLoader loader;
	static DataCrystal tested;
	static String path = ".//resource//test//input file.txt";
	
	@BeforeClass
	public static void setUpBeforeClass(){
		try {
			opener = new ProteusFileOpener(path);
			loader = new DataLoader(opener);
			loader.loadNumericData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DscDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		tested = loader.getDataObj();
	}
	@Test
	public void test(){
		assertEquals(10.0, tested.getCoolingRate(), 0.001);
		System.out.println(tested.getRelativeX());
		System.out.println(tested.getSummaricHeat());
		System.out.println(tested.getPeakTemperature());
	}

}
