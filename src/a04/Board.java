package a04;

import java.util.Iterator;
import edu.princeton.cs.algs4.Stack;

public class Board
{
	private final int[][] board;
	private final int size;

	// construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks)
	{
		// we check if we have a square puzzle because im sure having anything
		// other than square will be difficult to solve lmao
		size = blocks.length;
		this.board = new int[size][size];
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				board[i][j] = blocks[i][j];
			}
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
		int[] $OneDBoard = OneDimentionalConversion(board);
		int counter = 1; // This checks for
		int outofplace = 0;

		for (int i = 0; i < $OneDBoard.length; i++)
		{
			if ($OneDBoard[i] == counter)
			{
				counter++;
			} else if ($OneDBoard[$OneDBoard.length - 1] == 0 && counter == $OneDBoard.length)
			{

			} else if ($OneDBoard[i] == 0)
			{
				counter++;
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
		int manhattan = 0;

		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				if (board[i][j] == 0)
				{

				} else
				{
					int positiony = board[i][j] % size;
					int positionx = board[i][j] / size;
					if (positiony == 0)
					{
						positiony = size - 1;
						--positionx;
					} else
					{
						--positiony;
					}
					manhattan += Math.abs(positionx - i) + Math.abs(positiony - j);
				}
			}
		}

		return manhattan;

	}

	// is this board the goal board?
	public boolean isGoal()
	{
		if (hamming() == 0)
			return true;
		else
			return false;
	}

	// is this board solvable?
	public boolean isSolvable()
	{
		if (size % 2 == 0)
		{
			Point zeropoint = FindIndexOfElement(0);
			if (manhattan() + zeropoint.getY() % 2 != 0)
				return true;
			else
				return false;
		} else
		{
			if (manhattan() % 2 == 0)
				return true;
			else
				return false;

		}

	}

	// does this board equal y?
	public boolean equals(Object y)
	{
		Board otherBoard = (Board) y;
		if (y == this)
		{
			return true;
		}
		if (null == y)
		{
			return false;
		}
		if (!this.getClass().equals(y.getClass()))
		{
			return false;
		}
		if (this.size() != otherBoard.size())
		{
			return false;
		}
		if (this.hamming() != otherBoard.hamming())
		{
			return false;
		}
		int[] thisClass = OneDimentionalConversion(board);
		int[] thatClass = OneDimentionalConversion(otherBoard.board);
		if (thisClass.length == thatClass.length)
		{
			boolean stillTrue = true;
			for (int i = 0; i < thisClass.length; i++)
			{
				if (i == thisClass.length - 1 && thisClass[i] == thatClass[i])
				{
					return stillTrue;
				} else if (thisClass[i] == thatClass[i])
				{
					stillTrue = true;
				} else
					return false;
			}
		}
		return false;

	}

	// string representation of this board (in the output format specified
	// below)
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append(size + "\n");
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				s.append(String.format("%2d ", board[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}
	

	// all neighboring boards
	public Iterable<Board> neighbors()
	{
		Stack<Board> stack = new Stack<Board>();

		Point zeroIndex = FindIndexOfElement(0);
		int x = zeroIndex.getX();
		int y = zeroIndex.getY();

		if (x > 0 && x <= size - 1)
		{
			Board board1 = new Board(board);
			board1.swapCels(x, y, x - 1, y);
			stack.push(board1);
		} else if (x < size - 1 && x >= 0)
		{
			Board board2 = new Board(board);
			board2.swapCels(x, y, x + 1, y);
			stack.push(board2);
		} else if (y > 0 && y <= size - 1)
		{
			Board board3 = new Board(board);
			board3.swapCels(x, y, x, y - 1);
			stack.push(board3);

		} else if (y < size - 1)
		{
			Board board4 = new Board(board);
			board4.swapCels(x, y, x, y + 1);
			stack.push(board4);
		}

		return stack;

	}

	/**
	 * This helper method helps with converting from 2d to 1d to help with
	 * checking solvability and other things, such as to string for example.
	 * 
	 * @return the 1d array of the board
	 */
	private int[] OneDimentionalConversion(int[][] board)
	{
		int[] returnable = new int[size * size];
		int counter = 0;
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{

				returnable[counter] = board[i][j];
				counter++;
			}
		}
		return returnable;
	}

	private Point FindIndexOfElement(int number)
	{
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				if (board[i][j] == number)
				{
					return new Point(i, j);
				}
			}
		}
		// Realistically, this should never happen
		return null;
	}

	/**
	 * This helper method takes two cels and swaps them according to cordinates
	 * from the parameters
	 * 
	 * @param r1
	 *            Row from First cel
	 * @param c1
	 *            Column from First cel
	 * @param r2
	 *            Row from destination cel
	 * @param c2
	 *            Column from destination cel
	 */
	private void swapCels(int r1, int c1, int r2, int c2)
	{
		if (r1 >= 0 && c1 >= 0 && r2 < board.length && c2 < board.length)
		{
			int tempCelValue = board[r1][c1];
			board[r1][c1] = board[r2][c2];
			board[r2][c2] = tempCelValue;
		} else
		{
			throw new IndexOutOfBoundsException("One of the row/column calls for some reason fell outside the board range");
		}
	}

	private int calculate1DSpot(int row, int col)
	{
		int col1 = col;
		int row1 = row;
		col1++;
		row1++;
		int returnable = (((size) * row1) - (size)) + col1;
		return returnable - 1;

	}

	private class Point
	{
		int x;
		int y;

		Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}
	}

	// unit tests (not graded)
	public static void main(String[] args)
	{

	}
}
