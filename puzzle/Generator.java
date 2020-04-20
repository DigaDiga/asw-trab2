package wwwordz.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import wwwordz.shared.Table;
import wwwordz.shared.Table.Cell;
import wwwordz.puzzle.Trie.Search;
import wwwordz.shared.Puzzle;
import wwwordz.shared.Puzzle.Solution;

public class Generator 
extends java.lang.Object{

	public Generator() {}
	
	public Puzzle generate() {
		Puzzle puzzle = new Puzzle();
		Table table = puzzle.getTable();
		Dictionary dict = Dictionary.getInstance();
		List<Cell> emptyCells = new ArrayList<>();
		Table.Cell[][] tab = table.getTable();
		while ((emptyCells = table.getEmptyCells()).size() != 0) {
			System.out.println(emptyCells.size());
			int flag = 0;
			String largeWord = dict.getRandomLargeWord();
			int count = 0;
			Collections.shuffle(emptyCells);
			for(Cell cell : emptyCells) {
				tab[cell.getRow()][cell.getColumn()] = new Table.Cell(cell.getRow(), largeWord.charAt(count), cell.getColumn());
				count++;
				List<Cell> neighbors = table.getNeighbors(cell);
				Collections.shuffle(neighbors);
				for(Cell next : neighbors) {
					if (count >= largeWord.length() - 1) {
						flag = 1;
						break;
					}
					else if(next.getLetter()==largeWord.charAt(count))
						count++;
					else if(next.isEmpty()) {
						tab[next.getRow()][next.getColumn()] = new Table.Cell(next.getRow(), largeWord.charAt(count), next.getColumn());
						count++;
					}
					neighbors = table.getNeighbors(next);
					Collections.shuffle(neighbors);
				}
				if(flag==1)
					break;
			}
		}
		table.setTable(tab);
		puzzle.setTable(table);
		puzzle.setSolutions(getSolutions(table));
		return puzzle;
	}

	public Puzzle random() {
		
		Puzzle puzzle = new Puzzle();
		String[] data = new String[4];
		
		char[] chars = new char[4];
		
		for(int i = 1; i <= 4 ; i++) {
			for(int j = 1; j<=4 ; j++) {
				Random rand = new Random();
				chars[j-1] = (char) (rand.nextInt(26)+'A');
			}
			data[i-1] = new String(chars);
		}
		Table t = new Table(data);
		puzzle.setTable(t);
		puzzle.setSolutions(getSolutions(t));
		return puzzle;
	}

	public List<Puzzle.Solution> getSolutions(Table table) {
		
		List<Solution> solutions = new ArrayList<>();
		Dictionary dict = Dictionary.getInstance();
		
		for (Iterator<Cell> it = table.iterator(); it.hasNext();) {
			Cell cell = it.next();
			if(cell.isEmpty())
				continue;
			Search s = dict.startSearch();
			List<Cell> visited = new ArrayList<>();
			String word = "";
			getSolutions(cell,table,word,s,solutions,visited);
		}
		return solutions;
	}
	private void getSolutions(Cell cell,Table table,String word,Search s,List<Solution> solutions,List<Cell> visit){
		if (visit.contains(cell) || ! s.continueW(cell.getLetter())) {
			return;
		}
		word += cell.getLetter();
		visit.add(cell);
		Solution solution = new Solution(word,visit);
		if(word.length() > 2 && s.isW() && !verify(solutions,word))
			solutions.add(solution);
		List<Cell> neighbors = table.getNeighbors(cell);
		for(Cell next : neighbors) {
			getSolutions(next,table,new String(word),new Search(s),solutions,new ArrayList<>(visit));
			}
		}
	private boolean verify(List<Solution> solutions, String w) {
		for(Solution s : solutions)
			if(s.getWord().equals(w))
				return true;
		return false;
	}


}
