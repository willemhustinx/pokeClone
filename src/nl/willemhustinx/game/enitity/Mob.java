package nl.willemhustinx.game.enitity;


import nl.willemhustinx.game.Game;
import nl.willemhustinx.game.helper.Direction;
import nl.willemhustinx.game.level.Level;

import java.awt.*;

public class Mob extends Entity {

    Point destination;
    Direction lastMove;
    int velX;
    int velY;
    int velocity;
    int walkDist;

    public Mob(int x, int y, Level level) {
        super(x, y, level);

        this.velocity = 1;
    }

    public void tick(){

    }


    Point getCurrentTile() {
        int tileSize = Game.TILE_SIZE;
        int tileX = this.x / tileSize;
        int tileY = this.y / tileSize;
        return new Point(tileX, tileY);
    }

    Point getTileAdjacentToTile(int x, int y, Direction direction) {
        if (direction == Direction.UP) {
            y--;
        } else if (direction == Direction.DOWN) {
            y++;
        } else if (direction == Direction.LEFT) {
            x--;
        } else if (direction == Direction.RIGHT) {
            x++;
        }
        return new Point(x, y);
    }

    void startMoving(Direction direction) {
        Point currentTile = this.getCurrentTile();
        this.destination = this.getTileAdjacentToTile(currentTile.x, currentTile.y, direction);
        this.setVelocity(direction);
        this.walkDist = 1;
        this.lastMove = direction;
    }

    void stopMoving() {
        this.destination = null;
        this.velX = 0;
        this.velY = 0;
        this.walkDist = 0;
    }

    void continueMovingToDestination() {
        this.walkDist++;
        this.setVelocity(this.lastMove);
    }

    void continueMovingFromDestination() {
        Point currentTile = this.getCurrentTile();
        this.destination = this.getTileAdjacentToTile(currentTile.x, currentTile.y, this.lastMove);
        this.setVelocity(this.lastMove);
        this.walkDist++;
    }

    boolean isMoving() {
        return this.destination != null;
    }

    void setVelocity(Direction direction) {
        if (direction == Direction.DOWN) {
            velY += this.velocity;
        } else if (direction == Direction.UP) {
            velY -= this.velocity;
        } else if (direction == Direction.LEFT) {
            velX -= this.velocity;
        } else if (direction == Direction.RIGHT) {
            velX += this.velocity;
        }

    }

    boolean justReachedDestination() {
        int tileSize = Game.TILE_SIZE;
        int destX = this.destination.x * tileSize;
        int destY = this.destination.y * tileSize;

        return (this.x == destX && this.y == destY);
    }

    boolean canMoveFromCurrentTile(Direction direction) {
        Point currentTile = this.getCurrentTile();
        Point nextTile = this.getTileAdjacentToTile(currentTile.x, currentTile.y, direction);
        return level.getTile(nextTile.x, nextTile.y).canWalkOn();
    }
}
