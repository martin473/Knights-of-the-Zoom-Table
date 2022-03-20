import java.util.*;
/**
 * Represents a group or party of Fighters
 *
 * @Knights of the Zoom Table
 * @version (a version number or a date)
 */
public class Party
{
    private ArrayList <Fighter> aParty; 
    public Party ()
    {
        aParty = new ArrayList <Fighter>();
    }

    public void add(Fighter aFighter)
    {
        aParty.add(aFighter);
    }

    public Fighter remove(Fighter aFighter)
    {
        Fighter toRemove = new Fighter (aFighter.getName());
        aParty.remove(aFighter);
        return toRemove;
    }

    public Fighter getFighter(int index)
    {
        return aParty.get(index);
    }

    public ArrayList<Fighter> showParty()
    {
        return aParty;
    }
    
}

