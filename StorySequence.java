import java.util.*;
import java.io.*;
/**
 * Non-combat events (story sequences) are handled here, and alignment
 * is adjusted accordingly
 *
 * @Knights of the Zoom Table
 * @version (a version number or a date)
 */
public class StorySequence
{
    private static String heroName = "Hero";
    private static String job = "Freelancer";
    private static String friendsName = "Friend";
    private static Scanner scanner = new Scanner (System.in);
    private static boolean finished = false;
    private static Hero hero;
    private static Fighter friend;
    private static Scanner fileScan;
    private static final String FILEPATH = "SAVE.TXT";    
    // represents leanings. 0 is neutral, positive is Windowusa,
    // negative is Applesia
    // stored here as well for save purposes
    private static int alignment = 0;
    private static void setParty (Party theParty)
    {
        Fighter hero = theParty.showParty().get(0);
        String heroName = hero.getName();        
        Fighter friend = theParty.showParty().get(1);
        String friendsName = friend.getName();
    }
    //loads save data
    /**
     * Load operation here using instance variables to create the player 
     * party and set chapter
     */
    public static boolean load ()
    {
        System.out.println("Load a save file?");
        String decision = scanner.nextLine();
        if(decision.equalsIgnoreCase("Yes"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //loads player party
    public static Party loadParty() throws IOException
    {
        FileReader toLoad = new FileReader(FILEPATH);
        fileScan = new Scanner (new File(FILEPATH));
        heroName = fileScan.nextLine();
        job = fileScan.nextLine();
        friendsName = fileScan.nextLine();
        Hero hero = new Hero (heroName, job);
        friend = new Fighter (friendsName);
        Party playerParty = new Party();
        playerParty.add(hero);
        playerParty.add(friend);
        return  playerParty;
    }
    //Sends back both the chapter and alignment as an array of ints
    //[0] is the chapter, [1] is the Alignment
    public static int[] loadChapterAlignment() throws IOException 
    {
        FileReader toLoad = new FileReader(FILEPATH);
        fileScan = new Scanner (new File(FILEPATH));
        int [] chapterAlignment = new int [2];
        fileScan.nextLine();
        fileScan.nextLine();
        fileScan.nextLine();
        chapterAlignment[0] = fileScan.nextInt();
        chapterAlignment[1] = fileScan.nextInt();
        toLoad.close();
        return chapterAlignment;
    }
    //Saves to the save file
    public static void save (int chapter) throws IOException
    {
        System.out.println("Save game?");
        String toSave = scanner.nextLine();
        
        if (toSave.equalsIgnoreCase("Yes"))
        {
            PrintWriter toWrite = new PrintWriter(FILEPATH);
            toWrite.println(heroName);
            toWrite.println(job);
            toWrite.println(friendsName);
            toWrite.println(chapter);
            toWrite.println(alignment);
            toWrite.close();
        }
        else if (toSave.equalsIgnoreCase("No"))
        {

        }
        else
        {
            System.out.println("Yes or no?");
            save(chapter);
        }
    }
    //creates the player character

    public static Party characterCreation()
    {
        System.out.println("Welcome to the world of Javrosia!");

        int failures = 0;
        while (finished == false)
        {
            System.out.println("Please tell me your name?");
            heroName = scanner.nextLine();
            boolean jobless = true;
            String confirm = "";
            while (jobless)
            {
                boolean confirmed = false;

                System.out.println("And what job truly suits you?");
                System.out.println("NOTE: Once selected you CANNOT change jobs\n");
                System.out.println("Knight: A durable fighter with passable physical attack power, who suffers with magic");
                System.out.println("Ninja: A moderately durable fighter with top notch offense, who suffers against magic");
                System.out.println("Wizard: A weak physical fighter with poor durability, but with excellent magical skills");
                System.out.println("Gladiator: A powerful attacker with magic or a weapon, but with lacking defense");
                System.out.println("Freelancer: The embodiment of jack-of-all-trades master-of-none. All abilities are average\n");
                job = scanner.nextLine();
                if (job.equalsIgnoreCase("Knight") || job.equalsIgnoreCase("Ninja") ||job.equalsIgnoreCase("Wizard") || job.equalsIgnoreCase("Gladiator") || job.equalsIgnoreCase("Freelancer"))
                {
                    System.out.println("Your name is " + heroName + " and you are a " + job + ". Is that correct?");

                    while (confirmed == false)
                    {
                        confirm = scanner.nextLine();
                        if (confirm.equalsIgnoreCase("Yes"))
                        {
                            confirmed = true;
                            jobless = false;

                        }
                        else if (confirm.equalsIgnoreCase("No"))
                        {
                            System.out.println("Let's try again then.");
                            confirmed = true;
                        }
                        else
                        {
                            System.out.println("Please enter a valid answer");
                            failures ++;
                            if (failures >= 5)
                            {
                                System.out.println("Numbskull...");
                            }
                        }
                    }
                }
                else
                {
                    failures ++;
                    System.out.println("Please pick an ACTUAL job...");
                    if (failures >= 5)
                    {
                        System.out.println("Buncha morons...");
                    }
                }
            }
            System.out.println("And what is your best friend's name?");
            friendsName = scanner.nextLine();
            System.out.println("So just to recap:\nYour name is " + heroName + " and you are a " + job +".\nYour best friend's name is " + friendsName + ".");
            System.out.println("Correct?");
            confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("Yes"))
            {
                System.out.println("Excellent. Moving on...");
                finished = true;
            }
            else if (confirm.equalsIgnoreCase("No"))
            {
                System.out.println("Once more from the beginning.");
            }
            else
            {
                System.out.println("C'mon, man. ");
                failures ++;
                if (failures >= 5)
                {
                    System.out.println("Might I recommend a course in remedial computers?");
                }
            }
        }
        System.out.println("Now our adventure unfolds...\n\n");
        Party party = new Party();
        Hero hero = new Hero (heroName, job);
        Fighter friend = new Fighter (friendsName);
        party.add(hero);
        party.add(friend);
        return party;
    }

    public static boolean sequence1()
    {
        System.out.println("And behold, like a bat out of hell\nA hero rises to fight\nThe opposing forces splitting the land\nAnd looks like a boss all the while\n\t\t~Users manual 4:20");
        System.out.println("\nPROLOGUE: The Oracle\n\n");

        System.out.println(friendsName + ": " + heroName + " did you even hear a word that the Oracle said?");
        System.out.println(friendsName + ": No helping you is there? The Oracle Appi said that you have a destiny deciding the fate of Javrosia and of Sillicon Heaven");
        System.out.println(friendsName + ": Look! Armies approaching. This will soon be a battle between the armies of Windowusa and Applesia. We'd best hide.");
        System.out.println("After a considerable amount of time hiding in the bushes, not very bosslike I might add...");
        System.out.println(friendsName + ": It's a bloodbath out there. I can't look at even a bit of it. Incoming!");
        System.out.println("Windowusan Soldier: Hide me!");
        System.out.println(friendsName + ": Go away! We're staying out of this! (despite the prophecy...)");

        boolean chosen = false;
        boolean decision = true;
        String choice;
        while (chosen == false)
        {
            System.out.println("Help the Windowusan?");            
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("yes"))
            {
                System.out.println("\n\nThe surprisingly gassy Windowusan is greatful for shelter. You maybe not so much");
                System.out.println(friendsName + ": It's overflowing in here. Someone open a window!");
                System.out.println(friendsName + ": Ack! Here comes the pursuer!");
                decision = true;
                chosen = true;
            }
            else if (choice.equalsIgnoreCase("No"))
            {
                System.out.println("\n\nWindowusan: Fine I'll make my task killing you and then steal your cover");
                decision = false;
                chosen = true;
            }
            else
            {
                System.out.println("You didn't think neutrality really was an option did you?");
            }
        }
        return decision;
    }

    public static int sequence1Windows() throws IOException
    {
        System.out.println("\nWindowusan: Thank you! Now I can go home having cleared out the enemy!");
        System.out.println("The soldier leaves, and suddenly it's like the air is less green");
        System.out.println(friendsName + ": It would appear King Geitz isn't picky about who he employs. Though the same is probably true of Emperor Joobs considering how easily he went down");
        System.out.println(friendsName + ": I hope you realize, " + heroName + ", that we're now on their list. The Applesians can't be pleased at this");
        System.out.println(friendsName + ": Maybe there's something to this prophecy stuff after all?");
        alignment++;
        save(1);
        return 1;
    }

    public static int sequence1Apple() throws IOException
    {
        System.out.println("The pursuing Applesian soldier arrives");
        System.out.println("Applesian: Well this is a welcome surprise! Did my job for me. Lord Joobs will be pleased.");
        System.out.println("The soldier leaves");
        System.out.println(friendsName + ": I'm pretty sure we just made an enemy of King Geitz. Ah well what can you do?");
        System.out.println(friendsName + ": Maybe there's something to this prophecy stuff after all?");
        alignment--;
        save(1);
        return 1;
    }

    public static boolean sequence2(Party theParty) 
    {

        setParty(theParty);
        System.out.println("And lo how the woods confused!\nTheir twisty turns and their prickly thorns dare not deter a hero" +
            "\nAnd behold the hero arrives at the ruins of the Ancients\n\t\t~Users Manual 19:70\n");
        System.out.println("CHAPTER 1: The Lost Kingdom\n\n");
        System.out.println("The party has arrived in the woods of Linx. Millenia ago, this was the kingdom of Unux.");
        System.out.println("To avoid either army they went to this wasteland where not even the garbage collector would find them.");
        System.out.println(friendsName + ": I swear we've passed this rock five times now.");
        System.out.println("It had only been two. " + friendsName + " can be a real drama queen at times. A total diva really");
        System.out.println(friendsName + ": Like I said, we've passed that rock TWO times now. I think we're lost");
        System.out.println(friendsName + ": They say the ghost of Unux's leader, Sir Richy roams these plains. But that's just a ghost story.");
        System.out.println("Right on cue, the ghost of Unux past appears behind the party");
        System.out.println("Richy: DIE!!!!!!!!!!!!!!!!");
        System.out.println(friendsName + ": Die? Really? Cliche much?");
        System.out.println("Richy: I've spent the better part of a millenium waiting. It wasn't a cliche back in my day.");
        System.out.println("Richy: I smell blood. I shall attack your enemies! They shall pay for harming my new friends!");
        System.out.println("Richy: Who are your enemies? Applesia or Windowusa?");
        boolean chosen = false;
        boolean decision = true;
        while (chosen == false)
        {
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Applesia"))
            {
                decision = true;
                chosen = true;
            }
            else if (choice.equalsIgnoreCase("Windowusa"))
            {
                decision = false;
                chosen = true;
            }
            else 
            {
                System.out.println("Richy: I MUST KNOW THE NAME TO MURDER!");
            }
        }
        System.out.println("Richy: Very good. But first a battle to warm up! You knew it was coming!");
        System.out.println("And with my pet dragon-dog thing!");
        return decision;
    }

    public static int sequence2Windows(Party theParty) throws IOException
    {
        setParty(theParty);
        System.out.println("Richy: You are indeed strong. I shall not rest until the fields of Applesia are as red as a Macintosh Apple.");
        System.out.println(friendsName + ": That is... remarkably specific. And visual.");
        System.out.println("Richy left in a flash, something all too common among the residents of Javrosia.");
        System.out.println(friendsName + ": " + heroName + " I hope you realize the Applesians aren't gonna be happy...");
        System.out.println(friendsName + ": Little by little, Appi's prophecy is coming true...");
        alignment++;
        save(2);
        return 2;
    }

    public static int sequence2Apple(Party theParty) throws IOException
    {
        setParty(theParty);
        System.out.println("Richy: You are indded strong. I shall not rest until the Windowusans see a screen of death when I strike out of the blue!");
        System.out.println(friendsName + ": Man, you really forced that one.");
        System.out.println("Richy left in a flash, something all too common among the residents of Javrosia.");
        System.out.println(friendsName + ": " + heroName + " I hope you realize the Windowusans aren't gonna be happy...");
        System.out.println(friendsName + ": Little by little, Appi's prophecy is coming true...");        
        alignment--;
        save(2);

        return 2;
    }

    public static int sequence3(Party theParty) 
    {
        setParty(theParty);
        System.out.println("\n\nThe beast lurks in the shadows. \nBut the chosen savior shall pwn the beast! \nThe beast of Mammon shall be struck down!\n\t\tUsers Manual: 6:66\n");
        System.out.println("CHAPTER 2: Fate of the World\n\n");
        System.out.println("Deciding to face destiny head-on, " + heroName + " arrived at the plains dividing the two great kingdoms, the Central Prairie Unit");
        System.out.println(friendsName + ": Bodie's everywhere... There was a massive clash here.");
        System.out.println(friendsName + ": But these soldiers' wounds don't look like a human did it. Exactly 64 bites in every soldier...");
        System.out.println("In the distance the sounds of steel clashing was suddenly drowned out by a thunderous roar");
        System.out.println(friendsName + ": WHAT THE %#@& WAS THAT!?");
        System.out.println("After much complaining, and the promise of new underwear, " + heroName + " convinced his friend to head towards the source of the sound.");
        System.out.println("A massive symbol was etched into the earth itself. Ahead was a three way battle between the two kingdoms and a massive fiery fox.");
        System.out.println(friendsName + ": That symbol, the fools summoned Mammon to take out their enemies only for the Fire Fox itself to turn on them.");
        boolean chosen = false;
        String choice;
        int decision = 0;
        while (chosen == false)
        {
            System.out.println(friendsName + ": They're coming! One of each faction! Quick pick one to fight with and maybe it'll leave us alone!");
            System.out.println("Choose Windowusa, Applesia, or Mammon");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase ("Windowusa"))
            {
                decision = 0;
                chosen = true;
            }
            else if (choice.equalsIgnoreCase("Applesia"))
            {
                decision = 1;
                chosen = true;
            }
            else if (choice.equalsIgnoreCase("Mammon"))
            {
                decision = 2;
                chosen = true;
            }
            else
            {
                System.out.println("No time for that!");
            }
        }
        return decision;
    }

