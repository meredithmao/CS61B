import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
        // YOUR CODE HERE

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "rGrid"   : String[][], the files to display. <br>
     * "rul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "rul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "rlr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "rlr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();
        double qUllon = params.get("ullon");
        double qUllat = params.get("ullat");
        double qLrLon = params.get("lrlon");
        double qLrlat = params.get("lrlat");
        double qWidth = params.get("w");
        double rullon = MapServer.ROOT_ULLON;
        double rullat = MapServer.ROOT_ULLAT;
        double rlrlon = MapServer.ROOT_LRLON;
        double rlrlat = MapServer.ROOT_LRLAT;
        double rdepth = 0; //initialized with random number
        int rdepthNumber = 0;
        if (qUllon < rullon || qUllat > rullat) {
            results.put("query_success", false);
        } else if (qLrLon > rlrlon || qLrlat < rlrlat) {
            results.put("query_success", false);
        } else if (qLrLon < qUllon || qLrlat > qUllat) {
            results.put("query_success", false);
        } else {
            results.put("query_success", true);
        }

        double qLonDPP = (qLrLon - qUllon) / qWidth;
        double rLonDPP = (rlrlon - rullon) / MapServer.TILE_SIZE;

        for (double i = 0; i < 8; i++) {
            if (rLonDPP <= qLonDPP || i == 7) {
                rdepth = (Math.sqrt(Math.pow(4, i))); //128
                break;
            }
            rLonDPP = rLonDPP / 2;
        }
        //#1-8 for depth number
        rdepthNumber = (int) ((Math.log(rdepth)) / Math.log(2)); //7
        results.put("depth", rdepthNumber);
        double tileWidth = (rlrlon - rullon) / rdepth;
        double tileHeight = (rullat - rlrlat) / rdepth;
        //upper longitude starting distance
        int startingX = (int) ((qUllon - rullon) / tileWidth);
        double imageullon = rullon + (startingX * tileWidth);
        results.put("raster_ul_lon", imageullon);
        //upper latitude starting distance
        int startingY = (int) ((rullat - qUllat) / tileHeight);
        double imageullat = rullat - (startingY * tileHeight);
        results.put("raster_ul_lat", imageullat);
        //lower longitude ending distance
        //int endingX = (int) ((rlrlon - qLrLon) / tileWidth);
        int endingX = (int) ((qLrLon - imageullon) / tileWidth) + startingX + 1;
        double imagelrlon = rullon + (endingX * tileWidth);
        results.put("raster_lr_lon", imagelrlon);
        //lower latitude ending distance
        int endingY = (int) ((imageullat - qLrlat) / tileHeight) + startingY + 1;
        double imagelrrat = rullat - (endingY * tileHeight);
        results.put("raster_lr_lat", imagelrrat);
        //determines total number of x values
        int xValues = endingX - startingX;
        //determines total number of y values
        int yValues = endingY - startingY;
        int saveX = startingX;
        String[][] rGrid = new String[yValues][xValues];
        for (int y = 0; y < yValues; y++) {
            startingX = saveX;
            for (int x = 0; x < xValues; x++) {
                String file = "d" + rdepthNumber + "_x" + (startingX) + "_y" + (startingY) + ".png";
                rGrid[y][x] = file;
                startingX += 1;
            }
            startingY += 1;
        }
        results.put("render_grid", rGrid);

        return results;
    }

}
