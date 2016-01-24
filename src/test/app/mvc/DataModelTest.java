package mvc;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import crystallizationModelTest.MockCrystData;
import model.DataModel;

public class DataModelTest {
	MockCrystData mockData = new MockCrystData();
	DataModel tested=new DataModel();
	
	@Before
	public void setUp(){
		mockData = new MockCrystData();
		tested = new DataModel();
	}
	@Test
	public void testContains() {
		tested.add(mockData);
		assertEquals(true, tested.contains(mockData));
		assertEquals(false, tested.contains("asdasd"));
	}

}
