package a04;

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
			size = board.length;
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
		return 0;

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
		int counter = 0;
		boolean stillgood = true;
		do
		{
			if ($OneDBoard[counter] == counter)
			{
				stillgood = true;
				counter ++;
			} else
			{
				stillgood = false;
			}
		} while (stillgood == true);
		return stillgood;
	}

	// is this board solvable?
	public static boolean isSolvable()
	{
		return false;

	}

	// does this board equal y?
	public boolean equals(Object y)
	{
		return y.equals(this);

	}

	// all neighboring boards
	public Iterable<Board> neighbors()
	{
		return null;

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

	// unit tests (not graded)
	public static void main(String[] args)
	{

	}
}
