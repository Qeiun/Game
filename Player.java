import java.util.Scanner;
// add override getitem method from character
// if-else for if item exists there or not, void function
public class Player extends Character
{
	Scanner scan = new Scanner(System.in);
	private Item leftHand;
	private Item rightHand;
	private double gold = 40.0;
	private static final String[] SHOP_NAMES = {"Fairy Tonic", "Bronze Dagger", "Leather Vest"};
	private static final String[] SHOP_DESCS = {"A gentle potion from your fairy companion.", "A basic blade for close fights.", "Light armor stitched from hide."};
	private static final int[] SHOP_OFF = {0, 3, 0};
	private static final int[] SHOP_DEF = {0, 0, 2};
	private static final double[] SHOP_COST = {12.0, 18.0, 16.0};
	
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
			showMenu();
			break;
		case 'I':
			showItems();
			break;
		case 'L':
			lookAround(here);
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
		case '+':
			equipItem(obj);
			break;
		case 'A':
			attackNPCs(here, profile);
			break;
		case 'P':
			showDebug();
			break;
		case 'M':
			openMerchantShop();
			break;
		case 'T':
			
			break;
		case 'H':
			showGuide();
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
		System.out.println("--------Inventory--------");
		if(inventory.size() == 0)
		{
			System.out.println("You don't have any items.");
			return;
		}
		else
		{
			System.out.println("You have the following items:");
			for(int x = 0; x < inventory.size(); x++)
			{
				Item item = inventory.get(x);
				System.out.printf(
					"%-18s DMG: %-3d DEF: %-3d SELL: $%1.2f%n",
					item.getName(),
					item.getOffensive(),
					item.getDefensive(),
					item.getValue()
				);
			}
		}	
	}

	private void equipItem(String obj)
	{
		if(obj.length() == 0 || inventory.size() == 0)
		{
			return;
		}
		
		String[] equipData = obj.split(" ", 2);
		if(equipData.length < 2)
		{
			return;
		}
		
		String slot = equipData[0].toUpperCase();
		String itemName = equipData[1];
		Item chosen = null;
		
		for(int x = 0; x < inventory.size(); x++)
		{
			if(inventory.get(x).getName().equalsIgnoreCase(itemName))
			{
				chosen = inventory.get(x);
				break;
			}
		}
		if(chosen == null)
		{
			return;
		}
		disarmItem(chosen);
		
		if(slot.equals("L"))
		{
			disarmItem(leftHand);
			leftHand = chosen;
		}
		else if(slot.equals("R"))
		{
			disarmItem(rightHand);
			rightHand = chosen;
		}
		else
		{
			return;
		}
		
		strength += chosen.getOffensive();
		armor += chosen.getDefensive();
		System.out.println("Equipped " + chosen.getName() + " in " + slot + " slot.");
	}

	private void disarmItem(Item item)
	{
		if(item == null)
		{
			return;
		}
		strength -= item.getOffensive();
		armor -= item.getDefensive();
		if(item == leftHand)
		{
			leftHand = null;
		}
		if(item == rightHand)
		{
			rightHand = null;
		}
	}

	private void attackNPCs(MapBlock here, Character[] profile)
	{
		boolean foundTarget = false;
		for(int x = 1; x <= Character.count; x++)
		{
			Character npc = profile[x];
			
			if(this.isHere(npc) && npc.combative)
			{
				foundTarget = true;
				attack(here, npc);
			}
		}
		
		if(!foundTarget)
		{
			System.out.println("There is nothing combative here to attack.");
		}
	}

	private void lookAround(MapBlock here)
	{
		System.out.println("--------Look--------");
		System.out.println("Location: (" + xpos + "," + ypos + ")");
		System.out.println(here.getTitle());
		System.out.println(here.getDesc());
		System.out.println("Exits:");
		showExitStatus("N", here.getN());
		showExitStatus("S", here.getS());
		showExitStatus("E", here.getE());
		showExitStatus("W", here.getW());
	}

	private void showExitStatus(String direction, int value)
	{
		if(value == 1)
		{
			System.out.println(direction + ": open");
		}
		else if(value == 0)
		{
			System.out.println(direction + ": blocked");
		}
		else
			{
				if(hasItem(value))
				{
					System.out.println(direction + ": requires keyID " + value + " (you have key)");
				}
				else
				{
					System.out.println(direction + ": requires keyID " + value);
				}
			}
	}

	private void showDebug()
	{
		System.out.println("DEBUG -> Position: (" + xpos + "," + ypos + ")");
		System.out.printf("DEBUG -> Gold: $%1.2f%n", gold);
		System.out.print("DEBUG -> Item keyIDs: ");
		if(inventory.size() == 0)
		{
			System.out.println("none");
			return;
		}
		for(int x = 0; x < inventory.size(); x++)
		{
			System.out.print(inventory.get(x).keyID);
			if(x < inventory.size() - 1)
			{
				System.out.print(", ");
			}
		}
		System.out.println();
	}

	private void showMerchantShop()
	{
		System.out.println("---- Fairy Merchant ----");
		System.out.printf("Your Gold: $%1.2f%n", gold);
		for(int i = 0; i < SHOP_NAMES.length; i++)
		{
			System.out.printf(
				"%-15s COST: $%1.2f DMG: %d DEF: %d%n",
				SHOP_NAMES[i], SHOP_COST[i], SHOP_OFF[i], SHOP_DEF[i]
			);
		}
		System.out.println("Buy with: B <item name>");
		System.out.println("Sell with: V <item name>");
		System.out.println("List with: L");
		System.out.println("Exit with: X");
	}

	private void openMerchantShop()
	{
		System.out.println("Your fairy companion appears beside you.");
		showMerchantShop();
		
		boolean inShop = true;
		while(inShop)
		{
			System.out.print("Shop > ");
			String input = scan.nextLine().trim();
			if(input.length() == 0)
			{
				continue;
			}
			
			char command = input.toUpperCase().charAt(0);
			String arg = "";
			if(input.length() > 1)
			{
				arg = input.substring(1).trim();
			}
			
			switch(command)
			{
			case 'L':
				showMerchantShop();
				break;
			case 'B':
				buyFromMerchant(arg);
				break;
			case 'V':
				sellToMerchant(arg);
				break;
			case 'X':
				System.out.println("The fairy merchant fades away.");
				inShop = false;
				break;
			default:
				System.out.println("Shop commands: L, B <item>, V <item>, X");
				break;
			}
		}
	}

	private void buyFromMerchant(String itemName)
	{
		if(itemName.length() == 0)
		{
			showMerchantShop();
			return;
		}
		
		for(int i = 0; i < SHOP_NAMES.length; i++)
		{
			if(SHOP_NAMES[i].equalsIgnoreCase(itemName))
			{
				if(gold < SHOP_COST[i])
				{
					System.out.println("Not enough gold.");
					return;
				}
				
				gold -= SHOP_COST[i];
				Item bought = new Item(SHOP_NAMES[i], SHOP_DESCS[i], SHOP_OFF[i], SHOP_DEF[i], SHOP_COST[i]);
				inventory.add(bought);
				System.out.println("You bought " + bought.getName() + ".");
				System.out.printf("Gold left: $%1.2f%n", gold);
				return;
			}
		}
		
		System.out.println("The fairy merchant does not sell that.");
	}

	private void sellToMerchant(String itemName)
	{
		if(itemName.length() == 0)
		{
			System.out.println("Sell what?");
			return;
		}
		
		for(int x = 0; x < inventory.size(); x++)
		{
			Item item = inventory.get(x);
			if(item.getName().equalsIgnoreCase(itemName))
			{
				disarmItem(item);
				Item sold = inventory.remove(x);
				gold += sold.getValue();
				System.out.println("You sold " + sold.getName() + ".");
				System.out.printf("Gold now: $%1.2f%n", gold);
				return;
			}
		}
		
		System.out.println("You don't have a " + itemName + ".");
	}
	
	//Unique dropItem method for player instead of Character class for NPCs
	//really because of the disarmItem method
	@Override
	public boolean dropItem(String itemName, MapBlock here)
	{
		for(int x = 0; x < inventory.size(); x++)
		{
			Item item = inventory.get(x);
			if(item.getName().equalsIgnoreCase(itemName))
			{
				disarmItem(item);
				
				Item removed = inventory.remove(x);
				here.addItem(removed);
				System.out.println("You dropped the " + removed.getName() + ".");
				return true;
			}
		}
		System.out.println("You don't have a " + itemName + ".");
		return false;
	}
	
	public void showMenu()
	{
		System.out.println("----------------------------");
		System.out.println("You can move using the following commands:");
		System.out.println("N: North");
		System.out.println("S: South");
		System.out.println("E: East");
		System.out.println("W: West");
		System.out.println("----------------------------");
		System.out.println("Besides moving, you also have these possible actions:");
		System.out.println("I: Show Items");
		System.out.println("L: Look");
		System.out.println("+: Equip item (example: + L Iron Sword)");
		System.out.println("A: Attack");
		System.out.println("P: Debug position + item IDs");
		System.out.println("M: Open fairy merchant");
		System.out.println("G: Get");
		System.out.println("T: Take/Steal");
		System.out.println("D: Drop");
		System.out.println("Q: Quit");
		System.out.println("----------------------------");
	}
	
    public void showGuide()
	{
		System.out.println("Welcome to the adventure! You are the player.");
	}
	
	private String prompt()
	{
		System.out.println("Move? > ");
		return scan.nextLine();
	}
}
