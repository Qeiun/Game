import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Map
{
	int maxX;
	int maxY;

	//make 2d array of...
	MapBlock[][] map;
	
	public Map(int x, int y, String filename)
	{
		maxX = x;
		maxY = y;
		
		
		map = new MapBlock[x][y];
		
		for(int X = 0; X < maxX; X++)
		{
			for(int Y = 0; Y < maxY; Y++)
			{
				map[X][Y]= new MapBlock();
			}
		}
		
		try
		{
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			String splitBy = ",";
			String[] data;
			
			while((line = br.readLine()) != null)
			{
				data = line.split(splitBy, 10);
				if(data.length < 8)
				{
					continue;
				}
				
				int xpos = Integer.parseInt(data[0].trim());
				int ypos = Integer.parseInt(data[1].trim());
				String title = data[2].trim();
				String description = data[3].trim();
				int n = Integer.parseInt(data[4].trim());
				int s = Integer.parseInt(data[5].trim());
				int e = Integer.parseInt(data[6].trim());
				int w = Integer.parseInt(data[7].trim());
				
				String yes = "NA";
				String no = "You can't go that way.";
				
				if(data.length > 8 && data[8] != null && data[8].trim().length() > 0)
				{
					yes = data[8].trim();
				}
				if(data.length > 9 && data[9] != null && data[9].trim().length() > 0)
				{
					no = data[9].trim();	
				}
				
				map[xpos][ypos] = new MapBlock(title, description, n, s, e, w, yes, no);
				
			}
			
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("File Error: "+ filename);
		}
	}
	
	@Override
	public String toString()
	{
		char full = '\u2588';
		char empty = '\u2591';
		
		String out = "MAP:\n";
		
		for(int y = 0; y < maxY; y++)
		{
			for(int x = 0; x < maxX; x++)
			{
				if(map[x][y].getTitle() == "VOID")
				{
					for(int c = 0; c < 3; c++)
					{
						out += empty;	
					}
					
				}
				else
				{
					for(int c = 0; c < 3; c++)
					{
						out += full;
					}
				}
			}
			out += "\n";
		}
		return out;
	}
}
