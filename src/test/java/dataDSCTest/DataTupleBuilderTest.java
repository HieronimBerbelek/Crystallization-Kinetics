package dataDSCTest;

import static org.junit.Assert.*;

import org.junit.Test;

import dataWrappers.DataTupleBuilder;
import exceptions.DscDataException;
//not testing basic setters and getters
public class DataTupleBuilderTest {
	@Test
	public void testDataTupleBuilder(){
		DataTupleBuilder tested = new DataTupleBuilder();
		tested.setTime(3232);
		tested.setDsc(233);
		tested.setSegment(33);
		try{
			tested.buildDataTuple();
			fail();
		} catch(DscDataException e){assertTrue(true);}
		
		tested.reset();
		assertEquals(0.0, tested.getTime(), 0.001);
		
		try{
			tested.buildDataTuple();
			fail();
		} catch(DscDataException e){assertTrue(true);}
		tested.setTime(22);
		tested.setDsc(3);
		tested.setTemperature(44);
		tested.setSensitivity(22);
		tested.setSegment(333);
		try{
			tested.buildDataTuple();
			assertTrue(true);
		} catch(DscDataException e){fail();}
		
	}
}
