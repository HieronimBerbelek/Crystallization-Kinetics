package dataLoaderTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import dataLoader.DataLoader;
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
			assertEquals(tested.getIdentity(), "52DE55_2%GNP");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testLoadNumericData(){
		ProteusFileOpener testedOp;
		try {
			testedOp = new ProteusFileOpener("D:\\Paw³a\\studia\\dyplom nanokompozyty grafenowe\\POMIARY\\2% 5 peak.txt");
			DataLoader tested = new DataLoader(testedOp);
			tested.loadNumericData();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
