package a04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

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
		int[] test = OneDimentionalZeroRemover(OneDimentionalConversion(board));
		int inversions = 0;
		Point zeropoint = FindIndexOfElement(0);
		int zerorow = zeropoint.getRow();

		if (size % 2 == 0)
		{
			for (int i = 0; i < test.length; i++)
			{
				for (int j = i + 1; j < test.length; j++)
				{
					if (test[i] > test[j])
					{
						inversions++;
						
					}
				}
			}
			//StdOut.print(inversions + zerorow);
			if (inversions + zerorow % 2 != 0)
				return true;
			else
				return false;
		} else
		{
			for (int i = 0; i < test.length; i++)
			{
				for (int j = i + 1; j < test.length; j++)
				{
					if (test[i] > test[j])
					{
						inversions++;
						
					}
				}
			}
			//StdOut.print(inversions);
			if (inversions % 2 == 0)
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
		// System.out.print(toString());
		//System.out.println("Point:" + zeroIndex.toString());
		int row = zeroIndex.getRow();
		int col = zeroIndex.getCol();

		if (row > 0)
		{
			final int[][] board1 = swap(board, row, col, row - 1, col);
			stack.push(new Board(board1));
		}
		if (row < size - 1)
		{
			final int[][] board2 = swap(board, row, col, row + 1, col);
			stack.push(new Board(board2));
		}
		if (col > 0)
		{
			final int[][] board3 = swap(board, row, col, row, col - 1);
			stack.push(new Board(board3));
		}
		if (col < size - 1)
		{
			final int[][] board4 = swap(board, row, col, row, col + 1);
			stack.push(new Board(board4));
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

	private int[] OneDimentionalZeroRemover(int[] board)
	{
		int[] returnable = new int[board.length - 1];
		int counter = 0;
		for (int i = 0; i < board.length; i++)
		{
			if (board[i] == 0)
			{

			} else
			{
				returnable[counter] = board[i];
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
				if (board[j][i] == number)
				{
					return new Point(j, i);
				}
			}
		}
		// Realistically, this should never happen
		return null;
	}

	/**
	 * This helper method takes two cels, creates a new 2d array, and swaps the
	 * cels on new 2d array according to cordinates from the parameters
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
	private int[][] swap(int[][] grid, int r1, int c1, int r2, int c2)
	{
		int[][] temp = new int[size][size];
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				temp[i][j] = grid[i][j];
			}
		}
		int tempCelValue = temp[r1][c1];
		temp[r1][c1] = temp[r2][c2];
		temp[r2][c2] = tempCelValue;
		return temp;
	}

	private class Point
	{
		int row;
		int col;

		Point(int row, int col)
		{
			this.row = row;
			this.col = col;
		}

		public int getRow()
		{
			return row;
		}

		public int getCol()
		{
			return col;
		}

		public String toString()
		{
			return row + " " + col + "\n";
		}
	}

	// unit tests (not graded)
	public static void main(String[] args) throws FileNotFoundException
	{
		// create initial board from file
				Scanner scanner = new Scanner(new File("./src/txt/puzzle47.txt"));
				int N = scanner.nextInt();
				int[][] blocks = new int[N][N];
				for (int i = 0; i < N; i++){
					for (int j = 0; j < N; j++) {
						blocks[i][j] = scanner.nextInt();
					}
				}
									
				Board initial = new Board(blocks);
				//StdOut.print(initial.FindIndexOfElement(0).toString());
				// check if puzzle is solvable; if so, solve it and output solution
				if (initial.isSolvable()) {

					Solver solver = new Solver(initial);
					StdOut.println("Minimum number of moves = " + solver.moves());
					for (Board board : solver.solution()){
						StdOut.println(board);
					}
				}

				// if not, report unsolvable
				else {
					StdOut.println("Unsolvable puzzle");
				}
	}
}
