import java.util.Scanner;
// add override getitem method from character
// if-else for if item exists there or not, void function
public class Player extends Character
{
	Scanner scan = new Scanner(System.in);
	
	public Player(String n, String d, int x, int y, int hp, int s, int i, int dex, int a)
	{
		super(n,d,x,y,true,true,hp,s,i,dex,a);
		
		System.out.println("Player " + name +  "Created");
	}
	
	@Override
	public boolean move(Character[] profile, Map m)
	{
		MapBlock here = m.map[xpos][ypos];
		
		System.out.println(here);
		here.showItems();
		Character.moveNPCs(profile,m);
		//from perspective of player, so not static (all instances)
		showNPCs(profile);
		
		NPCsAttack(here, profile);
		
		if(profile[0].hitPoints <= 0)
		{
			System.out.println("You died!");
			return false;
		}
		
		String response = prompt();
		
		char command = response.toUpperCase().charAt(0);
		String[] data = response.split(" ", 2);
		String obj = "";
		if(data.length > 1)
		{
			obj = data[1]; //object after command
		}
		
		switch(command)
		{
		case '?':
			//showMenu();
			break;
		case 'I':
			showItems();
			break;
		case 'N':
			if(here.go(Direction.NORTH,this))
			{
				ypos--;
			}
			else
			{
				System.out.println("This way is shut");
			}
			break;
		case 'S':
			if(here.go(Direction.SOUTH,this))
			{
				ypos++;
			}
			else
			{
				System.out.println("This way is shut");
			}
			break;
		case 'E':
			if(here.go(Direction.EAST,this))
			{
				xpos++;
			}
			else
			{
				System.out.println("This way is shut");
			}
			break;
		case 'W':
			if(here.go(Direction.WEST,this))
			{
				xpos--;
			}
			else
			{
				System.out.println("This way is shut");
			}
			break;
		case 'G': //Get
			if(obj.length() == 0)
			{
				System.out.println("Get what?");
				break;
			}
			System.out.println("Getting " + obj);
			getItem(obj, here);
			
			break;
		case 'D':
			if(obj.length() == 0)
			{
				System.out.println("Drop what?");
				break;
			}
			System.out.println("Dropping " + obj);
			dropItem(obj, here);
			break;
		case 'Q': //Quit Game
			return false;
			
		default:
			break;
		}
		
		return true;
	}
	
	public void showItems() 
	{
		for(int x = 0; x < inventory.size(); x++)
		{
			System.out.println(inventory.get(x).getName());
		}	
	}
	
	public void showMenu()
	{
		
	}
	
	private String prompt()
	{
		System.out.println("Move? > ");
		return scan.nextLine();
	}
}
