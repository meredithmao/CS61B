package byog.Core;
import byog.TileEngine.TERenderer;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class CurrentGame extends KeyReader{
    String file = "saved_seed.txt";
    private static TERenderer ter = new TERenderer();

    //empty constructor
    CurrentGame() {
    }

    //constructor of a world
    CurrentGame(World worldObj) {
        this.world = worldObj.world;
        this.seed = worldObj.seed;
        this.fatWASDstring = worldObj.fatWASDstring;
        System.out.println(seed + fatWASDstring);

    }

    //method to save the game
    public void savingGame() {
        //creates a currentGame instance called gameSaved to save the current game
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print(seed + "s" + fatWASDstring);
            writer.close();

        } catch (IOException s) {
            System.out.println("IOException is found");
        }
        //method should pop up a window that indicates that game is saved
    }

    //method to load the game
    public void loadingGame() {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(file));
            String stringInput = new String(bytes, StandardCharsets.UTF_8);
            //the world class that contains this world before you set it to this.world has the proper player coords but it is not transferred
            World worldObj = playWithInputStringAfterLoad(stringInput);
            System.out.println(worldObj.playerX + "&" + worldObj.playerY);
            playWithKeyboardAfterLoad(worldObj);

        } catch (IOException s) {
            System.out.println("IOException is found");
        }
    }
}


