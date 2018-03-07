package byog.Core;

//import edu.princeton.cs.introcs.StdDraw;

public class KeyReader extends World {

    //reading the keys for AWSD
    public void reader(String[] stringKeys, World worldObj) {

        int i = 0;
        while (i < stringKeys.length) {
            String input = stringKeys[i];
            if (input != null) {
                if (input.equals("W") || input.equals("w")) {
                    worldObj.playerUp();
                }
                if (input.equals("A") || input.equals("a")) {
                    worldObj.playerLeft();
                }
                if (input.equals("S") || input.equals("s")) {
                    worldObj.playerDown();
                }
                if (input.equals("D") || input.equals("d")) {
                    worldObj.playerRight();
                }
                //Deserialize
                if (input.equals("L") || input.equals("l")) {
                    CurrentGame loadGame = new CurrentGame();
                    loadGame.loadingGame();
                }
                //Saving or serializing
                if (input.equals("q") || (input.equals("Q"))) {
                    CurrentGame gameSaved = new CurrentGame(worldObj);
                    gameSaved.savingGame();
//                    StdDraw.clear();
//                    gameSaved();
//                    StdDraw.show();
                    break;
                }
            }
            i++;
        }
    }
}

