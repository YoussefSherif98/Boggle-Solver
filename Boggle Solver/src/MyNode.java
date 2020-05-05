
public class MyNode {

	private static final int RADIX = 26;

	public boolean value = false;
	public MyNode[] next = new MyNode[RADIX];

	public void setValue()
	{
		this.value = true;
	}

}
