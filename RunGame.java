import java.io.*;
/**
 * Contains the main method to run the program
 *
 * @Knights of the Zoom Table
 * @version (a version number or a date)
 */
public class RunGame
{
    public static void main (String [] args) throws IOException
    {
        GameEngine theGame = new GameEngine();
        theGame.run();
    }
}
