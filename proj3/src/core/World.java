package core;

import core.gameLogic.Avatar;
import core.hallwayGenerator.Edge;
import core.roomGenerator.Room;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.List;

import static core.roomGenerator.DynamicRoomGenerator.generateRooms;
import static core.hallwayGenerator.HallwayGenerator.createHallway;

public class World {
    private int width;
    private int height;
    private long seed;
    public TETile[][] world;
    public Avatar avatar;

    public World(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        this.seed = seed;
        world = new TETile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        // 生成Room
        List<Room> rooms = generateRooms(width, height, 10, 5, 4, 7, seed);
        // 绘制Room
        for (Room room : rooms) {
            drawRoom(room);
        }
        // 生成长廊
        List<Edge> hallWays = createHallway(rooms);
        // 绘制Hallway
        for (Edge edge : hallWays) {
            drawHallway(rooms.get(edge.index1), rooms.get(edge.index2));
        }
        initAvatarAndDoor();
    }

    private void initAvatarAndDoor() {
        // 初始化avatar
        avatar = null;
        boolean flag = false;
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                if (world[i][j] == Tileset.FLOOR) {
                    avatar = new Avatar(i, j, world);
                    flag = true;
                }
                if (flag) {
                    break;
                }
            }
            if (flag) {
                break;
            }
        }

        flag = false;
        // 放置通往下一关的入口
        for (int i = world.length - 1; i >= 0; i--) {
            for (int j = world[0].length - 1; j >= 0; j--) {
                if (world[i][j].equals(Tileset.FLOOR)) {
                    world[i][j] = Tileset.FLOWER;
                    flag = true;
                }
                if (flag) {
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
    }

    private void drawRoom(Room room) {
        for (int i = room.x; i < room.x + room.width; i++) {
            world[i][room.y] = Tileset.WALL;
            world[i][room.y + room.height - 1] = Tileset.WALL;
        }
        for (int i = room.y; i < room.y + room.height; i++) {
            world[room.x][i] = Tileset.WALL;
            world[room.x + room.width - 1][i] = Tileset.WALL;
        }
        for (int i = room.x + 1; i < room.x + room.width - 1; i++) {
            for (int j = room.y + 1; j < room.y + room.height - 1; j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }
    private void drawHallway(Room r1, Room r2) {
        Room leftRoom;
        Room rightRoom;
        if (r1.x < r2.x) {
            leftRoom = r1;
            rightRoom = r2;
        } else {
            leftRoom = r2;
            rightRoom = r1;
        }
        int x1 = leftRoom.centerX();
        int x2 = rightRoom.centerX();
        int y1 = leftRoom.centerY();
        int y2 = rightRoom.centerY();

        // 画竖着的hallway
        for (int i = Math.min(y1, y2) - 1; i <= Math.max(y1, y2) + 1; i++) {
            if (!world[x1][i].equals(Tileset.FLOOR)) {
                if (i == Math.max(y1, y2) + 1 || i == Math.min(y1, y2) - 1) {
                    world[x1][i] = Tileset.WALL;
                } else {
                    world[x1][i] = Tileset.FLOOR;
                }
                if (!world[x1 - 1][i].equals(Tileset.FLOOR)) {
                    world[x1 - 1][i] = Tileset.WALL;
                }
                if (!world[x1 + 1][i].equals(Tileset.FLOOR)) {
                    world[x1 + 1][i] = Tileset.WALL;
                }
            }
        }

        // 画横着的hallway
        for (int i = x2; i >= x1; i--) {
            if (!world[i][y2].equals(Tileset.FLOOR)) {
                world[i][y2] = Tileset.FLOOR;
                if (!world[i][y2 + 1].equals(Tileset.FLOOR)) {
                    world[i][y2 + 1] = Tileset.WALL;
                }
                if (!world[i][y2 - 1].equals(Tileset.FLOOR)) {
                    world[i][y2 - 1] = Tileset.WALL;
                }
            }
        }
    }


    public static void main(String[] args) {
        World world1 = new World(60, 30, 123);
        TERenderer ter = new TERenderer();
        ter.initialize(60, 30);
        ter.renderFrame(world1.world);
    }
}
