package byog.Core;

import byog.TileEngine.TETile;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byog.Core.Game class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            Game game = new Game();
            game.playWithInputString(args[0]);
//            Game game2 = new Game();
//            game2.playWithInputString("n5604146706287872352s");
//            TETile[][] test = game.playWithInputString("n888s");
//            TETile[][] test2 = game2.playWithInputString("n888s");
//            for (int x = 0; x < 50; x += 1) {
//                for (int y = 0; y < 50; y += 1) {
//                    if (test[x][y] != test2[x][y]) {
//                        System.out.println("Bad");
//                    } else {
//                        System.out.println("Good");
//                    }
//                }
//            }
            System.out.println(game.toString());
        } else {
            Game game = new Game();
            game.playWithKeyboard();
        }
    }
}
