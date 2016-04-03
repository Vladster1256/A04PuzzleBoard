package a04;

import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver
{
	private MinPQ<Node> pq;
	private Board initial;;
	private boolean foundSolution = false;

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial)
	{
		this.initial = initial;
		pq = new MinPQ<Node>(byLeastMoves());
	}

	// min number of moves to solve initial board
	public int moves()
	{
		return initial.manhattan();

	}

	// sequence of boards in a shortest solution
	public Iterable<Board> solution()
	{	
		Stack<Node> solution = new Stack<Node>();
		Stack<Board> boardsolution = new Stack<Board>();
		pq.insert(new Node(initial, null, 0));
		Node highestpriority;
		Stack<Board> highestpriorityneighbors;
		int movecounter = 0;
		
		
		while(foundSolution == false)
		{
			highestpriority = pq.delMin();
			if(highestpriority.board.isGoal())
			{
				foundSolution = true;
				for(Node item: solution)
				{
					boardsolution.push(item.board);
				}
				return boardsolution;
			}
			else
			{
				solution.push(highestpriority);
				highestpriorityneighbors = (Stack<Board>) highestpriority.board.neighbors();
				movecounter++;
				for(Board item: highestpriorityneighbors)
				{
					pq.insert(new Node(item,highestpriority, movecounter));
				}

			}
			
		}
		return boardsolution;
		
	}

	private class Node implements Comparable<Node>
	{
		public Node previous;
		public final Board board;
		int moves = 0;
		public int priority;

		public Node(Board board, Node previous, int nummoves)
		{

			this.board = board;
			this.previous = previous;
			priority = this.board.manhattan() + nummoves;
		}

		@Override
		public int compareTo(Node arg0)
		{
			return this.priority - arg0.priority;
		}

		public String toString()
		{
			return board.toString() + priority;
		}

	}
	
	private static Comparator<Node> byLeastMoves()
	{
		return new ByLeastMoves();
	}
	
	private static class ByLeastMoves implements Comparator<Node>
	{

		@Override
		public int compare(Node n1, Node n2)
		{
			int priority1 = n1.priority;
			int priority2 = n2.priority;
			return priority1-priority2;
		}
		
	}

	// solve a slider puzzle (given below)
	public static void main(String[] args)
	{
		// create initial board from file
		In in = new In("./src/txt/puzzle01.txt");
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// check if puzzle is solvable; if so, solve it and output solution
		if (initial.isSolvable())
		{
			Solver solver = new Solver(initial);
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}

		// if not, report unsolvable
		else
		{
			StdOut.println("Unsolvable puzzle");
		}
	}
}
