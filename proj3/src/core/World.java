package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.List;

import static core.DynamicRoomGenerator.generateRooms;

public class World {
    private int width;
    private int height;
    private int seed;
    TETile[][] world;

    public World(int width, int height, int seed) {
        this.width = width;
        this.height = height;
        this.seed = seed;
        world = new TETile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        List<Room> rooms = generateRooms(width, height, 8, 5, 4, 7);

        for (Room room : rooms) {
            for (int i = room.x; i < room.x + room.width; i++) {
                world[i][room.y] = Tileset.WALL;
                world[i][room.y + room.height - 1] = Tileset.WALL;
            }
            for (int i = room.y; i < room.y + room.height; i++) {
                world[room.x][i] = Tileset.WALL;
                world[room.x + room.width - 1][i] = Tileset.WALL;
            }
        }
    }
}
