package dataLoaderTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import dataLoader.DataLoader;
import dataLoader.DecimalSeparator;
import dataWrappers.CrystExpData;
import exceptions.DscDataException;
import inputProvider.ProteusFileOpener;

public class DataLoaderTest {
	//setting up fields for correct dsc file
	static ProteusFileOpener testedOpener;
	static DataLoader tested;
	static String path = ".//resource//test//input file.txt";
	//setting up fields for corrupted dsc file
	static ProteusFileOpener testedCorruptedOpener;
	static DataLoader testedCorrupted;
	static String pathCorrupted = ".//resource//test//corrupted input file.txt";
	
	@BeforeClass
	public static void setUpBeforeClass(){
		try {
			testedOpener = new ProteusFileOpener(path);
			tested = new DataLoader(testedOpener);
			tested.loadData();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DscDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testDataLoader() {
		try {
			assertEquals("AP52DE55 PURE 10K/min", tested.getIdentity());
			assertEquals(DecimalSeparator.COMMA, tested.getDecimalSeparator());
		} catch (DscDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testLoadNumericData(){
		assertTrue(tested.isDataLoaded());
	}
	@Test
	public void testGetDataObj(){
		//it's partly testing CrystExpData too
		String testString = "its just test stuff";
		CrystExpData result = tested.getDataObj();
		assertTrue(result.getRelativeTime().size()>0);
		assertTrue(result.getCoolingRate()>0);
		
		result.putComment(testString);
		assertEquals(testString, result.getComments());
	}
	@Test
	public void testCorrupted(){
		try {
			testedCorruptedOpener = new ProteusFileOpener(pathCorrupted);
			testedCorrupted = new DataLoader(testedCorruptedOpener);
			testedCorrupted.loadData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DscDataException e) {
			return;
		}
		fail("corrupted file not failed!?");
	}
}
