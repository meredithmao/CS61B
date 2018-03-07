package byog.Core;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CurrentGame extends KeyReader {
    String file = "saved_seed.txt";

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
            World worldObj = internalPlayWithInputString(stringInput);
            playWithKeyboardAfterLoad(worldObj);

        } catch (IOException s) {
            System.out.println("IOException is found");
        }
    }
}


