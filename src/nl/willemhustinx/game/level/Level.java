package nl.willemhustinx.game.level;

import nl.willemhustinx.game.enitity.Entity;
import nl.willemhustinx.game.enitity.Player;
import nl.willemhustinx.game.grafics.Screen;
import nl.willemhustinx.game.level.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private Tile[][] tiles;
    private int width;
    private int height;

    private List<Entity> entities = new ArrayList<>();
    private Player player;

    public Level() {
        this.width = 10;
        this.height = 10;
        this.tiles = LevelGenerator.generate(width, height);
    }

    public void renderBackground(Screen screen, int xScroll, int yScroll) {
        int xo = xScroll >> 4;
        int yo = yScroll >> 4;
        int w = (screen.getWidth() + 15) >> 4;
        int h = (screen.getHeight() + 15) >> 4;

        screen.setOffset(xScroll, yScroll);

        for (int y = yo; y <= h + yo; y++) {
            for (int x = xo; x <= w + xo; x++) {
                if(x >= 0 && x < width && y >= 0 && y < height) {
                    this.tiles[x][y].render(screen, x, y);
                }
            }
        }
        screen.setOffset(0, 0);
    }

    public void renderEntities(Screen screen, int xScroll, int yScroll) {

        screen.setOffset(xScroll, yScroll);

        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(screen);
        }

        screen.setOffset(0, 0);
    }

    public void tick() {
        //TODO
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public void add(Entity e){
        if (e instanceof Player) {
            player = (Player) e;
        }
        entities.add(e);
    }
}