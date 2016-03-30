package a04;

import java.util.Iterator;

import edu.princeton.cs.algs4.Queue;

public class Board
{
	private int[][] board;
	private int size;

	// construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks)
	{
		// we check if we have a square puzzle because im sure having anything
		// other than square will be difficult to solve lmao.
		if (blocks[0].length == blocks.length)
		{
			board = blocks;
			size = blocks.length;
		}

	}

	// board size N
	public int size()
	{
		return size;

	}

	// number of blocks out of place
	public int hamming()
	{
		int[] $OneDBoard = TwoDimentionalConversion();
		int counter = 1;
		int outofplace = 0;

		for (int i = 0; i < $OneDBoard.length; i++)
		{
			if ($OneDBoard[i] == counter)
			{
				counter++;
			} else if ($OneDBoard[$OneDBoard.length - 1] == 0 && counter == $OneDBoard.length)
			{

			} else
			{
				counter++;
				outofplace++;
			}
		}
		return outofplace;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan()
	{
		return 0;

	}

	// is this board the goal board?
	public boolean isGoal()
	{
		int[] $OneDBoard = TwoDimentionalConversion();
		int counter = 1;
		boolean stillgood = true;

		for (int i = 0; i < $OneDBoard.length; i++)
		{
			if ($OneDBoard[i] == counter)
			{
				stillgood = true;
				counter++;
			} else if ($OneDBoard[$OneDBoard.length - 1] == 0 && counter == $OneDBoard.length)
			{
				stillgood = true;
				return stillgood;
			} else
			{
				stillgood = false;
				return stillgood;
			}
		}
		return stillgood;
	}

	// is this board solvable?
	public boolean isSolvable()
	{
		return false;

	}

	// does this board equal y?
	public boolean equals(Object y)
	{
		return this.hashCode() == y.hashCode();

	}

	// all neighboring boards
	public Iterable<Board> neighbors()
	{
		return new NeighborIterator();

	}

	// string representation of this board (in the output format specified
	// below)
	public String toString()
	{
		String returnable = size + " \n";

		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				returnable += board[i][j] + " ";
			}
			returnable += "\n";
		}

		return returnable;
	}

	private class NeighborIterator implements Iterable<Board>
	{

		@Override
		public Iterator<Board> iterator()
		{
			Queue<Board> queue = new Queue<Board>();
			
			
			
			return (Iterator<Board>) queue;
		}

	}

	/**
	 * This helper method helps with converting from 2d to 1d to help with
	 * checking solvability and other things, such as to string for example.
	 * 
	 * @return the 1d array of the board
	 */
	private int[] TwoDimentionalConversion()
	{
		int[] returnable = new int[size * size];
		int counter = 0;
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				returnable[counter] = board[i][j];
			}
		}
		return returnable;
	}

	/**
	 * This helper method takes two cels and swaps them according to cordinates from the parameters
	 * @param r1 Row from First cel
	 * @param c1 Column from First cel
	 * @param r2 Row from destination cel
	 * @param c2 Column from destination cel
	 */
	private void swapCels(int r1, int c1, int r2, int c2)
	{
		if(r1 >= 0 && c1 >= 0 && r2 < board.length && c2 < board.length)
		{
			int tempCelValue = board[r1][c1];
			board[r1][c1] = board[r2][c2];
			board[r2][c2] = tempCelValue;
		}
		else
		{
			throw new IndexOutOfBoundsException("One of the row/column calls for some reason fell outside the board range");
		}
	}

	// unit tests (not graded)
	public static void main(String[] args)
	{

	}
}
