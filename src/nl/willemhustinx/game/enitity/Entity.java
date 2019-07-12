package nl.willemhustinx.game.enitity;

import nl.willemhustinx.game.grafics.Screen;
import nl.willemhustinx.game.level.Level;

public class Entity {
    protected int x;
    protected int y;
    protected Level level;

    public Entity(int x, int y, Level level) {
        this.x = x;
        this.y = y;
        this.level = level;
    }

    public void tick(){}

    public void render(Screen screen){}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
