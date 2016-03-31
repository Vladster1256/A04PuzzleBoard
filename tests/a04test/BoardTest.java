package a04test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a04.Board;

public class BoardTest
{
	int[][] testboard = {{1,2,3},{4,5,6},{7,8,0}};
	Board b1 = new Board(testboard);
	
	@Before
	public void setUp() throws Exception
	{
		
	}

	@Test
	public void testBoard()
	{
		Board b2 = new Board(testboard);
		assertTrue(b2 != null);
	}
	
	@Test
	public void testIsGoalBoard()
	{
		assertTrue(b1.isGoal());
	}
	
	/**
	 * This test case was to test that the 1d conversion is accurate.
	 * The only reason why this is commented out is that the 1d conversion method is a private method,
	 * and must be private for public api requirements
	 */
//	@Test
//	public void test1DConversion()
//	{
//		int[] flat1 = b1.OneDimentionalConversion();
//		String flat1conversion = toString(flat1);
//		int[] testflat1 = {1,2,3,4,5,6,7,8,0};
//		String testflat1conversion = toString(testflat1);
//		assertTrue(flat1conversion.equals(testflat1conversion));
//	}
	
	@Test
	public void testSize()
	{
		assertTrue(b1.size() == 3);
	}
	
	@Test
	public void testHamming()
	{
		int i = b1.hamming();
		assertTrue(i == 0);
	}
	
	public String toString(int[] array)
	{
		String returnable = "";
		for(int item: array)
		{
			returnable +=item + " ";
		}
		return returnable;
	}

}
