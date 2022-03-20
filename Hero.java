import java.util.*;
/**
 * The main Hero, a specialized Fighter with a specific job to modify stats
 *
 * @Knights of the Zoom Table
 * @version (a version number or a date)
 */
public class Hero extends Fighter
{
    private String name, job;
    /**
     * Constructor for objects of class Hero
     */
    public Hero(String aName, String aJob)
    {
        super(aName);
        job = aJob;
        setStats(job);
    }
    public String getJob()
    {
        return job;
    }
    private void setStats(String theJob)
    {
        if(job.equalsIgnoreCase("Knight"))
        {
            maxHP = maxHP * 2;
            strength = strength;
            maxMP = maxMP / 2;
            magic = magic / 2;
            defense = defense * 2;
            resistance = resistance / 2;
        }
        else if(job.equalsIgnoreCase("Ninja"))
        {
            maxHP = maxHP;
            strength = strength * 2;
            maxMP = maxMP / 2;
            magic = magic / 2;
            defense = defense;
            resistance = resistance / 2;
        }
        else if(job.equalsIgnoreCase("Wizard"))
        {
            maxHP = maxHP / 2;
            strength = strength / 2;
            maxMP = maxMP * 2;
            magic = magic * 2;
            defense = defense / 2;
            resistance = resistance * 2;
        }
        else if(job.equalsIgnoreCase("Gladiator"))
        {
            maxHP = maxHP;
            strength = strength * 2;
            maxMP = maxMP;
            magic = magic * 2;
            defense = defense / 2;
            resistance = resistance / 2;
        }
        currentHP = maxHP;
        currentMP = maxMP;
    }      
}