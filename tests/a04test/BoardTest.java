package a04test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import a04.Board;
import edu.princeton.cs.algs4.In;

public class BoardTest
{

	
	public void setUp()
	{
		
	}
	
	public int[][] setUpHardBoard()
	{
		In in = new In("./src/txt/puzzle36.txt");
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		int[][] intarray = new int[N][N];
		return intarray;

	}
	
	
	private final Board board1 = new Board(new int[][] { { 0 } });
	private final Board board2 = new Board(new int[][] { { 2, 0 }, { 1, 3 } });
	private final Board board3 = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } });
	private final Board goalBoard = new Board(new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 0 } });
	private final Board hardboard = new Board(setUpHardBoard());
	private final Board board4 = new Board(new int[][] { { 0, 2 }, { 1, 3 } });

	@Test
	public void testBoard_immutable()
	{
		int[][] boardArray = { { 8, 1, 3 }, { 4, 2, 0 }, { 7, 6, 5 } };
		Board immutableBoard = new Board(boardArray);
		boardArray[1][2] = 2;
		assertEquals("Class Board is not immutable", "3\n 8  1  3 \n 4  2  0 \n 7  6  5 \n", immutableBoard.toString());
	}

	@Test
	public void testSize()
	{
		assertEquals(2, board2.size());
		assertEquals(1, board1.size());
		assertEquals(3, board3.size());
		assertEquals(4, goalBoard.size());
	}

	@Test
	public void testHamming()
	{
		assertEquals(0, board1.hamming());
		assertEquals(3, board2.hamming());
		assertEquals(5, board3.hamming());
		assertEquals(0, goalBoard.hamming());

		Board offByOneBoard = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } });
		assertEquals(1, offByOneBoard.hamming());
	}

	@Test
	public void testManhattan()
	{
		assertEquals(0, board1.manhattan());
		assertEquals(3, board2.manhattan());
		assertEquals(10, board3.manhattan());
		assertEquals(0, goalBoard.manhattan());

		Board offByOneBoard = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } });
		assertEquals(1, offByOneBoard.manhattan());
	}

	@Test
	public void testIsGoalTrue()
	{
		assertEquals(true, board1.isGoal());
		assertEquals(true, goalBoard.isGoal());
	}

	@Test
	public void testIsGoalFalse()
	{
		assertEquals(false, board2.isGoal());
		assertEquals(false, board3.isGoal());
	}

	@Test
	public void testIsSolvableTrue()
	{
		assertEquals(true, board3.isSolvable());
		assertEquals(true, goalBoard.isSolvable());
		assertEquals(true, board2.isSolvable());
		assertEquals(true, board1.isSolvable());
	}
	
	@Test
	public void testIsHardBoardSolvable()
	{
		assertEquals(true,hardboard.isSolvable());
	}

	@Test
	public void testIsSolvableFalse()
	{
		Board notSolvable3 = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 8, 7, 0 } });
		assertEquals(false, notSolvable3.isSolvable());

		Board notSolvable4 = new Board(new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 15, 14, 0 } });
		assertEquals(false, notSolvable4.isSolvable());
	}

	@Test
	public void testEqualsTrue()
	{
		Board boardThree = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } });
		assertEquals(true, board3.equals(boardThree));
		assertEquals(true, boardThree.equals(board3));
	}

	@Test
	public void testEqualsFalse()
	{
		Board otherBoard3 = new Board(new int[][] { { 8, 1, 3 }, { 4, 6, 2 }, { 7, 0, 5 } });
		assertEquals(false, board3.equals(otherBoard3));
		assertEquals(false, otherBoard3.equals(board3));
	}

	@Test
	public void testBoard1Neighbors()
	{
		Iterable<Board> neighbors = board1.neighbors();
		assertEquals(0, count(neighbors));
	}

	@Test
	public void testBoard2Neighbors()
	{
		Board[] board2Neighbors = { new Board(new int[][] { { 0, 2 }, { 1, 3 } }), new Board(new int[][] { { 2, 3 }, { 1, 0 } }) };
		Iterable<Board> neighbors = board2.neighbors();

		assertEquals(board2Neighbors.length, count(neighbors)); // check number
																// of neighbors

		for (Board board : neighbors)
		{
			// use contains because neighbors can be listed in arbitrary order
			if (!Arrays.asList(board2Neighbors).contains(board))
				fail(board.toString() + " is not a neighbor of " + board2.toString());
		}
	}

	@Test
	public void testBoard3Neighbors()
	{
		Board[] board3Neighbors = { new Board(new int[][] { { 8, 1, 3 }, { 4, 2, 0 }, { 7, 6, 5 } }), new Board(new int[][] { { 8, 1, 3 }, { 0, 4, 2 }, { 7, 6, 5 } }), new Board(new int[][] { { 8, 1, 3 }, { 4, 6, 2 }, { 7, 0, 5 } }),
				new Board(new int[][] { { 8, 0, 3 }, { 4, 1, 2 }, { 7, 6, 5 } }) };
		Iterable<Board> neighbors = board3.neighbors();

		assertEquals(board3Neighbors.length, count(neighbors)); // check number
																// of neighbors

		for (Board board : neighbors)
		{
			// use contains because neighbors can be listed in arbitrary order
			if (!Arrays.asList(board3Neighbors).contains(board))
				fail(board.toString() + " is not a neighbor of " + board3.toString());
		}
	}

	@Test
	public void testGoalBoardNeighbors()
	{
		Board[] boardGoal = { new Board(new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 0 }, { 13, 14, 15, 12 } }), new Board(new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 0, 15 } }) };
		Iterable<Board> neighbors = goalBoard.neighbors();

		assertEquals(boardGoal.length, count(neighbors));

		for (Board board : neighbors)
		{
			if (!Arrays.asList(boardGoal).contains(board))
				fail(board.toString() + " is not a neighbor of " + goalBoard.toString());
		}
	}

	@Test
	public void testToString()
	{
		assertEquals("1\n 0 \n", board1.toString());
		assertEquals("2\n 2  0 \n 1  3 \n", board2.toString());
		assertEquals("3\n 8  1  3 \n 4  0  2 \n 7  6  5 \n", board3.toString());
		assertEquals("4\n 1  2  3  4 \n 5  6  7  8 \n 9 10 11 12 \n13 14 15  0 \n", goalBoard.toString());
	}

//	@Test
//	public void testFindIndex()
//	{
//		//assertEquals("0 1", board2.FindIndexOfElement(0).toString());
//		assertEquals("0 0", board4.FindIndexOfElement(0).toString());
//
//	}

	// - - - helper methods
	private int count(Iterable<Board> boards)
	{
		int count = 0;
		for (Board b : boards)
			count++;

		return count;
	}
}
