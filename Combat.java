import java.util.Scanner;
import java.lang.Math;
/**
 * Handles flow of combat
 * Makes calls to Fighter class for specific combat methods
 *
 * @Knights of the Zoom Table
 * @version (a version number or a date)
 */



public class Combat
{	
	Party heroes;
	Party enemies;
	Party everyone;
	Scanner userInput = new Scanner(System.in);
	String separator = "---------------------------\n\n"; 
	


	public Combat(Party heroes, Party enemies)
	{
		//set combat's heroes and enemies to current heroes and enemies
		this.heroes = heroes;
		this.enemies = enemies;
	
		//set up the scanner for future use

		//set everyone to a combination of heroes and enemies,
		//heroes first
		this.everyone = new Party();
		for (Fighter hero : heroes.showParty())
		{
			everyone.add(hero);
		}
		for (Fighter enemy : enemies.showParty()) 
		{
			everyone.add(enemy);
		}
	}

	//gets user input in expected way
	public String getInput()
	{
		return this.userInput.nextLine();
	}
	
	public String combatMenu()
	{
		return "What do you want to do?\n"
			+ "(A)ttack\n"
			+ "(D)efend\n"
			+ "(M)editate\n"
			+ "Cast (F)ire - 4MP\n"
			+ "Cast (H)eal - 2MP\n"
			+ "\n";
	}

	public boolean doCombat()
	{
		//create a turn counter and set it to zero
		//get size of everyone for use for modulus math
		int turn = 0;

		//converts size to correct number for index reference
		int groupSize = everyone.showParty().size();
		//
		//while both teams are alive, iterate through everyone
		//and take a turn, then increment the turn counter to take
		//the next person's turn
		while(!isDead(enemies) && !isDead(heroes))
		{
			//this is currently a recursive call
			System.out.print(separator);
			takeTurn(everyone.showParty().get(turn % groupSize));
			turn++;
		}
		
		//if all the enemies are dead return true for victory
		if(isDead(enemies))
		{
			System.out.print("Victory\n");
			reset();
			return true;
		}
		else
		{
			System.out.print("Defeat\n");
			reset();
			return false;
		}
	}


	//checks to see if a fighter's hp is above 0
	public boolean isDead(Fighter currentFighter)
	{
		if (currentFighter.getCurrentHP() > 0)
		{
			return false;
		}
		else return true;
	}

	//party size isDead
	public boolean isDead(Party currentParty)
	{
		int deadCounter = 0;
		for (Fighter fighter : currentParty.showParty())
		{
			if (isDead(fighter))
			{
				deadCounter++;
			}
		}

		if (deadCounter == currentParty.showParty().size())
		{
			return true;
		}
		
		else
		{
			return false;
		}
	}

	public boolean isPlayer(Fighter currentFighter)
	{
		boolean isAPlayer = false;


		for (Fighter match : heroes.showParty())
		{
			if (match.equals(currentFighter))
			{
				isAPlayer = true;
			}
		}

		return isAPlayer;
	}

