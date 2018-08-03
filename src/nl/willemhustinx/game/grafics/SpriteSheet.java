package nl.willemhustinx.game.grafics;

import nl.willemhustinx.game.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    private BufferedImage image;
    private int[] pixels;
    private int width;
    private int height;

    public SpriteSheet(String path) {

        try {
            this.image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.width = image.getWidth();
        this.height = image.getHeight();
        this.pixels = image.getRGB(0, 0, this.width, this.height, null, 0, this.width);
    }

    public BufferedImage getSprite(int x, int y) {
        return this.image.getSubimage(x * Game.TILESIZE, y * Game.TILESIZE, Game.TILESIZE, Game.TILESIZE);
    }

    public BufferedImage getFlippedSprite(int x, int y) {
        return this.image.getSubimage(x * Game.TILESIZE, y * Game.TILESIZE, Game.TILESIZE, Game.TILESIZE);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int[] getPixels() {
        return pixels;
    }

}
