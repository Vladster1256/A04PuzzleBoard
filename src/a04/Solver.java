package a04;

import java.io.File;
import java.util.Scanner;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private static final int SPACE = 0;

	private static class Move implements Comparable<Move> {
		private Move previous;
		private Board board;
		private int numMoves = 0;

		public Move(Board board) {
			this.board = board;
		}

		private Move(Board board, Move previous) {
			this.board = board;
			this.previous = previous;
			this.numMoves = previous.numMoves + 1;
		}

		private MinPQ pq;
		// find a solution to the initial board (using the A* algorithm)

		private Move lastMove;

		public void Solver(Board initial) {
			MinPQ<Move> moves = new MinPQ<Move>();
			moves.insert(new Move(initial));

			MinPQ<Move> twinMoves = new MinPQ<Move>();
			twinMoves.insert(new Move(twin()));

			while (true) {
				lastMove = expand(moves);
				if (lastMove != null || expand(twinMoves) != null)
					return;
			}
		}

		private Move expand(MinPQ<Move> moves) {
			if (moves.isEmpty())
				return null;
			Move bestMove = moves.delMin();
			if (bestMove.board.isGoal())
				return bestMove;
			for (Board neighbor : bestMove.board.neighbors()) {
				if (bestMove.previous == null || !neighbor.equals(bestMove.previous.board)) {
					moves.insert(new Move(neighbor, bestMove));
				}
			}
			return null;
		}

		// min number of moves to solve initial board
		public int moves() {
			return Board.isSolvable() ? lastMove.numMoves : -1;

		}

		// sequence of boards in a shortest solution
		public Iterable<Board> solution() {
			if (!Board.isSolvable())
				return null;

			Stack<Board> moves = new Stack<Board>();
			while (lastMove != null) {
				moves.push(lastMove.board);
				lastMove = lastMove.previous;
			}

			return moves;

		}

		// solve a slider puzzle (given below)
		public static void main(String[] args) {
			Scanner scanner =  new Scanner(new File());
			int N = 
			int[][] blocks = new int[N][N];
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					blocks[i][j] = in.readInt();

			Board initial = new Board(blocks);
			Solver solver = new Solver();
			Move mover = new Move(initial);

			if (!Board.isSolvable())
				StdOut.println("No solution possible");
			else {
				StdOut.println("Minimum number of moves = " + mover.moves());
				for (Board board : mover.solution()) {
					StdOut.println(board);
				}
			}
		}

		@Override
		public int compareTo(Move move) {
			return (this.board.manhattan() - move.board.manhattan()) + (this.numMoves - move.numMoves);
		}

		private int[][] blocks;

		private boolean isSpace(int block) {
			return block == SPACE;
		}

		private int block(int row, int col) {
			return blocks[row][col];
		}

		private Board twin() {
			for (int row = 0; row < blocks.length; row++)
				for (int col = 0; col < blocks.length - 1; col++)
					if (!isSpace(block(row, col)) && !isSpace(block(row, col + 1)))
						return new Board(swap(row, col, row, col + 1));
			throw new RuntimeException();
		}

		private int[][] copy(int[][] blocks) {
			int[][] copy = new int[blocks.length][blocks.length];
			for (int row = 0; row < blocks.length; row++)
				for (int col = 0; col < blocks.length; col++)
					copy[row][col] = blocks[row][col];

			return copy;
		}

		private int[][] swap(int row1, int col1, int row2, int col2) {
			int[][] copy = copy(blocks);
			int tmp = copy[row1][col1];
			copy[row1][col1] = copy[row2][col2];
			copy[row2][col2] = tmp;

			return copy;
		}
	}
}
