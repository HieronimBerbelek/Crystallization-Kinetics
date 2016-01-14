package crystallizationModelTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import crystallizationModel.AvramiEquation;
import dataLoader.DataLoader;
import exceptions.DataSizeException;
import exceptions.DscDataException;
import inputProvider.ProteusFileOpener;

public class AvramiEquationTest {

	@Test
	public void testMock() {
		AvramiEquation tested = new AvramiEquation(new MockCrystData());
		try {
			tested.calculate();
		} catch (DataSizeException e) {	e.printStackTrace();}
		assertEquals(5, tested.getAvramiY().size());
		//checks for null pointer exception too
		assertNotEquals(0, tested.getCoefficient());
	}
	@Test
	public void testFile(){
		String path = ".//resource//test//7,5 test data.txt";
		try {
			ProteusFileOpener opener = new ProteusFileOpener(path);
			DataLoader loader = new DataLoader(opener);
			loader.loadNumericData();
			AvramiEquation tested = new AvramiEquation(loader.getDataObj());
			tested.calculate();
			//System.out.println(tested.getLogTime());	
			System.out.println(tested);		
			
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
}
