package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private static Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        rand = new Random(seed);
        MemoryGame game = new MemoryGame(40, 40);
        game.startGame();
    }

    public MemoryGame(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator

    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String rdmstring = "";
        for (int i = 0; i < n; i++) {
            int index = rand.nextInt(CHARACTERS.length - 1);
            rdmstring = rdmstring + Character.toString(CHARACTERS[index]);
        }
        return rdmstring;
    }

    public void drawFrame(String s, int round, boolean watch) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear();
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.text(20,  20, s);
        StdDraw.show();
        StdDraw.pause(1000);
        Font font2 = new Font("Monaco", Font.BOLD, 10);
        StdDraw.setFont(font2);
        StdDraw.text(5, 38, "Round: " + round);
        if (watch) {
            StdDraw.text(20, 38, "Watch!");
        } else {
            StdDraw.text(20, 38, "Type!");
        }
        if (round <= 7) {
            StdDraw.text(35, 38, ENCOURAGEMENT[round]);
        } else {
            StdDraw.text(35, 38, "HECK YEAH KEEP GOING!");
        }
        StdDraw.line(0, 36, 40, 36);
        StdDraw.show();
    }

    public void flashSequence(String letters, int round) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        char[] chars = letters.toCharArray();
        for (int i = 0; i < letters.length(); i++) {
            String s = Character.toString(chars[i]);
            drawFrame(s, round, true);
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String s = "";
        while (StdDraw.hasNextKeyTyped() && n > 0) {
            s = s + Character.toString(StdDraw.nextKeyTyped());
            drawFrame(s, n, false);
            n -= 1;
        }
        return s;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        StdDraw.clear();
        round = 1;
        gameOver = true;
        String rdmstring;
        //TODO: Establish Game loop
        while (gameOver) {
            drawFrame("Round: " + round, round, true);
            rdmstring = generateRandomString(round);
            flashSequence(rdmstring, round);
            playerTurn = true;
            while (playerTurn) {
                StdDraw.pause(3000);
                String player = solicitNCharsInput(round);
                if (!rdmstring.equals(player)) {
                    gameOver = false;
                    String fail = "Game " + "Over! " + "You made it to round ";
                    drawFrame(fail + round, round, true);
                }
                playerTurn = false;
            }
            round += 1;

        }


    }

}
