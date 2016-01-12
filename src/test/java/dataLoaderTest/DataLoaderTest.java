package dataLoaderTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import dataLoader.DataLoader;
import dataLoader.DecimalSeparator;
import inputProvider.ProteusFileOpener;

public class DataLoaderTest {
	static ProteusFileOpener testedOp;
	static DataLoader tested;
	
	@BeforeClass
	public static void setUpBeforeClass(){
		try {
			testedOp = new ProteusFileOpener("D:\\Paw³a\\studia\\dyplom nanokompozyty grafenowe\\POMIARY\\2% 5 peak.txt");
			tested = new DataLoader(testedOp);
			tested.loadNumericData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testDataLoader() {
		assertEquals("52DE55_2%GNP", tested.getIdentity());
		assertEquals(DecimalSeparator.COMMA, tested.getDecimalSeparator());
	}
	@Test
	public void testLoadNumericData(){
		assertTrue(tested.isDataLoaded());
	}
	@Test
	public void testGetDataObj(){
		
	}
}
