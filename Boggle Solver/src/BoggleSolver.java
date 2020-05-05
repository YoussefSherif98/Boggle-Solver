import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.SET;

public class BoggleSolver {
	
	private MyTrie allWords = new MyTrie();
	private BoggleBoard board;
	private SET<String> answer;
	
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary)
    {
    	if (dictionary == null)
    		throw new IllegalArgumentException("Null Argument");
    	
    	
    	for (int i = 0; i < dictionary.length ; i++)
    	{
    		if (dictionary[i] == null)
    			throw new IllegalArgumentException("Null Argument");
    	}
    	

    	for (String s : dictionary)
    		allWords.put(s);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
    	if (board == null)
    		throw new IllegalArgumentException("Null Argument");
    	
    	this.board = board;
    	
    	answer = new SET<String>();
    	boolean[][] marked = new boolean[board.rows()][board.cols()];
    	for (int i = 0; i < board.rows() ; i++)
    		for (int j = 0; j < board.cols() ; j++)
    			marked[i][j] = false;
    	
    	MyNode root = allWords.getRoot();
    	
    	for (int i = 0; i < board.rows(); i++)
    	{
    		for (int j = 0; j < board.cols(); j++)
    		{
    			marked[i][j] = true;
    			
    			char c = board.getLetter(i, j);
    			int next = c - 'A';
    			
    			if (root.next[next] == null)
    				continue;
    			
    			String s;
    			if (c == 'Q')
    				s = "QU";
    			else
    				s = Character.toString(c);
    			
    			visit(s,root.next[next], i, j, marked);
    			marked[i][j] = false;
    		}
    	}
    	return answer;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word)
    {
    	if (word == null)
    		throw new IllegalArgumentException("Null Argument");
    	
    	if (!allWords.contains(word))
    		return 0;
    	
    	int length = word.length();
    	
    	if (length < 3)
    		return 0;
    	else if (length < 5)
    		return 1;
    	else if (length < 6)
    		return 2;
    	else if (length < 7)
    		return 3;
    	else if (length < 8)
    		return 5;
    	else
    		return 11;
    	
    }

    private Point[] getNeighbours(int row, int col)
    {
    	int length;
    	if (board.cols() > 1 && board.rows() > 1)
    	{
    		if ((row == 0 && col == 0) || (row == 0 && col == board.cols()-1) || (row == board.rows()-1 && col == 0) || (row == board.rows()-1 && col == board.cols()-1))
        		length = 3;
        	else if (row == 0 || row == board.rows()-1 || col == 0 || col == board.cols() - 1)
        		length = 5;
        	else
        		length = 8;
    	}
    	else
    	{
    		if (board.cols() == 1 && board.rows() == 1)
    			length = 0;
    		else
    		{
    			if (board.cols() == 1)
    			{
    				if (row == 0 || row == board.rows() - 1)
    					length = 1;
    				else
    					length = 2;
    			}
    			else
    			{
    				if (col == 0 || col == board.cols() - 1)
    					length = 1;
    				else
    					length = 2;
    			}
    		}
    	}
    	
    	Point[] neighbours = new Point[length];
    	int counter = 0;
    	
    	for (int i = row-1 ; i <= row+1 ; i++)
    	{
    		if (i < 0 || i >= board.rows())
    			continue;
    		for (int j = col-1 ; j<= col+1 ; j++)
    		{
    			if (j < 0 || j == board.cols())
    				continue;
    			if (i == row && j == col)
    				continue;
    			
    			neighbours[counter++] = new Point(i,j);
    		}
    	}
    	
    	return neighbours;
    }
    
    private void visit(String prefix, MyNode node, int i, int j, boolean[][] marked)
    {
    	Point[] neighbours = getNeighbours(i, j);
    	for (Point p : neighbours)
    	{
    		int row = p.getX();
    		int col = p.getY();
    		
    		if (marked[row][col])
    			continue;
    		
    		char c = board.getLetter(row, col);
    		int next = (c - 'A') ;
    		
    		if ((node.next[next]) != null)
    		{
    			marked[row][col] = true;
    			
    			String letter;
    			if (c == 'Q')
    				letter = "QU";
    			else
    				letter = Character.toString(c);
    			
    			if ((node.next[next]).value)
    			{
    				String s = prefix + letter;
    				if (s.length() >= 3)
    					answer.add(s);
    			}
    			
    			visit(prefix+letter, node.next[next], row, col, marked);
    			marked[row][col] = false;
    		}
    	}
    }

    
    public static void main(String[] args)
    {
    	
    	String[] dictionary = new String[264061];
    	int counter = 0;
    	
    	String path = "C:\\Users\\Youssef\\Desktop\\College\\Courses\\Algorithms 2\\Assignments\\Boggle\\Specs\\";
    	path = path + "dictionary-yawl.txt";
    	File file = new File(path);
    	try {
			Scanner scan = new Scanner(file);
			while (scan.hasNext())
			{
				dictionary[counter++] = scan.next();
			}
			scan.close();
		} catch (FileNotFoundException e) {
		}
    	
    	char[][] letters = {{'D','A','U','E'},{'S','W','O','T'},{'R','R','X','S'},{'N','T','Z','M'}};
    	
    	BoggleSolver solver = new BoggleSolver(dictionary);
    	BoggleBoard board = new BoggleBoard(letters);
    	
    	System.out.println(board.toString());
    	
    	for (String s : solver.getAllValidWords(board))
    		System.out.println("Found "+s);
    	
    }
    
}
