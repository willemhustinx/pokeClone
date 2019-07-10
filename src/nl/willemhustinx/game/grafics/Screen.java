package nl.willemhustinx.game.grafics;

import nl.willemhustinx.game.Game;

public class Screen {

    private int width;
    private int height;
    private int[] pixels;

    private int xOffset;
    private int yOffset;

    private SpriteSheet spriteSheetWorld;
    private SpriteSheet spriteSheetEntity;

    public Screen(int width, int height, SpriteSheet spriteSheetWorld, SpriteSheet spriteSheetEntity) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];

        this.spriteSheetWorld = spriteSheetWorld;
        this.spriteSheetEntity = spriteSheetEntity;
    }

    public void renderTile(int xp, int yp, int sprite) {
        xp -= this.xOffset;
        yp -= this.yOffset;

        int xTile = sprite % (spriteSheetWorld.getWidth() / Game.TILESIZE);
        int yTile = sprite / (spriteSheetWorld.getWidth() / Game.TILESIZE);
        int tileOffset = xTile * Game.TILESIZE + (yTile * Game.TILESIZE * spriteSheetWorld.getWidth());

        for (int y = 0; y < Game.TILESIZE; y++) {
            int ys = y;
            if (y + yp < 0 || y + yp >= this.height) continue;
            for (int x = 0; x < Game.TILESIZE; x++) {
                int xs = x;
                if (x + xp < 0 || x + xp >= this.width) continue;
                int color = spriteSheetWorld.getPixels()[xs + ys * spriteSheetWorld.getWidth() + tileOffset];
                pixels[(x + xp) + (y + yp) * this.width] = color;
            }
        }
    }

    public void renderFrame(int x, int y, int w, int h) {
        for (int j = 0; j < h; j++){
            for (int i = 0; i < w; i++) {
                renderTile((x+i) * Game.TILESIZE, (y+j) * Game.TILESIZE, 2);
            }
        }
    }

    public void renderEntity(int xp, int yp, int sprite, boolean flipVertical) {
        int xTile = sprite % (spriteSheetEntity.getWidth() / Game.TILESIZE);
        int yTile = sprite / (spriteSheetEntity.getWidth() / Game.TILESIZE);
        int tileOffset = xTile * Game.TILESIZE + (yTile * Game.TILESIZE * spriteSheetEntity.getWidth());

        xp -= this.xOffset;
        yp -= this.yOffset;

        for (int y = 0; y < Game.TILESIZE; y++) {
            int ys = y;
            for (int x = 0; x < Game.TILESIZE; x++) {
                int xs = x;
                if (flipVertical) {
                    xs = (Game.TILESIZE - 1) - x;
                }
                int color = spriteSheetEntity.getPixels()[xs + ys * spriteSheetEntity.getWidth() + tileOffset];
                if (color != 0) {
                    pixels[(x + xp) + (y + yp - 4) * this.width] = color;
                }
            }
        }
    }





    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int[] getPixels() {
        return pixels;
    }

    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}

