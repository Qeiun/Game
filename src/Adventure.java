public class Adventure //implements ActionListener
{

	public static void main(String[] args)
	{
		int maxX = 10;
		int maxY = 10;
		int numChars = 20;
		
		boolean playing = true;
		
		String mapFile = "../data/map.csv";
		String charFile = "../data/characters.csv";
		String itemFile = "../data/items.csv";
		String winFile = "../data/win.csv";
		
		Map map = new Map(maxX,maxY,mapFile);
		Character[] profile = new Character[numChars];
		WinTracker winTracker = new WinTracker(winFile);
		
		System.out.println(map);
		
		//System.out.println(MapBlock.count);
		
		//takes newly made character array and initializes it with the characters in the characters.csv file
		Character.initChars(profile, charFile);
		
		//loads items from the items.csv file into the map
		Items.loadItems(itemFile, map, profile);
		//starting positions
		//int xpos = 0;
		//int ypos = 0;
		
		//System.out.println(map.map[xpos][ypos]);
		
		//Character c = new Character("Fred","stoneage guy wearing a leopard tunic",1,0,true,true);
		//Player p = new Player("Barney","Fred's best friend and next door neighbor",0,0);
		//Moveable m = new Moveable("Wilma","Fred's wife - white dress - fancy hair",1,0,false);
		//Immoveable i = new Immoveable("Betty","Barney's stay-at-home wife",0,0,false);
		
		Player player = (Player)profile[0];
		
		//Start game
		
		while(playing) {
			playing = player.move(profile, map); //did we die or win???
			if(playing && winTracker.checkWin(player))
			{
				playing = false;
			}
		}
		
		//Character.initChars(profile,charFile);
		
		System.out.println("Game Over");
		
	}

}
