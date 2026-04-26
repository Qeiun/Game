import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Character
{
	static int InvSize = 5;
	
	String name;
	String description;
	int xpos;
	int ypos;
	/*
	String race;
	String cClass;
	*/
	int hitPoints;
	int strength;
	int intelligence;
	int dexterity;
	int armor;

	boolean moveable;
	boolean combative;
	
	static int count = 0;
	
	//future inventory ***
	ArrayList<Item> inventory = new ArrayList<Item>();
	 
	//Item[] inventory = new Item[InvSize](); 
	
	
	
	//constructors
	
	public abstract boolean move(Character[] profile, Map m);
	
	public void NPCsAttack(MapBlock here, Character[] p)
	{
		for(int x = 1; x <= count; x++)
		{
			if(p[0].isHere(p[x]) && p[x].combative)
			{
				if(p[x].rollForInitiative())
				{
					p[x].attack(here, p[0]);
				}
			}
		}
	}
	
	public boolean rollForInitiative()
	{
		int p1 = Die.roll(20);
		System.out.println(name + " rolled " + p1);
		int p2 = Die.roll(20);
		System.out.println("Player rolled " + p2);
		if(p1 > p2)
		{
			return true;
		}
		return false;
	}
	
	public boolean takeDamage(int d)
	{
		hitPoints -= d;
		System.out.println(name + "took " + d + "damage, leaving them with " + hitPoints + " HP");
		if(hitPoints > 0)
		{
			return true; // still alive?
		}
		return false;
	}
	
	public void attack(MapBlock here, Character o)
	{
		System.out.println(name + " swings at " + o.name);
		//Hit?
		if(Die.roll(20) > o.armor)
		{
			System.out.print(name + " hits with ");
			int damage = Die.roll(strength);
			System.out.println(damage + " DMG.");
			boolean alive = o.takeDamage(damage);
			if(!alive)
			{
				o.killMe(here);
			}
		}
		else
		{
			System.out.println("... but misses.");
		}
	}
	
	protected void killMe(MapBlock here)
	{
		moveable = false;
		combative = false;
		name = "Body of " + name;
		//dropAll(here); //loop through all inventory and remove - add to "here"
	}
	
	protected static void moveNPCs(Character[] profile,Map m)
	{
		for(int x = 1; x <= count; x++)
		{
			profile[x].move(profile, m);
		}
	}
	
	public boolean hasItem(int id)
	{
		for(int x = 0; x < inventory.size(); x++){
			//inventory is arraylist of items, pull keyID from each item
			if(inventory.get(x).keyID == id) 
			{
				return true;
			}
		
		}
		return false;
	}
	
	protected boolean isHere(Character c)
	{
		if((xpos == c.xpos) && (ypos == c.ypos))
		{
			return true;
		}
		return false;
	}
	
	protected void showNPCs(Character[] profile)
	{
		for(int x = 1; x <= count; x++)
		{
			if(profile[0].isHere(profile[x]))
			{
				System.out.println(profile[x].name + " is here.");
			}
		}
	}
	
	public Character()
	{
		
	}
	
	public Character(String n, String d, int x, int y, boolean m, boolean c, int hp, int s, int i, int dex, int a)
	{
		name = n;
		description = d;
		xpos = x;
		ypos = y;
		moveable = m;
		combative = c;
		hitPoints = hp;
		strength = s;
		intelligence = i;
		dexterity = dex;
		armor = a;
		
		//System.out.println(name + "Created");
	}
	
	
	
	public boolean getItem(String itemName, MapBlock here)
	{
		//remove item from mapblock, add to our inventory
		Item removed = here.removeItem(itemName);
		if(removed != null)
		{
			inventory.add(removed);
			System.out.println("You now have the " + removed.getName());	
			return true;
		}
		return false;
	}
	
	public boolean dropItem(String itemName, MapBlock here)
	{
		for(int x = 0; x < inventory.size(); x++)
		{
			if(inventory.get(x).getName().equalsIgnoreCase(itemName))
			{
				Item removed = inventory.remove(x);
				here.addItem(removed);
				System.out.println("You dropped the " + removed.getName() + ".");
				return true;
			}
		}
		System.out.println("You don't have a " + itemName + ".");
		return false;
	}
	
	public static void initChars(Character[] profile, String filename)
	{
		try
		{
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			String splitBy = ",";
			String[] data;
			
			while((line = br.readLine()) != null)
			{
				data = line.split(splitBy);
				
				int xpos = Integer.parseInt(data[0]);
				int ypos = Integer.parseInt(data[1]);
				String name = data[2];
				String description = data[3];
				/*
				
				*/
				int hitPoints = Integer.parseInt(data[6]);
				int strength = Integer.parseInt(data[7]);
				int intelligence = Integer.parseInt(data[8]);
				int dexterity = Integer.parseInt(data[9]);
				int armor = Integer.parseInt(data[10]);
				
				
				int pType = Integer.parseInt(data[11]);
				boolean combative = (Integer.parseInt(data[12]) > 0)?true:false;
				
				switch(pType)
				{
				case 0:
					//Immoveable
					Immoveable i = new Immoveable(name,description,xpos,ypos,combative,hitPoints,strength,dexterity,intelligence,armor);
					profile[++count] = i;
					break;
				case 1:
					//Moveable
					Moveable m = new Moveable(name,description,xpos,ypos,combative,hitPoints,strength,dexterity,intelligence,armor);
					profile[++count] = m;
					break;
				case 2:
					//Player
					Player p = new Player(name,description,xpos,ypos,hitPoints,strength,dexterity,intelligence,armor);
					profile[0] = p;
					break;
				default:
					System.out.println("Bad Character Type" + pType);
					break; 
				}
			}
			
			br.close();
			
			/* #Probably opening items csv file looping like map/characters then adding to respective char
			 * #and getting
			 * Item i = new Item();
			 * 
			 * profile[0].inventory.add(i);
			 * profile[0].inventory.get(3); //arraylist
			 * profile[0].inventory[3]; //array
			 * 
			 * profile[0].inventory.add(profile[2].inventory.remove(3));
			 */
		}
		catch(IOException e)
		{
			System.out.println("File Error: "+ filename);
		}	
	}

}
