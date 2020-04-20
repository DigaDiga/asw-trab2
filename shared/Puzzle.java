package wwwordz.shared;

import java.util.ArrayList;
import java.util.List;

import wwwordz.shared.Table.Cell;

public class Puzzle
extends java.lang.Object
implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private List<Puzzle.Solution> solutions = new ArrayList<>();
	private Table table= new Table();	
	
	public Puzzle() {
	}
	public List<Puzzle.Solution> getSolutions() {
		return solutions;
	}
	public void setSolutions(List<Puzzle.Solution> solutions) {
		this.solutions = solutions;
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}

	public static class Solution
	extends java.lang.Object
	implements java.io.Serializable{

		private static final long serialVersionUID = -7101093650676016226L;
		private String word;
		private List<Table.Cell> cells;
		
		public Solution() {
		}
		
		public Solution(String word, List<Cell> cells) {
			this.word = word;
			this.cells = cells;
		}

		public String getWord() {
			return word;
		}

		public List<Table.Cell> getCells() {
			return cells;
		}
		public int getPoints() {
			return calPoints(this.getWord().length());
		}
		public int calPoints(int n) {
			if(n==3)
				return 1;
			else
				return calPoints(n-1)*2+1;
		}	
	}
}
