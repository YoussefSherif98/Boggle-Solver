public class MyTrie {
	
	private MyNode root;
	
	public MyTrie()
	{
		this.root = new MyNode();
	}
	
	public MyNode getRoot()
	{
		return this.root;
	}

	public MyNode getNode(MyNode x, char c)
	{
		if (x == null)
			return null;
		
		int next = c - 'A';
		return x.next[next] ;
	}
	
	public boolean isWord(MyNode x)
	{
		if (x == null)
			return false;
		
		return x.value;
	}

	public void put(String s)
	{
		if (s.contains("Q"))
		{
			if (!s.contains("QU"))
				return;
		}
		put(s , root , 0);
	}
	
	private void put(String s , MyNode x, int d)
	{
		if (d == s.length())
		{
			x.setValue();
			return;
		}
		
		char c = s.charAt(d);
		int next = c - 'A';
		if (x.next[next] == null)
			x.next[next] = new MyNode();
		
		if (c == 'Q')
			put (s , x.next[next], d+2);
		else
			put(s , x.next[next] , d+1);
	}

	public boolean contains(String s)
	{
		return contains(s, root, 0);
	}
	
	private boolean contains(String s, MyNode x, int d)
	{
		if (d == s.length())
			return x.value;
		
		char c = s.charAt(d);
		int next = c - 'A';
		if (x == null)
			return false;
		if (x.next[next] == null)
			return false;
		
		if (c == 'Q')
			return contains(s, x.next[next], d+2);
		else
			return contains(s, x.next[next], d+1);
	}

}
