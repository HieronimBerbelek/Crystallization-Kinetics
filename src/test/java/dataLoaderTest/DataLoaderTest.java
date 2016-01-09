package dataLoaderTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import dataLoader.DataLoader;
import dataLoader.DecimalSeparator;
import inputProvider.ProteusFileOpener;

public class DataLoaderTest {

	@Test
	public void testDataLoader() {
	}

	@Test
	public void testLoadMetaData() {
		ProteusFileOpener testedOp;
		try {
			testedOp = new ProteusFileOpener("D:\\Paw³a\\studia\\dyplom nanokompozyty grafenowe\\POMIARY\\2% 5 peak.txt");
			DataLoader tested = new DataLoader(testedOp);
			tested.loadMetaData();
			assertEquals("52DE55_2%GNP", tested.getIdentity());
			assertEquals(DecimalSeparator.COMMA, tested.getDecimalSeparator());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
