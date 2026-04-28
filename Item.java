import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Item
{
	public static int masterKEYID = 2;
	
	private String name;
	private String des;
	private int offensive;
	private int defensive;
	private double value;
	
	public int keyID;
	
	private static int count = 0;
	
	//constructor
	public Item(String n, String d, int off, int def, double v)
	{
		name = n;
		des = d;
		offensive = off;
		defensive = def;
		value = v;
		
		keyID = masterKEYID++;
		
		count++;
	}
	
	public String getName()
	{
		return name;
	}

	public int getOffensive()
	{
		return offensive;
	}

	public int getDefensive()
	{
		return defensive;
	}

	public double getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s - %s [%d,%d]\nValue: $%1.2f\n",name,des,offensive,defensive,value);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Item))
		{
			return false;
		}
		
		//Typecasts object var into item
		Item i = (Item)obj;
				
		if(i.name.equalsIgnoreCase(name) && i.des.equalsIgnoreCase(des)) 
		{
			return true;
		}
		
		return false;
		
		
		
	}
	
	//getters & setters
	public static int getCount() 
	{
		return count;
	}
	
	public static void loadItems(String itemFile, Map m, Character[] p)
	{
		try
		{
			FileReader fr = new FileReader(itemFile);
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			String splitBy = ",";
			String[] data;
			
			while((line = br.readLine()) != null)
			{
				data = line.split(splitBy);
				
				int a = Integer.parseInt(data[0]);
				int b = Integer.parseInt(data[1]);
				String name = data[2];
				String desc = data[3];
				short off = Short.parseShort(data[4]);
				short def = Short.parseShort(data[5]);
				double value = Double.parseDouble(data[6]);
				
				Item i = new Item(name,desc,off,def,value);
				
				if(a < 0)
				{
					//character b gets item
					p[b].inventory.add(i);
					//p[b].armWith(i);
				}
				else
				{
					int x = a;
					int y = b;
					m.map[x][y].itemsHere.add(i);
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("File Error: "+ itemFile);
		}	
	}
}
