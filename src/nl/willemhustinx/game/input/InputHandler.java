package nl.willemhustinx.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener {

    public List<Key> keys = new ArrayList<>();
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key menu = new Key();

    public void releaseAll() {
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).down = false;
        }
    }

    public void tick() {
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).tick();
        }
    }

    public void keyTyped(KeyEvent e) {
        //Not Used
    }

    public void keyPressed(KeyEvent e) {
        Toggle(e, true);
    }

    public void keyReleased(KeyEvent e) {
        Toggle(e, false);
    }

    private void Toggle(KeyEvent ke, Boolean pressed) {
        if (ke.getKeyCode() == KeyEvent.VK_UP) {
            up.toggle(pressed);
        }
        if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            down.toggle(pressed);
        }
        if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            left.toggle(pressed);
        }
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            right.toggle(pressed);
        }
        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
            menu.toggle(pressed);
        }
    }

    public class Key {

        public boolean down;
        public boolean clicked;
        public int presses;
        public int absorbs;

        public Key() {
            keys.add(this);
        }

        public void toggle(boolean pressed) {
            if (pressed != down) {
                down = pressed;
            }
            if (pressed) {
                presses++;
            }
        }

        public void tick() {
            if (absorbs < presses) {
                absorbs++;
                clicked = true;
            } else {
                clicked = false;
            }
        }

    }
}