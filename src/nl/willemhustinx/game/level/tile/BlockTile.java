package nl.willemhustinx.game.level.tile;

public class BlockTile extends Tile {


    public BlockTile() {
        this.sprite = 0;
    }

    @Override
    public boolean canWalkOn() {
        return false;
    }
}
