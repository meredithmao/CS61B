package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.util.Random;

public class Game {
    private static TERenderer ter = new TERenderer();
//    /* Feel free to change the width and height. */
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    Random seedRandomizer;
    long seedValue;
    static TETile[][] finalWorldFrame;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    private Random seedMaker(String input) {
        String seed = "";
        for (int i = 0; i < input.length(); i++) {
            char a = input.charAt(i);
            if (a >= 48 && a < 58) {
                seed += (a + "");
            }
        }
        seedValue = Long.parseLong(seed);
        return new Random(seedValue);
    }

    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        //converting the input to a char array
//        ter.initialize(50, 50);
        seedRandomizer = this.seedMaker(input);
//        char [] inputChar = input.toCharArray();
//        String [] seed = new String [input.length()];
//        //checks to see if the first value in your input starts with N
//        if (inputChar[0] == 'n' || inputChar[0] == 'N') {
//            //going through the rest of the input string
//            for (int i = 1; i < input.length() - 1; i++) {
//                //check if the next character is a number
//                if (Character.isDigit(inputChar[i])) {
//                    //storing the characters as a string
//                    seed[i] = String.valueOf(inputChar[i]);
//                }
//            }
//
//        }


        //making the the integer from the string seed
//        for (int i = 1; seed[i] != null; i++) {
//            seedValue = seedValue + Long.parseLong((String.valueOf(seed[i])));
//

        World finalworld = new World();
        finalWorldFrame = finalworld.finalWorldgenerator(seedRandomizer);
//        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }
}
