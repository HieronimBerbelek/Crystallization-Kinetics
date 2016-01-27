package crystallizationModelTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import crystallization.AvramiModel;

public class CrystallizationModelTest {
	AvramiModel tested;
	@Before
	public void setUp(){
		tested = new AvramiModel(new MockCrystData());
	}
	@Test
	public void test() {
		tested.setLowerLimit(0.1);
		tested.setUpperlimit(0.8);
		assertEquals(true, tested.isInBounds(0.5));
		assertEquals(true, tested.isAboveUpperLimit(0.9));
		assertEquals(true, tested.isBelowLowerLimit(0.03));
	}

}
