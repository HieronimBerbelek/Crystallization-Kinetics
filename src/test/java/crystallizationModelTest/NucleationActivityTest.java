package crystallizationModelTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import crystallizationModel.MoModel;
import crystallizationModel.NucleationActivity;
import dataWrappers.CrystallizationData;
import dataLoader.DataLoader;
import exceptions.DataSizeException;
import exceptions.DscDataException;
import inputProvider.ProteusFileOpener;

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
			loader.loadNumericData();
			neat.add(loader.getDataObj());
			loader = new DataLoader(opener2);
			loader.loadNumericData();
			neat.add(loader.getDataObj());
			loader = new DataLoader(opener3);
			loader.loadNumericData();
			neat.add(loader.getDataObj());
			
			loader = new DataLoader(opener4);
			loader.loadNumericData();
			nucleated.add(loader.getDataObj());
			loader = new DataLoader(opener5);
			loader.loadNumericData();
			nucleated.add(loader.getDataObj());
			loader = new DataLoader(opener6);
			loader.loadNumericData();
			nucleated.add(loader.getDataObj());
			
			tested = new NucleationActivity(neat, nucleated);
			
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
