package linearRegressionTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import linearRegression.LeastSquaresApprox;

public class LeastSquaresApproxTest {
LeastSquaresApprox tested = new LeastSquaresApprox();
	@Test
	public void testCalculate() {
		ArrayList<Double> x = new ArrayList<Double>();
		x.add(0.0);x.add(5.6);x.add(89.6);
		ArrayList<Double> y = new ArrayList<Double>();
		y.add(0.0);y.add(2.0);y.add(5.0);
		tested.calculate(x, y);
		System.out.println(tested);
	}

}
