package crystallizationModelTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import crystallizationModel.AvramiModel;
import crystallizationModel.OzawaModel;
import dataLoader.DataLoader;
import exceptions.DataSizeException;
import exceptions.DscDataException;
import inputProvider.ProteusFileOpener;

public class OzawaModelTest {
	OzawaModel tested;
	@Test
	public void testThreeFiles() {
		String path1 = ".//resource//test//test PURE 5K.txt";
		String path2 = ".//resource//test//test PURE 7,5K.txt";
		String path3 = ".//resource//test//test PURE 10K.txt";
		try {
			ProteusFileOpener opener1 = new ProteusFileOpener(path1);
			ProteusFileOpener opener2 = new ProteusFileOpener(path2);
			ProteusFileOpener opener3 = new ProteusFileOpener(path3);
			
			DataLoader loader = new DataLoader(opener1);
			loader.loadNumericData();
			tested = new OzawaModel(loader.getDataObj());
			
			loader = new DataLoader(opener2);
			loader.loadNumericData();
			tested.putData(loader.getDataObj());
			
			loader = new DataLoader(opener3);
			loader.loadNumericData();
			tested.putData(loader.getDataObj());
			
			tested.calculate();
			limitsAssertions();
			
			tested.printPlot();
			System.out.println(tested.getAvgSlope());
			System.out.println(tested.getAvgIntercept());
			System.out.println(tested.getAvgCertainity());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DscDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testOneFile(){
		String path1 = ".//resource//test//test PURE 10K.txt";
		String path2 = ".//resource//test//test PURE 5K.txt";
		String path3 = ".//resource//test//test PURE 7,5K.txt";
		try {
			ProteusFileOpener opener = new ProteusFileOpener(path2);
			
			DataLoader loader = new DataLoader(opener);
			loader.loadNumericData();
			tested = new OzawaModel(loader.getDataObj());
			
			tested.setLowerLimit(0.0);
			tested.setUpperlimit(1.0);
			tested.calculate();
			limitsAssertions();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DscDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void limitsAssertions(){
		assertTrue(Double.isFinite(tested.getLowerTempLimit()));
		assertTrue(Double.isFinite(tested.getUpperTempLimit()));
	}

}
