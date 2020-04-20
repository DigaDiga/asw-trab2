package wwwordz.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class Table
extends java.lang.Object
implements java.lang.Iterable<Table.Cell>, java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Table.Cell[][] table = new Table.Cell[6][6];
	
	private int maxC = 4;
	private int maxR = 4;
	
	public Table() {
		for(int i = 1; i <= maxR; i++) {
			for(int j = 1; j <= maxC ; j++) {
				table[i][j] = new Table.Cell(j,i);
			}
		}
	}
	public Table(java.lang.String[] data) {
		String word;
		for(int i = 1; i <= maxR; i++) {
			word=data[i-1];
			for(int j = 1; j <= maxC ; j++)
				table[i][j] = new Table.Cell(i,word.charAt(j-1),j);;
		}
	}
	
	public Cell[][] getTable() {
		return table;
	}
	public void setTable(Cell[][] table) {
		this.table = table;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (!Arrays.deepEquals(table, other.table))
			return false;
		return true;
	}
	public Table.Cell getCell(int row,int column){
		return table[row][column];
	}
	public void setCell(Cell cell,int i, int j){
		this.table[i][j]=cell;
	}
	public java.util.List<Table.Cell> getEmptyCells(){
		List<Table.Cell> list = new ArrayList<Table.Cell>();
		for(int i = 1; i < 5; i++ ) {
			for(int j = 1; j < 5;j++) {
				if(this.table[i][j].isEmpty())
					list.add(this.table[i][j]);
			}
		}
		return list;
	}
	public char getLetter(int row,int column) {
		return this.getCell(row, column).letter;
	}
	public java.util.List<Table.Cell> getNeighbors(Table.Cell cell){
		List<Table.Cell> list = new ArrayList<Table.Cell>();
		
		for(int line = cell.row-1; line <= cell.row +1;line++) {
			for(int column = cell.column -1 ; column <= cell.column + 1; column++) {
				if (table[line][column] != null && table[line][column] != cell) {
					list.add(table[line][column]);
				}
			}
		}
		return list;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(table);
		return result;
	}
	public java.util.Iterator<Table.Cell> iterator(){
		Iterator<Table.Cell> it = new Table.CellIterator();
		return it;
	}
	public void setLetter(int row,int column,char letter) {
		this.getCell(row, column).letter = letter;
	}
	@Override
	public String toString() {
		return "Table [table=" + Arrays.toString(table) + "]";
	}

	
	public static class Cell
	extends java.lang.Object
	implements java.io.Serializable{
		
		private static final long serialVersionUID = 1L;
		private int column;
		private char letter;
		private int row;
		
		public Cell() {}		
		public Cell(int column, int row) {
			this.column = column;
			this.row = row;
		}
		public Cell(int row, char letter, int column) {
			this.column = column;
			this.letter = letter;
			this.row = row;
		}
		
		public int getColumn() {
			return column;
		}
		public void setColumn(int column) {
			this.column = column;
		}
		public int getRow() {
			return row;
		}
		public void setRow(int row) {
			this.row = row;
		}
		public boolean isEmpty() {
			if(Character.isLetter(getLetter()))
				return false;
			else return true;
		}
		@Override
		public String toString() {
			return "Cell [column=" + column + ", letter=" + letter + ", row=" + row + "]";
		}
		public char getLetter() {
			return letter;
		}
		public void setLetter(char letter) {
			this.letter = letter;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + column;
			result = prime * result + letter;
			result = prime * result + row;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Cell other = (Cell) obj;
			if (column != other.column)
				return false;
			if (letter != other.letter)
				return false;
			if (row != other.row)
				return false;
			return true;
		}
				
	}
	
	private class CellIterator
	extends java.lang.Object
	implements java.util.Iterator<Table.Cell>{
		
		private int column = 1;
		private int row = 1;
		
		public CellIterator() {}
		
		public boolean hasNext() {
			if(row > 4)
				return false;
			else
				return true;
		}
		public Cell next() {
			Cell cell = table[row][column];
			column++;
			if(column > 4){
				row++;
				column=1;	
			}
			return cell;
		}

		public void remove() {
			Iterator.super.remove();
		}	
	}

}
