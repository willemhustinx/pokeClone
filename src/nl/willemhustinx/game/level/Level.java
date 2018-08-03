package nl.willemhustinx.game.level;

import nl.willemhustinx.game.grafics.Screen;
import nl.willemhustinx.game.level.tile.Tile;

public class Level {

    Tile[][] tiles;
    private int width;
    private int height;

    public Level() {
        this.width = 10;
        this.height = 10;
        this.tiles = LevelGenerator.generate(width, height);
    }

    public void render(Screen screen) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.tiles[x][y].render(screen, x, y);
            }
        }

    }

    public void tick() {
        //TODO
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }
}