    public static int sequence3Windows(Party theParty) throws IOException
    {
        setParty(theParty);
        System.out.println("Windowusan: Thank you! The Beast was a nightmare...");
        System.out.println(friendsName + ": Who was stupid to summon that thing!?");
        System.out.println("Windowusan: ...");
        System.out.println(friendsName + ": We're due an answer!");
        System.out.println("Windowusan: Lord Geitz and Joobs both summoned it. In an attempt to end this war and see who deserves passage to Sillicon Heaven.");
        System.out.println(friendsName + ": WHAT!? They finally work together on THAT!?");
        System.out.println("Windowusan: As you can see it didn't work. This won't end until one or both of them is dead. Best of luck. Peace!");
        System.out.println("The Windowusan vanished into the distance");
        System.out.println(friendsName + ": I feel like the end is near...");
        alignment++;
        save(3);
        return 3;
    }

    public static int sequence3Apple(Party theParty) throws IOException
    {
        setParty(theParty);
        System.out.println("Applesian: Thank you! The Beast was a nightmare...");
        System.out.println(friendsName + ": Who was stupid to summon that thing!?");
        System.out.println("Applesian: ...");
        System.out.println(friendsName + ": We're due an answer!");
        System.out.println("Applesian: Lord Joobs and Geitz both summoned it. In an attempt to end this war and see who deserves passage to Sillicon Heaven.");
        System.out.println(friendsName + ": WHAT!? They finally work together on THAT!?");
        System.out.println("Applesian: As you can see it didn't work. This won't end until one or both of them is dead. Best of luck. Peace!");
        System.out.println("The Applesian vanished into the distance");
        System.out.println(friendsName + ": I feel like the end is near...");
        alignment--;
        save(3);
        return 3;
    }

