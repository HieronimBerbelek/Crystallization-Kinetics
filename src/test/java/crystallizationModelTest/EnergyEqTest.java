package crystallizationModelTest;

import java.io.IOException;

import org.junit.Test;

import crystallization.EnergyEq;
import crystallization.results.EnergyEqResults;
import exceptions.DataSizeException;
import exceptions.DscDataException;
import input.ProteusFileOpener;
import loader.DataLoader;

public class EnergyEqTest {
	EnergyEq tested;
	@Test
	public void test() {
		String path1 = ".//resource//test//test PURE 7,5K.txt";
		String path2 = ".//resource//test//test PURE 5K.txt";
		String path3 = ".//resource//test//test PURE 10K.txt";
		try {
			ProteusFileOpener opener1 = new ProteusFileOpener(path1);
			ProteusFileOpener opener2 = new ProteusFileOpener(path2);
			ProteusFileOpener opener3 = new ProteusFileOpener(path3);
			
			DataLoader loader = new DataLoader(opener1);
			loader.loadData();
			tested = new EnergyEq(loader.getDataObj());
			
			loader = new DataLoader(opener2);
			loader.loadData();
			tested.putData(loader.getDataObj());
			
			loader = new DataLoader(opener3);
			loader.loadData();
			tested.putData(loader.getDataObj());
			EnergyEqResults results = tested.calculate();
			System.out.println(results.getEnergyBarriers());
			System.out.println(results.getCertainities());
			System.out.println(results.getAvgCertainity());	
				
			
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
