package nl.willemhustinx.game.enitity;

import nl.willemhustinx.game.grafics.Screen;
import nl.willemhustinx.game.helper.Direction;
import nl.willemhustinx.game.input.InputHandler;
import nl.willemhustinx.game.level.Level;

public class Player extends Mob {

    private InputHandler inputHandler;


    public Player(int x, int y, Level level, InputHandler inputHandler) {
        super(x, y, level);

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
            //Destination reached and keep going
        } else if (this.isMoving() && this.justReachedDestination() && moveIntention != null && moveIntention == this.lastMove && this.canMoveFromCurrentTile(moveIntention)) {
            this.continueMovingFromDestination();
            //Destination reached and move in other direction
        } else if (this.isMoving() && this.justReachedDestination() && moveIntention != null && moveIntention != this.lastMove && this.canMoveFromCurrentTile(moveIntention)) {
            this.startMoving(moveIntention);
            //Destination not reached so keep moving
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