    public static int sequence3Neutral(Party theParty) throws IOException
    {
        setParty(theParty);
        System.out.println("Mammon: Thank you! They wouldn't leave me alone!");
        System.out.println(friendsName + ": It talked!");
        System.out.println("Mammon: Why does that surprise you?");
        System.out.println(friendsName + ": Where do I start? Who summoned you anyways?");
        System.out.println("Mammon: Geitz and Joobs both summoned me. In an attempt to end this war and see who deserves passage to Sillicon Heaven.");
        System.out.println(friendsName + ": WHAT!? They finally work together on THAT!?");
        System.out.println("Mammon: As you can see it didn't work. This won't end until one or both of them is dead. Best of luck. Peace! Back to Sillicon Hell with me!");
        System.out.println("Mammon vanishes as does the summoning sigil");
        System.out.println(friendsName + ": I feel like the end is near...");
        save(3);
        return 3;
    }

    public static int finaleWindows(Party theParty) 
    {

        setParty(theParty);
        System.out.println("\n\n");
        System.out.println("And thus the end was near!\nThrough much tribulation the hero reaches the end\n\t\tUsers Manual 32:64\n");
        System.out.println("FINALE: Crashing Applesia\n\n");
        System.out.println("Geitz arrived out of the castle and approached " + heroName +".");
        System.out.println("Geitz: I have seen your actions. You must help me put an end to the Applesians! Sillicon heaven is in reach!");
        System.out.println(friendsName + ": A myth. This war for a myth.");
        System.out.println("Geitz: Not a myth! We shall live eternally in Sillicon Heaven once we have taken out Joobs!");
        System.out.println("Joobs teleports in when nobody was on the watch.");
        System.out.println("Joobs: It is indeed real. But my kingdom will be the one to reach it!");
        System.out.println(friendsName + ": Sorry, " + heroName + " but he has a point...");
        System.out.println("Geitz: " + heroName + "! You are the one Appi prophesied! Join me!");
        System.out.println(friendsName + ": Yeah I'll be joining Joobs.");
        System.out.println("Geitz: Let's finish these fools!");

        return 4;
    }

