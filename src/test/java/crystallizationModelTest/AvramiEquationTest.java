package crystallizationModelTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import crystallization.AvramiModel;
import crystallization.results.AvramiResults;
import exceptions.DataSizeException;
import exceptions.DscDataException;
import input.ProteusFileOpener;
import loader.DataLoader;

public class AvramiEquationTest {

	@Test
	public void testMock() {
		AvramiModel tested = new AvramiModel(new MockCrystData());
		try {
			AvramiResults result = tested.calculate();
			assertEquals(5, result.getAvramiY().size());
			assertNotEquals(0, result.getCoefficient());
		} catch (DataSizeException e) {	e.printStackTrace();}		
	}
	@Test
	public void testFile(){
		String path = ".//resource//test//7,5 test data.txt";
		try {
			ProteusFileOpener opener = new ProteusFileOpener(path);
			DataLoader loader = new DataLoader(opener);
			loader.loadData();
			AvramiModel tested = new AvramiModel(loader.getDataObj());
			tested.calculate();
			
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
