package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void addHexagon(TETile[][] tiles, int s, int startPosX, int startPosY) {
        TETile tile = randomTile();
        int lineStartPos = 0;
        int lineLength = 0;
        for (int y = 0; y < s * 2; y += 1) {
            lineStartPos = lineStartPosFinder(startPosX, s, y);
            lineLength = lineLengthFinder(s, y);
            drawLine(lineLength, lineStartPos, startPosY + y, tiles, tile);
        }
    }

    private static int lineLengthFinder(int s, int level) {
        if (level < s) {
            return s + 2 * level;
        } else {
            return s + (s - 1) * 2 - (level - s) * 2;
        }
    }

    private static int lineStartPosFinder(int startPosX, int s, int level) {
        if (level < s) {
            return startPosX - level;
        } else {
            return startPosX - 2 * s + (level + 1);
        }
    }

    private static void drawLine(int length, int startX, int y, TETile[][] tiles, TETile tile) {
        for (int i = 0; i < length; i += 1, startX += 1) {
            tiles[startX][y] = tile;
        }
    }

    public static void drawOneSThreeHexColumn(TETile[][] tiles, int numHex, int startX) {
        int startY = (5 - numHex) * 3;
        for (int i = numHex; i > 0; i -= 1, startY += 6) {
            addHexagon(tiles, 3, startX, startY);
        }
    }

    public static void drawSThreeHexs(TETile[][] tiles) {
        int startX =  3;
        for (int numHex = 3; numHex < 6; numHex += 1, startX += 5) {
            drawOneSThreeHexColumn(tiles, numHex, startX);
        }
        for (int numHex = 4; numHex > 2; numHex -= 1, startX += 5) {
            drawOneSThreeHexColumn(tiles, numHex, startX);
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(2);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            default: return Tileset.WALL;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] tiles = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }

        //addHexagon(tiles, 5, 20, 20);

        drawSThreeHexs(tiles);

        ter.renderFrame(tiles);
    }


}