    public static int finaleApple(Party theParty) 
    {
        setParty(theParty);
        System.out.println("\n\n");
        System.out.println("And thus the end was near!\nThrough much tribulation the hero reaches the end\n\t\tUsers Manual 32:64\n");
        System.out.println("FINALE: Crashing Windowusa\n\n");
        System.out.println("Joobs arrived out of the castle and approached " + heroName +".");
        System.out.println("Joobs: I have seen your actions. You must help me put an end to the Windowusans! Sillicon heaven is in reach!");
        System.out.println(friendsName + ": A myth. This war for a myth.");
        System.out.println("Joobs: Not a myth! We shall live eternally in Sillicon Heaven once we have taken out Geitz!");
        System.out.println("Geitz teleports in so softly he must have been micro in order to avoid detection.");
        System.out.println("Geitz: It is indeed real. But my kingdom will be the one to reach it!");
        System.out.println(friendsName + ": Sorry, " + heroName + " but he has a point...");
        System.out.println("Joobs: " + heroName + "! You are the one Appi prophesied! Join me!");
        System.out.println(friendsName + ": Yeah I'll be joining Geitz.");
        System.out.println("Joobs: Let's finish these fools!");

        return 4;
    }

    public static int finaleNeutral (Party theParty) 
    {
        setParty(theParty);
        System.out.println("\n\n");
        System.out.println("And thus the end was near!\nThrough much tribulation the hero reaches the end\n\t\tUsers Manual 32:64\n");
        System.out.println("FINALE: A New System\n\n");
        System.out.println("Out of nowhere, both Geitz and Joobs appeared before " + heroName + ".");
        System.out.println("You've done quite a lot of damage to us both. I think we can put our differences aside long enough to kill you!");
        System.out.println(friendsName + ": Why the war!? Why the carnage!?");
        System.out.println("Geitz and Joobs: Sillicon Heaven!");
        System.out.println(friendsName + ": A myth. This war for a myth.");
        System.out.println("Geitz and Joobs: Not a myth! We shall live eternally in Sillicon Heaven once we have taken out Joobs/Geitz!");
        System.out.println("A long awkward silence passed as the two stared each other down.");
        System.out.println("Geitz and Joobs: " + heroName + ", join me! You are the one Appi prophesied!");
        System.out.println("More awkward silence...");
        System.out.println(friendsName + ": You sure you're enemies...?");
        System.out.println("Geitz and Joobs: YES!");
        System.out.println(friendsName + ": Enough of this. Let's end this once and for all!");
        return 4;
    }

