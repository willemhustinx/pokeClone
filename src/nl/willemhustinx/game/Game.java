package nl.willemhustinx.game;

import nl.willemhustinx.game.enitity.Player;
import nl.willemhustinx.game.grafics.Screen;
import nl.willemhustinx.game.grafics.SpriteSheet;
import nl.willemhustinx.game.input.InputHandler;
import nl.willemhustinx.game.level.Level;
import nl.willemhustinx.game.menu.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
    public static final String NAME = "Pokémon";
    public static final int SCREEN_HEIGHT = 144;
    public static final int SCREEN_WIDTH = 160;
    public static final int SCALE = 4;
    public static final int TILE_SIZE = 16;
    private boolean running = false;

    private Level level;
    private Player player;
    private Screen screen;
    private Menu menu;

    private InputHandler inputHandler;

    private BufferedImage image = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData(); // the array of pixels that will be displayed on the screen.


    public static void main(String[] args) {
        Game game = new Game();
        game.setMinimumSize(new Dimension(SCREEN_WIDTH * SCALE, SCREEN_HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(SCREEN_WIDTH * SCALE, SCREEN_HEIGHT * SCALE));
        game.setPreferredSize(new Dimension(SCREEN_WIDTH * SCALE, SCREEN_HEIGHT * SCALE));

        JFrame frame = new JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }

    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void stop() {
        running = false;
    }

    public void run() {
        long lastTime = System.nanoTime();
        double unprocessed = 0;
        double nsPerTick = 1000000000.0 / 60;
        int frames = 0;
        int ticks = 0;
        long lastTimer1 = System.currentTimeMillis();

        init();

        while (running) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;
            while (unprocessed >= 1) {
                ticks++;
                tick();
                unprocessed -= 1;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            frames++;
            render();

            if (System.currentTimeMillis() - lastTimer1 > 1000) {
                lastTimer1 += 1000;
                System.out.println(ticks + " ticks, " + frames + " fps");

                frames = 0;
                ticks = 0;
            }
        }
    }

    private void tick() {
        inputHandler.tick();
        player.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        int xScroll = this.player.getX() - (SCREEN_WIDTH /2) + TILE_SIZE;
        int yScroll = this.player.getY() - (SCREEN_HEIGHT /2) + (TILE_SIZE / 2);

        this.level.renderBackground(this.screen, xScroll, yScroll);
        this.level.renderEntities(this.screen, xScroll, yScroll);

        for (int y = 0; y < this.screen.getHeight(); y++) {
            for (int x = 0; x < this.screen.getWidth(); x++) {
                int cc = this.screen.getPixels()[x + y * this.screen.getWidth()];
                pixels[x + y * SCREEN_WIDTH] = cc;
            }
        }

        Graphics g = bs.getDrawGraphics();
        g.fillRect(0, 0, getWidth(), getHeight());

        int ww = Game.SCREEN_WIDTH * Game.SCALE;
        int hh = Game.SCREEN_HEIGHT * Game.SCALE;
        int xo = (this.getWidth() - ww) / 2;
        int yo = (this.getHeight() - hh) / 2;
        g.drawImage(image, xo, yo, ww, hh, null);

        g.dispose();
        bs.show();
    }

    private void init() {
        this.screen = new Screen(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, new SpriteSheet("/sprites_world.png"), new SpriteSheet("/sprites_entity.png"));

        this.inputHandler = new InputHandler();
        this.addKeyListener(inputHandler);
        this.requestFocus();

        this.level = new Level();
        this.player = new Player(3 * Game.TILE_SIZE, 3 * Game.TILE_SIZE, level, inputHandler);
        this.level.add(player);

        setMenu(new Menu());
    }

    private void setMenu(Menu menu){
        this.menu = menu;
        if (menu != null) menu.init(this, inputHandler);
    }
}