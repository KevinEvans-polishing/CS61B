package core.gameLogic;

import core.World;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

public class Avatar {
    int x;
    int y;
    TETile[][] world;
    static final TETile appearance = Tileset.AVATAR;

    public Avatar(int x, int y, TETile[][] world) {
        this.x = x;
        this.y = y;
        this.world = world;
        world[x][y] = appearance;
    }

    public void moveUp() {
        world[x][y] = Tileset.FLOOR;
        y++;
        world[x][y] = appearance;
    }

    public void moveRight() {
        world[x][y] = Tileset.FLOOR;
        x++;
        world[x][y] = appearance;
    }

    public void moveDown() {
        world[x][y] = Tileset.FLOOR;
        y--;
        world[x][y] = appearance;
    }

    public void moveLeft() {
        world[x][y] = Tileset.FLOOR;
        x--;
        world[x][y] = appearance;
    }
}
