import java.util.ArrayList;
//Remove printlines from mapblock level
public class MapBlock
{
	private static int count;
	
	private String title;
	private String description;
	private int N;
	private int S;
	private int E;
	private int W;
	
	public String yesMsg = "";
	public String noMsg = "";
	
	ArrayList<Item> itemsHere = new ArrayList<Item>();
	
	
	public MapBlock()
	{
		title = "VOID";
		description = "VOID";
		N=0;
		S=0;
		E=0;
		W=0;
	}
	
	public MapBlock(String t, String d, int n, int s, int e, int w, String yes, String no) 
	{
		title = t;
		description = d;
		N = n;
		S = s;
		E = e;
		W = w;
		yesMsg = yes;
		noMsg = no;
		
		count++;
	}
	
	//left as number so num greater than 1 means possible entry
	public boolean go(Direction d, Character c) 
	{
		switch(d)
		{
		case Direction.NORTH:
			if(N == 1){
				return true;
			}	
			else if (c.hasItem(N))
			{
				System.out.println(yesMsg);
				return true;	
			}
			else
			{
				System.out.println(noMsg);
			}	
			break;
		case Direction.SOUTH:
			if(S == 1){
				return true;
			}	
			else if (c.hasItem(S))
			{
				System.out.println(yesMsg);
				return true;	
			}
			else
			{
				System.out.println(noMsg);
			}	
			break;
		case Direction.EAST:
			if(E == 1){
				return true;
			}	
			else if (c.hasItem(E))
			{
				System.out.println(yesMsg);
				return true;	
			}
			else
			{
				System.out.println(noMsg);
			}	
			break;
		case Direction.WEST:
			if(W == 1){
				return true;
			}	
			else if (c.hasItem(W))
			{
				System.out.println(yesMsg);
				return true;	
			}
			else
			{
				System.out.println(noMsg);
			}	
			break;
		}
		return false;
	}
	
	// getters
	public String getTitle()
	{
		return title;
	}
	
	public String getDesc()
	{
		return description;
	}
	
	public int getN()
	{
		return N;
	}
	
	public int getS()
	{
		return S;
	}
	
	public int getE()
	{
		return E;
	}
	
	public int getW()
	{
		return W;
	}
	
	
	@Override
	public String toString()
	{
		return String.format("\n%s\n%s\n",title,description);
	}
	
	public void showItems()
	{
		for(int x = 0; x < itemsHere.size(); x++)
		{
			//Starts from arraylist > item > item characteristic
			System.out.println("There is a " + itemsHere.get(x).getName() + " here.");
		}
	}
	
	public Item removeItem(String i)
	{
		for(int x = 0; x < itemsHere.size(); x++)
		{
			if(itemsHere.get(x).getName().equalsIgnoreCase(i))
			{
				return itemsHere.remove(x);
			}
		}
		System.out.println("There is no" + i + "here.");
		return null;
	}
	
	public void addItem(Item item)
	{
		if(item != null)
		{
			itemsHere.add(item);
		}
	}
}
