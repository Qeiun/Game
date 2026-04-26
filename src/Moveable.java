
public class Moveable extends Character
{
	public Moveable(String n, String d, int x, int y, boolean c, int hp, int s, int i, int dex, int a)
	{
		super(n,d,x,y,true,true,hp,s,i,dex,a);
		
		System.out.println("Player " + name +  "Created");
	}
	
	@Override
	public boolean move(Character[] profile, Map m)
	{
		boolean stillAlive = true;
		
		MapBlock here = m.map[xpos][ypos];
		
		//static function, no need to create instance just run from class
		switch(Die.roll(4))
		{
		case 1:
			//North
			if(here.go(Direction.NORTH,this))
			{
				ypos--;
			}
			break;
		case 2:
			if(here.go(Direction.SOUTH,this))
			{
				ypos++;
			}
			break;
		case 3:
			if(here.go(Direction.EAST,this))
			{
				xpos++;
			}
			break;
		case 4:
			if(here.go(Direction.WEST,this))
			{
				xpos--;
			}
			break;
		default:
			System.out.println("Should never get here");
			break;
		}
		
		return stillAlive;
	}
}
