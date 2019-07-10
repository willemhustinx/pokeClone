package nl.willemhustinx.game.enitity;

import nl.willemhustinx.game.Game;
import nl.willemhustinx.game.grafics.Screen;
import nl.willemhustinx.game.helper.Direction;
import nl.willemhustinx.game.input.InputHandler;
import nl.willemhustinx.game.level.Level;

import java.awt.*;

public class Player extends Entity {

    private InputHandler inputHandler;
    private Point destination;
    private Direction lastMove;
    private int velX;
    private int velY;
    private int velocity;
    private int walkDist;

    public Player(int x, int y, Level level, InputHandler inputHandler) {
        super(x, y, level);

        this.velocity = 1;
        this.inputHandler = inputHandler;
    }

    public void tick() {

        Direction moveIntention = null;
        this.velY = 0;
        this.velX = 0;

        if (this.inputHandler.down.down) {
            moveIntention = Direction.DOWN;
        } else if (this.inputHandler.up.down) {
            moveIntention = Direction.UP;
        } else if (this.inputHandler.left.down) {
            moveIntention = Direction.LEFT;
        } else if (this.inputHandler.right.down) {
            moveIntention = Direction.RIGHT;
        }

        //Stop moving if entity is at destination
        if (this.isMoving() && this.justReachedDestination() && moveIntention == null) {
            this.stopMoving();
            //Stop moving when entity hits wall
        } else if (this.isMoving() && this.justReachedDestination() && moveIntention != null && !this.canMoveFromCurrentTile(moveIntention)) {
            this.stopMoving();
            //Destenation reached and keep going
        } else if (this.isMoving() && this.justReachedDestination() && moveIntention != null && moveIntention == this.lastMove && this.canMoveFromCurrentTile(moveIntention)) {
            this.continueMovingFromDestination();
            //Destenation reached and move in other direction
        } else if (this.isMoving() && this.justReachedDestination() && moveIntention != null && moveIntention != this.lastMove && this.canMoveFromCurrentTile(moveIntention)) {
            this.startMoving(moveIntention);
            //Destenation not reached so keep moving
        } else if (this.isMoving() && !this.justReachedDestination()) {
            this.continueMovingToDestination();
            //not moving and wanting to move
        } else if (!this.isMoving() && moveIntention != null && this.canMoveFromCurrentTile(moveIntention)) {
            this.startMoving(moveIntention);
            //Open menu
        }

        this.x += this.velX;
        this.y += this.velY;
    }


    private Point getCurrentTile() {
        int tileSize = Game.TILE_SIZE;
        int tileX = this.x / tileSize;
        int tileY = this.y / tileSize;
        return new Point(tileX, tileY);
    }

    private Point getTileAdjacentToTile(int x, int y, Direction direction) {
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

    private void startMoving(Direction direction) {
        Point currentTile = this.getCurrentTile();
        this.destination = this.getTileAdjacentToTile(currentTile.x, currentTile.y, direction);
        this.setVelocity(direction);
        this.walkDist = 1;
        this.lastMove = direction;
    }

    public void stopMoving() {
        this.destination = null;
        this.velX = 0;
        this.velY = 0;
        this.walkDist = 0;
    }

    private void continueMovingToDestination() {
        this.walkDist++;
        this.setVelocity(this.lastMove);
    }

    private void continueMovingFromDestination() {
        Point currentTile = this.getCurrentTile();
        this.destination = this.getTileAdjacentToTile(currentTile.x, currentTile.y, this.lastMove);
        this.setVelocity(this.lastMove);
        this.walkDist++;
    }

    private boolean isMoving() {
        return this.destination != null;
    }

    private void setVelocity(Direction direction) {
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

    private boolean justReachedDestination() {
        int tileSize = Game.TILE_SIZE;
        int destX = this.destination.x * tileSize;
        int destY = this.destination.y * tileSize;

        return (this.x == destX && this.y == destY);
    }

    private boolean canMoveFromCurrentTile(Direction direction) {
        Point currentTile = this.getCurrentTile();
        Point nextTile = this.getTileAdjacentToTile(currentTile.x, currentTile.y, direction);
        return level.getTile(nextTile.x, nextTile.y).canWalkOn();
    }

    public void render(Screen screen) {
        if (isMoving()) {
            if (this.lastMove == Direction.DOWN) {
                int imageToRender = (this.walkDist / 8) % 4;
                if (imageToRender == 0 || imageToRender == 2) {
                    screen.renderEntity(x, y, 0, false);
                } else if (imageToRender == 1) {
                    screen.renderEntity(x, y, 3, false);
                } else if (imageToRender == 3) {
                    screen.renderEntity(x, y, 3, true);
                }
            } else if (this.lastMove == Direction.UP) {
                int imageToRender = (this.walkDist / 8) % 4;
                if (imageToRender == 0 || imageToRender == 2) {
                    screen.renderEntity(x, y, 1, false);
                } else if (imageToRender == 1) {
                    screen.renderEntity(x, y, 4, false);
                } else if (imageToRender == 3) {
                    screen.renderEntity(x, y, 4, true);
                }
            } else if (this.lastMove == Direction.LEFT) {
                int imageToRender = (this.walkDist / 8) % 2;
                if (imageToRender == 0 || imageToRender == 2) {
                    screen.renderEntity(x, y, 2, false);
                } else if (imageToRender == 1) {
                    screen.renderEntity(x, y, 5, false);
                }
            } else if (this.lastMove == Direction.RIGHT) {
                int imageToRender = (this.walkDist / 8) % 2;
                if (imageToRender == 0 || imageToRender == 2) {
                    screen.renderEntity(x, y, 2, true);
                } else if (imageToRender == 1) {
                    screen.renderEntity(x, y, 5, true);
                }
            }
        } else if (this.lastMove != null) {
            if (this.lastMove == Direction.DOWN) {
                screen.renderEntity(x, y, 0, false);
            } else if (this.lastMove == Direction.UP) {
                screen.renderEntity(x, y, 1, false);
            } else if (this.lastMove == Direction.LEFT) {
                screen.renderEntity(x, y, 2, false);
            } else if (this.lastMove == Direction.RIGHT) {
                screen.renderEntity(x, y, 2, true);
            }
        } else {
            screen.renderEntity(x, y, 0, false);
        }

    }
}
