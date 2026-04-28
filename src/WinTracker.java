import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WinTracker
{
	private static class WinCondition
	{
		int x;
		int y;
		int itemId;
		String message;
		boolean complete;
		
		WinCondition(int x, int y, int itemId, String message)
		{
			this.x = x;
			this.y = y;
			this.itemId = itemId;
			this.message = message;
			this.complete = false;
		}
	}
	
	private ArrayList<WinCondition> conditions = new ArrayList<WinCondition>();
	private String finalMessage = "";
	private boolean gameWon = false;
	
	public WinTracker(String filename)
	{
		try
		{
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			while((line = br.readLine()) != null)
			{
				String[] data = line.split(",", 4);
				if(data.length < 4)
				{
					continue;
				}
				
				int x = Integer.parseInt(data[0].trim());
				int y = Integer.parseInt(data[1].trim());
				int itemId = Integer.parseInt(data[2].trim());
				String message = data[3].trim();
				
				if(x == -1)
				{
					finalMessage = message;
				}
				else
				{
					conditions.add(new WinCondition(x, y, itemId, message));
				}
			}
			
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("File Error: " + filename);
		}
	}
	
	public boolean checkWin(Player player)
	{
		if(gameWon)
		{
			return true;
		}
		
		boolean allDone = true;
		
		for(int i = 0; i < conditions.size(); i++)
		{
			WinCondition c = conditions.get(i);
			
			if(c.complete)
			{
				continue;
			}
			
			boolean atLocation = (player.xpos == c.x) && (player.ypos == c.y);
			boolean hasItem = player.hasItem(c.itemId);
			
			if(atLocation && hasItem)
			{
				c.complete = true;
				System.out.println(c.message);
			}
			else
			{
				allDone = false;
			}
		}
		
		if(allDone && conditions.size() > 0)
		{
			if(finalMessage.length() > 0)
			{
				System.out.println(finalMessage);
			}
			gameWon = true;
			return true;
		}
		
		return false;
	}
}