	//represents a single turn
	//basic flow
	//	resets defending status
	//	skips turn if currentFighter is dead
	//	takes turn if currentFighter is alive
	//	if is a player
	//		ask for input in a loop
	//		if correct input is given, end loop and perform action
	//		print output
	//	if is a computer
	//		choose action based on semi-random logic
	//		print output
	public void takeTurn(Fighter currentFighter)
	{
		//if current character health above 0 take the turn
		//	otherwise skip
		
		//used for player controlled characters to track if player has 
		//entered correct input
		String userInput = " ";
		boolean waitingForUserInput = true;
		//used in player choice fight and heal to prevent repetetive code
		Fighter currentTarget;
		
		//reset defending status
		currentFighter.stopDefending();
		//if current fighter is dead skip turn
		if(isDead(currentFighter))
		{
			System.out.println(currentFighter.getName() +
					" lies there.");
		}

		//else take turn
		else
		{
			//if currently a player, print combat menu & get userinput
			//then perform correct action and end turn
			if(isPlayer(currentFighter))
			{
				System.out.print("It's " + currentFighter.getName() + "'s turn!\n");
				System.out.print(currentFighter);
				System.out.print(combatMenu());

				//waitingForUserInput starts as false, and is only set
				//	to true during a switch case that calls a character
				//	action
				while(waitingForUserInput)
				{
					//fixes bug of empty input
					userInput = getInput().toLowerCase();
					if(userInput.length() == 0)
					{
						userInput = " ";
					}
					//switch on first character of user input
					switch(userInput.charAt(0))
					{
						//attack
						case('a'):
							//ask for user input
							//print enemies
							//get userinput
							System.out.println("Who would"
								+ " you like to attack?"
								+ " (type the number)");
							System.out.print(showTargets(enemies));
							userInput = getInput();

							//if user input is a one or a two
							//hardcoded to a 2 person list 
							//for simplicity as all parties in this version
							//are of size 2
							if (verifyTargetInput(userInput))
							{
								//set the current target to be a fighter chosen by the player
								//	from a list of enemies
								currentTarget = chooseTarget(enemies, Integer.parseInt(userInput));
								//print the results of the attack on that target
								System.out.print(currentFighter.attack(currentTarget));
								//print the updated info on the target so the player can see how much damage was done
								System.out.print(updatedTargetInfo(currentTarget));
								waitingForUserInput = false;
								break;
							}
							else
							{
								System.out.println("Choose the correct enemy.");
								break;
							}
						//defend
						case('d'):
							System.out.print(
								currentFighter.defend());
							waitingForUserInput = false;
							break;
						//meditate
						case('m'):
							System.out.print(
								currentFighter.meditate());
							waitingForUserInput = false;
							break;
						//cast fire
						case('f'):
							System.out.print(
								currentFighter.castFire(enemies));
							for(Fighter enemy : enemies.showParty())
							{
								System.out.print(updatedTargetInfo(enemy));
							}
							waitingForUserInput = false;
							break;
						//heal
						//
						//exactly the same as attack, but different message
						//	uses heroes as input instead of enemies
						//	uses heal method instead of attack method
						//	has different wrong input code
						case('h'):
							System.out.print("Who would you"
								+ " like to heal? (type name)"
								+ "\n");
							System.out.print(showTargets(heroes));
							userInput = getInput();

							//if user input is a one or a two
							//hardcoded to a 2 person list 
							//for simplicity as all parties in this version
							//are of size 2
							if (verifyTargetInput(userInput))
							{
								//set the current target to be a fighter chosen by the player
								//	from a list of heroes
								currentTarget = chooseTarget(heroes, Integer.parseInt(userInput));
								//print the results of the heal spell on that target
								System.out.print(currentFighter.heal(currentTarget));
								//print the updated info on the target so the player can see how much damage was done
								System.out.print(updatedTargetInfo(currentTarget));
								
								waitingForUserInput = false;
								break;
							}
							else
							{
								System.out.println("Choose the correct friend.");
								break;
							}
						default:
							System.out.print("Type: a, d, m, f,"
								+ " or h.\n");
					}				
				}
			}

			//else if its a computer, perform "ai" procedure
			else
			{
				//set target to default to current fighter
				currentTarget = currentFighter;
				
				//chose a random ability (1-4) except for heal, 
				//	separate logic for heal below
				int choice = ((((int)(Math.random() * 4)) % 3) + 1); 
					//gives a slightly higher chance to choose action 1
				
				//overrides random choice if enemies are hurt
				//loop through enemies and if any has health below half
				//	make choice = 5 which is heal, and make that
				//	enemy the target
				//
				//	if current fighter doesn't have enough MP to cast heal
				//	override heal choice and choose meditate instead
				//for (Fighter enemy: enemies.showParty())
				{
				//        if (enemy.getCurrentHP() < (enemy.getMaxHP() / 2))
					{
				//		choice = 5;
				//		currentTarget = enemy;
					}
					if (currentFighter.getCurrentMP() < 2)
					{
				//		choice = 3;
					}
				}

				//similar to fighter but with numbers instead of characters
				//	and enemy/hero targets reversed
				switch(choice)
				{
					//attack
					case(1):
						//rand chooses a float between 0 and 1
						//	choose one hero or the other
						//	based on whether rand returns
						//	number above or below 0.5
						if(Math.random() > 0.5)
						{
							currentTarget = heroes.showParty().get(0);
						}
						else
						{
							currentTarget = heroes.showParty().get(1);
						}
						System.out.print(currentFighter.attack(currentTarget));
						break;
					//cast fire
					case(2):
						System.out.print(currentFighter.castFire(heroes));
						break;
				}
			}
		}
		//
		//end turn
	}

	//prints all fighters in a party along with the int that is expected to
	//be input into choose target
	public String showTargets(Party targetParty)
	{
		String targetList = "";
		int index = 1;
		for(Fighter target : targetParty.showParty())
		{
			targetList += (Integer.toString(index) + "\n"
				+ target);
			index ++;			
		}
		return targetList;
	}

	//attempts to return a fighter from the target party based on player choice
	//	if the int chosen is less than or greater than the party size
	//	returns null
	public Fighter chooseTarget(Party targetParty, int choice)
	{
		if((choice <= targetParty.showParty().size()) && (choice > 0))
		{
			//subtract 1 from choice to get a machine readable number
			choice -= 1;
			return targetParty.showParty().get(choice);
		}
		return null;
	}

	public boolean verifyTargetInput(String uIn)
	{
	
		if(uIn.equals("1") || uIn.equals("2"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//returns a string representing a fighters current HP and MP
	//intended to be used after an attack or heal to show the updated HP and MP
	public String updatedTargetInfo(Fighter target)
	{
		String output = "";
		output += "Look at " + target.getName() + " now.\n";
		output += "HP: " + target.getCurrentHP() + "/" + target.getMaxHP() + "\n";
		output += "MP: " + target.getCurrentMP() + "/" + target.getMaxMP() + "\n";
		return output;
	}

	//resets player party hp and mp for next battle
	public void reset()
	{
		for(Fighter hero : heroes.showParty())
		{
			hero.setCurrentHP(hero.getMaxHP());
			hero.setCurrentMP(hero.getMaxMP());
		}
	}

}
