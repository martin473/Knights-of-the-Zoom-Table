import java.util.*;
import java.io.*;
/**
 * Handles the progression of events in the game
 *
 * @Knights of the Zoom Table
 * @version (a version number or a date)
 */
public class GameEngine 
{
    //Represents current location for save data
    private int chapter = 0;
    // represents leanings. 0 is neutral, positive is Windowusa,
    // negative is Applesia
    private int alignment = 0;
    private Hero theHero = new Hero ("Hero","Freelancer");
    private Fighter theFriend = new Fighter ("Friend");
    private Party playerParty;
    private Party enemyParty;
    private Fighter windowsSoldier1 = new Fighter ("Windowusan Soldier 1");
    private Fighter windowsSoldier2 = new Fighter ("Windowusan Soldier 2");
    private Fighter windowsSoldier3 = new Fighter ("Windowusan Soldier");
    private Fighter appleSoldier1 = new Fighter ("Applesian Soldier 1");
    private Fighter appleSoldier2 = new Fighter ("Applesian Soldier 2");    
    private Fighter appleSoldier3 = new Fighter ("Applesian Soldier");
    private Fighter ghost = new Fighter ("Ghost of King Richy");
    private Fighter ghost2 = new Fighter ("Denys");
    private Fighter mammon = new Fighter ("Mammon");
    private Fighter geitz = new Fighter ("Geitz");
    private Fighter joobs = new Fighter ("Joobs");
    private Combat battle;
    boolean alreadyLoaded = false;
    boolean loading = false;

    /**
     * Constructor is empty, run method will contain the code to run the
     * program. 
     */
    public GameEngine()
    {
        loading = StorySequence.load();
    }

    /**
     * Handles running the game. Battle calls are commented out until proper syntax is figured out
     */
    public void run() throws IOException
    {
        boolean branch;

        if (alreadyLoaded == false)
        {
            if (loading == true)
            {
                playerParty = StorySequence.loadParty();
                int [] chapterAlignment = new int [1];
                chapterAlignment = StorySequence.loadChapterAlignment();
                chapter = chapterAlignment[0];
                alignment = chapterAlignment[1];

                alreadyLoaded = true;
            }
        }
        if (chapter == 0)
        {
            playerParty = StorySequence.characterCreation();
            branch = StorySequence.sequence1();
            //initializes/resets the enemy party
            enemyParty = new Party();
            if (branch == true)
            {
                enemyParty.add(appleSoldier1);
                enemyParty.add(appleSoldier2);

                //create a new battle with current enemies 
                battle = new Combat(playerParty, enemyParty);

                //Combat returns true if successful, so checks for false 

                if (!battle.doCombat())
                {
                    StorySequence.gameOver();
                }
                chapter = StorySequence.sequence1Windows();
                alignment++;
            }
            else
            {
                enemyParty.add(windowsSoldier1);
                //added second soldier and combat constructor
                enemyParty.add(windowsSoldier2);
                battle = new Combat(playerParty, enemyParty);
                if (!battle.doCombat())
                {
                    StorySequence.gameOver();
                }
                chapter = StorySequence.sequence1Apple();
                alignment--;

            }
            run();
        }
        else if (chapter == 1)
        {
            //save method
            branch = StorySequence.sequence2(playerParty);
            enemyParty = new Party();
            enemyParty.add(ghost);
            enemyParty.add(ghost2);
            battle = new Combat(playerParty, enemyParty);
            if (!battle.doCombat())
            {
                StorySequence.gameOver();
            }
            if (branch == true)
            {
                chapter = StorySequence.sequence2Windows(playerParty);
                alignment++;

            }
            else
            {
                chapter = StorySequence.sequence2Apple(playerParty);
                alignment--;

            }
            run();
        }
        else if (chapter == 2)
        {
            //save method
            int split = StorySequence.sequence3(playerParty);
            if (split == 0)
            {
                enemyParty = new Party();
                enemyParty.add(appleSoldier3);
                enemyParty.add(mammon);
                battle = new Combat(playerParty, enemyParty);
                if (!battle.doCombat())
                {
                    StorySequence.gameOver();
                }
                chapter = StorySequence.sequence3Windows(playerParty);
                alignment++;

            }
            else if (split ==1)
            {
                enemyParty = new Party();
                enemyParty.add(windowsSoldier3);
                enemyParty.add(mammon);
                battle = new Combat(playerParty, enemyParty);
                if (!battle.doCombat())
                {
                    StorySequence.gameOver();
                }
                chapter = StorySequence.sequence3Apple(playerParty);
                alignment--;

            }
            else
            {
                enemyParty = new Party();
                enemyParty.add(appleSoldier3);
                enemyParty.add(windowsSoldier3);
                battle = new Combat(playerParty, enemyParty);
                if (!battle.doCombat())
                {
                    StorySequence.gameOver();
                }
                chapter = StorySequence.sequence3Neutral(playerParty);
            }
            run();
        }
        else if (chapter == 3)
        {
            //save method
            if (alignment > 0)
            {
                chapter = StorySequence.finaleWindows(playerParty);
                playerParty.remove(theFriend);
                playerParty.add(geitz);
                enemyParty = new Party ();
                enemyParty.add(theFriend);
                enemyParty.add(joobs);
                battle = new Combat(playerParty, enemyParty);
                if (!battle.doCombat())
                {
                    StorySequence.gameOver();
                }
            }
            else if (alignment < 0)
            {
                chapter = StorySequence.finaleApple(playerParty);
                playerParty.remove(theFriend);
                playerParty.add(joobs);
                enemyParty = new Party();
                enemyParty.add(theFriend);
                enemyParty.add(geitz);
                battle = new Combat(playerParty, enemyParty);
                if (!battle.doCombat())
                {
                    StorySequence.gameOver();
                }
            }
            else
            {
                chapter = StorySequence.finaleNeutral(playerParty);
                enemyParty = new Party();
                enemyParty.add(geitz);
                enemyParty.add(joobs);
                battle = new Combat(playerParty, enemyParty);
                if (!battle.doCombat())
                {
                    StorySequence.gameOver();
                }
            }
            run();
        }
        else if (chapter == 4)
        {
            if (alignment >  0)
            {
                chapter = StorySequence.endingWindows();
            }
            else if (alignment < 0)
            {
                chapter = StorySequence.endingApple();
            }
            else
            {
                chapter = StorySequence.endingNeutral();
            }
            run();
        }
        else if (chapter == 5)
        {
            StorySequence.credits();
        }
    }
}
