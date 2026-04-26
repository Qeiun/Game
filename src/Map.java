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
				data = line.split(splitBy);
				
				int xpos = Integer.parseInt(data[0]);
				int ypos = Integer.parseInt(data[1]);
				String title = data[2];
				String description = data[3];
				int n = Integer.parseInt(data[4]);
				int s = Integer.parseInt(data[5]);
				int e = Integer.parseInt(data[6]);
				int w = Integer.parseInt(data[7]);
				
				String yes = "";
				String no = "";
				
				if(data.length > 8)
				{
					yes = data[8];
					no = data[9];	
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
