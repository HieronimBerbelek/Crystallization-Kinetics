package dataDSCTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.DscDataException;
import input.ProteusFileOpener;
import loader.DataLoader;
import wrappers.ExperimentalData;
import wrappers.CrystallizationMode;

public class CrystExpDataTest {
	static ProteusFileOpener opener;
	static DataLoader loader;
	static ExperimentalData tested;
	static String path = ".//resource//test//7,5 test data.txt";
	
	@BeforeClass
	public static void setUpBeforeClass(){
		try {
			opener = new ProteusFileOpener(path);
			loader = new DataLoader(opener);
			loader.loadData();
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
		assertEquals(7.5, tested.getCoolingRate(), 0.001);
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
		int numOfPoints =28;
		assertEquals(numOfPoints, tested.size());
		
		double lowest = tested.getRelativeX().get(0);
		double largest = tested.getRelativeX().get(numOfPoints-1);
		for(int index=1; index<tested.size();index++){
			if(tested.getRelativeX().get(index)>largest
					||tested.getRelativeX().get(index)<lowest) fail("DATA ERROR");
		}
		System.out.println(tested.getRelativeTime());
		System.out.println(tested.getRelativeX());
	}
}