    public static int endingWindows()
    {
        System.out.println(friendsName + ": I was wrong... I'll see you in Sillicon Heaven my friend! *dies*");
        System.out.println("Joobs: Geitz, you won. Take care of my kingdom and people. *dies*");
        System.out.println("Geitz: Well that was anticlimactic.");
        System.out.println("Geitz: We have done it! Sillicon Heaven is ours! Our world will be blessed and you will recieve any riches you desire!");
        System.out.println("Geitz: Don't worry about them they're not dead. How many people say \"asterisk dies asterisk \" as they die?");
        System.out.println("He was right. They were alive. Turns out " + friendsName + " dramatic tendencies were contagious.");
        System.out.println("Joobs surrendered the rights to Sillicon Heaven. The battle spawned for the copywrite was at last over");
        return 5;
    }

    public static int endingApple()
    {
        System.out.println(friendsName + ": I was wrong... I'll see you in Sillicon Heaven my friend! *dies*");
        System.out.println("Geitz: Joobs, you won. Take care of my kingdom and people. *dies*");
        System.out.println("Joobs: Well that was anticlimactic.");
        System.out.println("Joobs: We have done it! Sillicon Heaven is ours! Our world will be blessed and you will recieve any riches you desire!");
        System.out.println("Joobs: Don't worry about them they're not dead. How many people say \"asterisk dies asterisk \" as they die?");
        System.out.println("He was right. They were alive. Turns out " + friendsName + " dramatic tendencies were contagious.");
        System.out.println("Geitz surrendered the rights to Sillicon Heaven. The battle spawned for the copywrite was at last over");
        return 5;
    }

    public static int endingNeutral()
    {
        System.out.println("Geitz and Joobs: NO! One of us is supposed to be the winner! Now who gets the rights to Sillicon Heaven!?");
        System.out.println(friendsName + ": You could always...share?");
        System.out.println("And share they did. The old partners split the rights to Sillicon Heaven, and made untold fortunes.");
        System.out.println(friendsName + ": Let's go home, " + heroName + " this has been enough for one lifetime.");
        return 5;
    }

    public static int credits()
    {
        System.out.println("THE END");
        System.out.println("Designed by the Knights of the Zoom Table");
        System.out.println("Team leader and story writer (sorry!): Seth Malin");
        System.out.println("Battle system: Martin Lynn");
        System.out.println("Character system: Matt SanSouci"); 
        return 6;
    }

    public static void gameOver()
    {
        System.out.println("You lose\nTime to re-evaluate your life choices...");
    }
}
