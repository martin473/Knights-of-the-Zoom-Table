import java.util.*;
/**
 * Represents a combatant and it's stats, as well as combat options
 *
 * @Knights of the Zoom Table
 * @version (a version number or a date)
 */
public class Fighter
{
    private String name;
    protected int maxHP = 20;
    protected int currentHP = maxHP;
    protected int maxMP = 10;
    protected int currentMP = maxMP;
    protected int strength = 10;
    protected int magic = 10;
    protected int defense = 10;
    protected int resistance = 10;
    protected double defend = 0.5;
    protected boolean defending = false;

    /**
     * Constructor for objects of class Fighter
     */
    public Fighter(String aName)
    {
        name = aName;
    }

    public String getName()
    {
        return name;
    }
    public int getMaxHP()
    {
        return maxHP;
    }
    public int getCurrentHP()
    {
       return currentHP; 
    }

	public int getMaxMP()
	{
		return maxMP;
	}

	public int getCurrentMP()
	{
		return currentMP;
	}


    public int getStr()
    {
        return strength;
    }
    public int getMag()
    {
        return magic;
    }
    public int getDef()
    {
        return defense;
    }
    public int getRes()
    {
        return resistance;
    }
    public void setCurrentHP(int newHP)
    {
        currentHP = newHP;
    }
    public void setCurrentMP(int newMP)
    {
        currentMP = newMP;
    }
    public boolean isDefending()
    {
	    return this.defending;
    }
    
    	//generates random number from 0-2. Used to add random damage to attacks
    	public int random()
    	{
		int max = 2;
		int min = 0;
		int range = max - min + 1;
		return (int)(Math.random()*range) + min;
	}  

   	
	//return output string
	public String attack(Fighter target)
	{
		//set initial values
		String attackResult = "";
		int attackValue = 0;

		if(target.getCurrentHP() <= 0)
		{
			attackResult += this.getName() 
				+ " casually attacks a dead body.\nGross!\n";
		}
		//check to see if attack hits
		//miss method was deleted?
		else //if (toHit())
		{
			//set initial damage
			attackValue = ((int)(this.getStr()* 1.5) 
				- target.getDef());

			//floor damage to 0 so negative damage isn't dealt
			if (attackValue < 0)
			{
				attackValue = 0;
			}

			//add random value to attack damage
			attackValue += random();

			//check to see if target is defending
			//apply defense
			//update output if neccessary
			if (target.isDefending())
			{
				attackValue = applyDefense(attackValue);
				attackResult += target.getName() +
					" is defending!\n";
			}

			//deal damage
			target.currentHP -= attackValue;

			//if you crank the damage values up high enough
			//you can have int overflow and you'll
			//give the enemy a huge amount of hp instead
			//of killing them
	
			//update output
			attackResult += this.getName() + " does mean things to "
				+ target.getName() + " for "
				+ (Integer.toString(attackValue))
				+ " damage.\n";

			//check for death
			//floor target hp to 0
			//update output if necessary
			if (target.currentHP < 0)
			{
				target.currentHP = 0;
				attackResult += target.getName() 
					+ " dies a deadly death!\n";
			}
		}
		//return output
		return attackResult;
	}

	//cast fire is almost exactly the same as attack, except
	//	it hits every member of the
	//	defending party, and therefore takes Party as input instead of
	//	singular fighter
	//It also checks to see if self has enough MP. If it does, it casts
	//	the spell. If it doesn't it skips spell logic and prints 
	//	a "miss" output string
	//
	//Like attack, it has a special message for dead enemies. But it only
	//	skips the damage logic for dead enemies. If all enemies
	//	are dead the battle is over, so there is no case where this
	//	spell would damage 0 targets.
	public String castFire(Party enemies)
	{
		//initialize output
		
		String attackResult = "";

		//if character has enough MP
		if (this.getCurrentMP() >= 4)
		{
			//initialize attack values
			int attackValue = 0;
	
			//reduce MP by correct amount
			this.currentMP -= 4;

			attackResult += "-4 MP!\n\n";

			//loop through all enemies in party
			for (Fighter enemy : enemies.showParty())
			{
				//if enemy is already dead, print special
				//message and skip rest of logic in current
				//loop iteration 
				if (enemy.getCurrentHP() <= 0)
				{
					attackResult += enemy.getName()
					       + " was already dead "
					       + "and is now on fire.\n";
					continue;
				}
					
				//calculate initial damage
				attackValue = ((int)(this.getMag()*1.5) 
					- enemy.getRes());

				//floor to 0
				if (attackValue < 0)
				{
					attackValue = 0;
				}

				//add random amount
				attackValue += random();
	
				//check for defense
				//updating output if neccessary
				if (enemy.isDefending())
				{
					attackValue = applyDefense(attackValue);
					attackResult += enemy.getName() +
						" is defending!\n";
				}

				//apply damage
				enemy.currentHP -= attackValue;
		
				//same issue with attack values
				//
				//update output
				attackResult += this.getName() 
					+ " sets a fire on "
					+ enemy.getName() + " for "
					+ (Integer.toString(attackValue))
					+ " damage.\n";
			
				//check for death
				//floor enemy hp to 0, in case they are being healed
				//they aren't healed from negative values
				//update output if neccessary
				if (enemy.currentHP < 0)
				{
					enemy.currentHP = 0;
					attackResult += enemy.getName() 
						+ " dies a deadly death!\n";
				}
			}
		}
		
		//if character doesn't have enough mp
		else
		{
			attackResult += this.spellFailed();
		}
		
		//return all output representing attack
		return attackResult;

	}

