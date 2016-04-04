package a04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


	// find a solution to the initial board (using the A* algorithm)
public class Solver {
	private static final int SPACE = 0;
	private Board board;
	private MinPQ<Move> pq;

	private Move lastMove;

	public Solver(Board initial) {
		this.board = initial;
		pq = new MinPQ<Move>();
		pq.insert(new Move(initial));
		
		while (pq.size() > 0 ) {
			lastMove = expand(pq);
			//System.out.println("Size:  " + pq.size());// todo remove
			if (lastMove != null)
				return;
		}
	}

	// min number of moves to solve initial board
	public int moves() {
		return board.isSolvable() && lastMove != null ? lastMove.numMoves : -1;

	}

	// sequence of boards in a shortest solution
	public Iterable<Board> solution() {
		if (!board.isSolvable()){
			return null;			
		}

		Stack<Board> moves = new Stack<Board>();
		while (lastMove != null) {
			moves.push(lastMove.board);
			lastMove = lastMove.previous;
		}

		return moves;

	}

	/** PRIVATE CLASS **/
	private Move expand(MinPQ<Move> moves) {
		if (moves.isEmpty()) {
			return null;
		}
			
		Move bestMove = moves.delMin();
		
		if (bestMove.board.isGoal()){
			return bestMove;
		}
	
		for (Board neighbor : bestMove.board.neighbors()) {
			if (bestMove.previous == null || !neighbor.equals(bestMove.previous.board)) {
				moves.insert(new Move(neighbor, bestMove));
			}
		}
		
		return null;
	}
	
	boolean isSpace(int block) {
		return block == SPACE;
	}

	/** PRIVATE CLASS **/
	private class Move implements Comparable<Move> {
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

		public int compareTo(Move move) {
			return (board.manhattan() - move.board.manhattan()) + (this.numMoves - move.numMoves);
		}
	}

	/** TEST **/
	// solve a slider puzzle (given below)
	public static void main(String[] args) throws FileNotFoundException {
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