package dataDSCTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import dataLoader.DataLoader;
import dataWrappers.CrystallizationMode;
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
	public void testCoolingRate(){
		assertEquals(10.0, tested.getCoolingRate(), 0.001);
		assertEquals(CrystallizationMode.NONISOTHERMAL, tested.getMode());
	}
	@Test
	public void testComments(){
		String test = "asjd aisjd oa joaijd ";
		tested.putComment(test);
		assertEquals(test, tested.getComments());
	}
	@Test
	public void testData(){
		int numOfPoints =20;
		assertEquals(numOfPoints, tested.getRelativeX().size());
		
		double lowest = tested.getRelativeX().get(0);
		double largest = tested.getRelativeX().get(numOfPoints-1);
		for(int index=1; index<numOfPoints;index++){
			if(tested.getRelativeX().get(index)>largest
					||tested.getRelativeX().get(index)<lowest) fail("logic error");
		}
	}
}
