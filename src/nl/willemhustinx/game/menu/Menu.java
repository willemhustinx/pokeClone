package nl.willemhustinx.game.menu;

import nl.willemhustinx.game.Game;
import nl.willemhustinx.game.grafics.Screen;
import nl.willemhustinx.game.input.InputHandler;

public class Menu {
    protected Game game; // game object used in the menu classes.
    protected InputHandler input; // input object used in the menu classes.

    public void init(Game game, InputHandler input) {
        this.input = input;
        this.game = game;
    }

    public void tick() {
    }

    public void render(Screen screen) {
        screen.renderFrame(0, 7, 10, 3);
    }
}