package crystallizationModelTest;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import crystallization.NucleationActivity;
import crystallization.results.NucleationResults;
import exceptions.DataSizeException;
import exceptions.DscDataException;
import input.ProteusFileOpener;
import loader.DataLoader;
import wrappers.CrystallizationData;

public class NucleationActivityTest {
	private NucleationActivity tested;
	@Test
	public void test() {
		String path1 = ".//resource//test//test PURE 5K.txt";
		String path2 = ".//resource//test//test PURE 7,5K.txt";
		String path3 = ".//resource//test//test PURE 10K.txt";
		String path4 = ".//resource//test//test 1%TRG 5K.txt";
		String path5 = ".//resource//test//test 1%TRG 7,5K.txt";
		String path6 = ".//resource//test//test 1%TRG 10K.txt";
		try {
			ProteusFileOpener opener1 = new ProteusFileOpener(path1);
			ProteusFileOpener opener2 = new ProteusFileOpener(path2);
			ProteusFileOpener opener3 = new ProteusFileOpener(path3);
			ProteusFileOpener opener4 = new ProteusFileOpener(path4);
			ProteusFileOpener opener5 = new ProteusFileOpener(path5);
			ProteusFileOpener opener6 = new ProteusFileOpener(path6);
			
			ArrayList<CrystallizationData> neat= 
					new ArrayList<CrystallizationData>();
			ArrayList<CrystallizationData> nucleated= 
					new ArrayList<CrystallizationData>();
			
			DataLoader loader = new DataLoader(opener1);
			loader.loadData();
			neat.add(loader.getDataObj());
			loader = new DataLoader(opener2);
			loader.loadData();
			neat.add(loader.getDataObj());
			loader = new DataLoader(opener3);
			loader.loadData();
			neat.add(loader.getDataObj());
			
			loader = new DataLoader(opener4);
			loader.loadData();
			nucleated.add(loader.getDataObj());
			loader = new DataLoader(opener5);
			loader.loadData();
			nucleated.add(loader.getDataObj());
			loader = new DataLoader(opener6);
			loader.loadData();
			nucleated.add(loader.getDataObj());
			
			tested = new NucleationActivity(neat, nucleated);
			NucleationResults result = tested.calculate(220, 222.5);
			System.out.println(result.getActivity());
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
