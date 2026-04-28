import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Items
{
	public static void loadItems(String itemFile, Map m, Character[] characters)
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
				if(data.length < 7)
				{
					continue;
				}
				
				int a = Integer.parseInt(data[0].trim());
				int b = Integer.parseInt(data[1].trim());
				String name = data[2].trim();
				String desc = data[3].trim();
				short off = Short.parseShort(data[4].trim());
				short def = Short.parseShort(data[5].trim());
				double value = Double.parseDouble(data[6].trim());
				
				Item item = new Item(name, desc, off, def, value);
				
				if(a < 0)
				{
					if(b >= 0 && b < characters.length && characters[b] != null)
					{
						characters[b].inventory.add(item);
					}
				}
				else
				{
					int x = a;
					int y= b;
					if(x >= 0 && x < m.map.length && y >= 0 && y < m.map[x].length)
					{
						m.map[x][y].addItem(item);
					}
				}
			}
			
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("File Error: " + itemFile);
		}
	}
}
