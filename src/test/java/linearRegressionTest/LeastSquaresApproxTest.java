package linearRegressionTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import exceptions.DataSizeException;
import linearity.LeastSquaresApprox;

public class LeastSquaresApproxTest {
LeastSquaresApprox tested = new LeastSquaresApprox();
	@Test
	public void testCalculate() {
		ArrayList<Double> x = new ArrayList<Double>();
		x.add(0.0);x.add(2.0);x.add(76.0);
		ArrayList<Double> y = new ArrayList<Double>();
		y.add(0.0);y.add(2.0);y.add(0.0);
		try {
			tested.calculate(x, y);
		} catch (DataSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testInterpole(){
		assertEquals(15, tested.interpole(4, 2, 10, 6, 20), 0.1);
	}

}
