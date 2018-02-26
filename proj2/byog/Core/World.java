package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class World {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    public static TERenderer ter = new TERenderer();
    public static TETile[][] world = new TETile[WIDTH][HEIGHT];

    public static void base() {
        ter.initialize(WIDTH, HEIGHT);
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private static void room(int n, long s) {
        Random r = new Random(s);
        int roomw = r.nextInt(7) + 1;
        int roomh = r.nextInt(7) + 1;

//        // wall params
//        int wallx = roomx + 2;
//        int wally = roomy + 2;
//        for (int x = 0; x < wallx; x += 1) {
//            for (int y = 0; y < wally; y += 1) {
//                world[x][y] = Tileset.WALL;
//            }
//        }

        while (n > 0) {
            // roomw is width , roomh is height
            // roomx is x coord roomy is y coord
            int roomx = r.nextInt(WIDTH - 1 - roomw);
            int roomy = r.nextInt(HEIGHT - 1 - roomh);
            for (int x1 = roomx; x1 < roomw + roomx - 1; x1 += 1) {
                for (int y1 = roomy; y1 < roomh + roomy; y1 += 1) {
                    world[x1][y1] = Tileset.ROOMFLOOR;
                }
            }
            n--;
        }
    }

    public static void roomgen (long s) {
        Random r = new Random(s);
        int n = r.nextInt(10) + 20;
//        for (int i = 0; i < x; i++) {
        room(n, s);
    }

    public static void halls() {
        int range = 15;
        for (int x = 0; x < WIDTH - 1; x++) {
            for (int y = 0; y < HEIGHT - 1; y++) {
                //looking to see if its on the edge of a room
                if (world[x][y] == Tileset.ROOMFLOOR) {
                    if ((world[x+1][y] == Tileset.NOTHING && world[x][y+1] == Tileset.NOTHING)) {
                        //upper right corner tile coords
                        int room1x = x;
                        int room1y = y;
                        //checking if another room is within a range of tiles UPWARDS right
                        //increment x1 until it hits a new room
                        for (int x1 = x; x1 < range + x && x1 < WIDTH && x1 > 0; x1++) {
                            //increments y for each x
                            for (int y1 = y; y1 < range + y && y1 < HEIGHT && y1 > 0; y1++) {
                                if (world[x1][y1] == Tileset.ROOMFLOOR) {
                                    int room2x = x1;
                                    int room2y = y1;
                                    while (room1y + 1 < room2y) {
                                        world[room1x][room1y + 1] = Tileset.HALLFLOOR;
                                        room1y++;
                                    }
                                    while (room1x + 1 < room2x) {
                                        world[room1x + 1][room2y] = Tileset.HALLFLOOR;
                                        room1x++;
                                    }
                                }
                            }
                        }
                        //checking if another room is within a range of tiles DOWNWARDS right
                        for (int x1 = x; x1 < range + x && x1 < WIDTH && x1 > 0; x1++) {
                            for (int y1 = y; y1 > y - range && y1 > 0 && y1 > 0; y1--) {
                                if (world[x1][y1] == Tileset.ROOMFLOOR) {
                                    int room2x = x1;
                                    int room2y = y1;
                                    while (room1x + 1 < room2x) {
                                        world[room1x + 1][room1y] = Tileset.HALLFLOOR;
                                        room1x++;
                                    }
                                    while (room1y - 1 > room2y) {
                                        world[room2x][room1y - 1] = Tileset.HALLFLOOR;
                                        room1y--;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        base();
        roomgen(6436);
        halls();
        ter.renderFrame(world);
    }
}
