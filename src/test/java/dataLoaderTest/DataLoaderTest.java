package dataLoaderTest;

import static org.junit.Assert.*;
import org.junit.Before;
import java.io.IOException;
import org.junit.Test;

import dataLoader.DataLoader;
import dataLoader.DecimalSeparator;
import inputProvider.ProteusFileOpener;

public class DataLoaderTest {
	
	static ProteusFileOpener testedOp;
	static DataLoader tested;
	
	@Before 
	public void setUp(){
		try {
			testedOp = new ProteusFileOpener("//Crystallization-Kinetics//resource//test//ExpDat_AP52DE55 PURE 10K.txt");
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
	public void testLoadMetaData() {
				
	}
	@Test
	public void testLoadNumericData(){
		assertTrue(tested.isDataLoaded());
	}
	@Test
	public void testGetDataObj(){
	}
}
