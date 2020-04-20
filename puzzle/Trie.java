package wwwordz.puzzle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Trie
extends java.lang.Object
implements java.lang.Iterable<java.lang.String> {
	public Node root;
	
	public Trie() {
		this.root = new Node();
	}
	
	public void put(String word) {
		root.put(word, 0);
	}
	
	public String getRandomLargeWord() {
		StringBuilder word = new StringBuilder();
		Node node = this.root;
		while(!(node.getisWord() && node.children.size()==0 )) {
			Random random = new Random();
			int rand = random.nextInt(node.children.size());
			Object[] values = node.children.keySet().toArray();
			char c = (char) values[rand];
			word.append(c);
			node=node.children.get(c);
		}
		String s = word.toString();
		return s;
	}

	@Override
	public Iterator<String> iterator() {
		Iterator<String> it = new NodeIterator();
		return it;
	}
	
	public Trie.Search startSearch(){
		Trie.Search t = new Trie.Search(root);
		return t;
	}
	
	public static class Node 
	extends HashMap<Character,Node>{

		private static final long serialVersionUID = 1L;
		private HashMap<Character, Node> children = new HashMap<>();
	    private boolean isWord;

	    public Node() {}

	    public boolean getisWord() {
	        return isWord;
	    }

	    public void setWord(boolean isWord) {
	        this.isWord = isWord;
	    }
	    public void put(String word, int n) {
	    	if(n>=word.length()) {
	    		this.isWord = true;
	    		return;
	    	}
	    	HashMap<Character, Node> children = this.children;
	    	char c = word.charAt(n);
	    	if(children.containsKey(c))
	    		children.get(c).put(word,n+=1);
	    	else {
	    		Node node = new Node();
	    		children.put(c, node);
	    		children.get(c).put(word,n+=1);
	    	}		
	    }
	}
	
	public static class Search
	extends java.lang.Object{
		
		private Trie.Node root = new Trie.Node();
		
		public Search(Trie.Node root) {
			this.root = root;
		}
		public Search(Trie.Search search) {
			this.root = search.root;
		}
		public boolean continueW(char letter) {
			return continueWith(letter);
		}
		private boolean continueWith(char letter) {
			if(root!=null) {
				if(root.children.containsKey(letter)) {
					root = root.children.get(letter);
					return true;
				}
				root = null;
			}
			return false;
		}
		public boolean isW() {
			return isWord();
		}
		private boolean isWord() {
			return root != null && root.isWord;
		}
		
	}
	
	public class NodeIterator
	extends java.lang.Object
	implements java.util.Iterator<java.lang.String>, java.lang.Runnable { 
		private java.lang.Thread thread;
		private java.lang.String nextWord;
		private boolean terminated;
		public NodeIterator() {
			this.thread = new Thread(this, "node iterator");
			thread.start();
		}
		@Override
		public void run() {
			StringBuilder strBld = new StringBuilder();
			this.terminated = false;
			visited(strBld, root);
            synchronized(this) {
            	this.terminated = true;
            	handshake();
            }
		}
		@Override
		public boolean hasNext() {
			synchronized(this) {
				if (terminated == false)
					handshake();
			}
			if (this.nextWord != null) return true;
			else return false;
		}
		@Override
		public java.lang.String next() {
			String str = this.nextWord;
			synchronized(this) {
				this.nextWord = null;
			} return str;
		}
		private void handshake() {
            notify();
            try {
                wait();
            } catch (InterruptedException cause) {
                throw new RuntimeException("Unexpected interruption while waiting", cause);
            }
        }
		public void visited(StringBuilder strBld, Node node) {
	          
	            synchronized(this) {
	                if (this.nextWord != null) handshake();
	                	if (node.getisWord())
	                		this.nextWord = strBld.toString();
	            }
	            for (char letter: node.children.keySet()) {
	                StringBuilder newStrBld = new StringBuilder(strBld);
	                newStrBld.append(letter);
	                visited(newStrBld, node.children.get(letter));
	            }
	        }
	}
}
