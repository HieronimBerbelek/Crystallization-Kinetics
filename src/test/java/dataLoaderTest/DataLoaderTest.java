package dataLoaderTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import dataLoader.DataLoader;
import dataLoader.DecimalSeparator;
import exceptions.DscDataException;
import inputProvider.ProteusFileOpener;

public class DataLoaderTest {
	static ProteusFileOpener testedOpener;
	static DataLoader tested;
	static String path = ".//resource//test//input file.txt";
	
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
