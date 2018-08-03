package nl.willemhustinx.game.level.tile;

import nl.willemhustinx.game.Game;
import nl.willemhustinx.game.grafics.Screen;

public abstract class Tile {

    protected int sprite;


    public Tile() {

    }

    public void render(Screen screen, int x, int y) {
        screen.renderTile(x * Game.TILESIZE, y * Game.TILESIZE, this.sprite);
    }

    public void tick() {

    }

    public boolean canWalkOn() {
        return true;
    }

    public void interact() {

    }

    public void steppedOn() {

    }
}
