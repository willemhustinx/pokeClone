package nl.willemhustinx.game.level;

import nl.willemhustinx.game.level.tile.BlockTile;
import nl.willemhustinx.game.level.tile.GrassTile;
import nl.willemhustinx.game.level.tile.Tile;

public class LevelGenerator {

    private LevelGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static Tile[][] generate(int width, int height) {
        Tile[][] tiles = new Tile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x + 1 == width || y + 1 == height) {
                    tiles[x][y] = new BlockTile();
                } else {
                    tiles[x][y] = new GrassTile();
                }
            }
        }

        return tiles;
    }
}