	//this is the defend action for combat
	//it sets defend to true. When any physical attack is made against
	//this character it checks to see if the character is defending
	//and then applies that character's defense value to the damage
	public String defend()
	{
		this.defending = true;
		return (this.getName() + " hunkers in a bunker and defends.\n");
	}

	//this is used in the combat class to reset a character's defending
	//status at the beginning of their turn
	public void stopDefending()
	{
		this.defending = false;
	}

	//reduces damage from incoming attacks by defense amount
	public int applyDefense(int damage)
	{
		//math formula
		//defend removes X% damage from incoming damage
		//it should be a float of 1 or less representing
		//amount of damage to be removed. 1 - X would be
		//the damage remaining.
		//
		//if we convert the incoming int damage to a float,
		//and then multiply that by the % of damage remaining
		//we get a float that represents the new reduced damage
		//output.
		//
		//Instead of rounding we truncate and simultaneously get
		//the right data type for output by casting back into an it
		//
		//(truncate/convert)((convert)damage * (%damage remaining)
		return (int)(((double)damage) * (1 - defend));
	}
	
	
    //heals target fighter for mag + rand(0-2) HP
    //if target is healed, print one message
    //if you're out of MP print a failure message
    //if you have enough MP and fighter isn't healed, do heal logic
    //	and print heal message    
    public String heal(Fighter target)
    {
	    String attackResult = "";
	    int healAmount = 0;

		// if you have 2MP or more and target isn't fully healed
	    if((this.getCurrentMP() >=2) 
			    && (target.getCurrentHP() < target.getMaxHP()))
	    {
		//reduce self MP by 2, heal target, add message
	    	this.setCurrentMP(this.getCurrentMP() - 2);
	    	healAmount = this.getMag() + random();
	    	target.setCurrentHP(target.getCurrentHP() + healAmount);
		
		//if you heal above targets max HP, set current hp to = max hp
		if (target.getCurrentHP() > target.getMaxHP())
		{
			target.setCurrentHP(target.getMaxHP());
		}

		attackResult += (this.getName() + " heals " + target.getName()
			+ " for " + Integer.toString(healAmount) + " HP.\n");
	    }
	    //if they're already healed print a message
	    else if ((this.getCurrentMP() >=2) 
			    && (target.getCurrentHP() >= target.getMaxHP()))
	    {
		    attackResult += (target.getName() + " is already pretty"
			    + " healthy. " + this.getName() + " gives them"
			    + " an apple.\n");
	    }
	    //if you're out of mp print failure
	    else
	    {
		    attackResult += this.spellFailed();
	    }

	    return attackResult;
        
    }

    //Adds 0.25(floor) of max HP to current HP. Caps at max HP
    //same with MP except to 0.75(floor)
    public String meditate()
    {
        int HPheal = (int)(((float)this.getMaxHP())*0.25);
	int MPheal = (int)(((float)this.getMaxMP())*0.75);
	
	this.setCurrentHP(this.getCurrentHP() + HPheal);
	this.setCurrentMP(this.getCurrentMP() + MPheal);


	if (this.getCurrentHP() > this.getMaxHP())
	{
		this.setCurrentHP(this.getMaxHP());
	}

	if (this.getCurrentMP() > this.getMaxMP())
	{
		this.setCurrentMP(this.getMaxMP());
	}
	
	return this.getName() + " has healed:\n"
		+ Integer.toString(HPheal) + " HP\n"
		+ Integer.toString(MPheal) + " MP\n"
	        + "and found inner peace.\n"
		+ "-_- ahhh.\n";
    }

    //generic spell failure message
    public String spellFailed()
    {
        return this.getName() + " is too tired to do that.\n";
    }

    //printing a character gives its name, current hp, and mp
    public String toString()
    {
        return this.getName() + "\n" 
		+ "HP: " + this.getCurrentHP() + "/" + this.getMaxHP() + "\n"
		+ "MP: " + this.getCurrentMP() + "/" + this.getMaxMP() + "\n"
		+ "\n\n";
    }


}
