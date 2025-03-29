package core.gameLogic;

import core.World;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import static core.Main.*;
import static edu.princeton.cs.algs4.StdDraw.*;
import static edu.princeton.cs.algs4.StdDraw.show;

public class Game {
    public void runGame(long seed, TERenderer ter, boolean loadMode) throws IOException {
        boolean loadBefore = loadMode;
        StringBuilder str = new StringBuilder();
        boolean firstSeed = true;

        while (true) {
            Avatar avatar;
            World world1;
            TETile[][] world;
            if (loadMode) {
                world1 = loadGame();
                world = world1.world;
                avatar = world1.avatar;
                loadMode = false;
            } else {
                world1 = new World(WIDTH, HEIGHT, seed);
                world = world1.world;
                avatar = world1.avatar;

                if (!loadBefore && firstSeed) {
                    str.append(seed).append("\n");
                    firstSeed = false;
                }
            }
            boolean gameOver = false;
            while (!gameOver) {
                if (hasNextKeyTyped()) {
                    char key = nextKeyTyped();
                    str.append(key);
                    gameOver = moveAvatar(key, world, avatar, gameOver);
                }
                ter.renderFrame(world);
                int length = str.length();
                if (length >= 2) {
                    char secLast = str.charAt(length - 2);
                    if (secLast == ':') {
                        char last = str.charAt(length - 1);
                        if (last == 'q' || last == 'Q') {
                            str.deleteCharAt(length - 1);
                            length--;
                            str.deleteCharAt(length - 1);

                            if (loadBefore) {
                                PrintWriter out = new PrintWriter(new FileWriter("./save.txt", true));
                                out.print(str);
                                out.close();
                            } else {
                                Out out = new Out("./save.txt");
                                out.print(str);
                                out.close();
                            }
                            System.exit(0);
                        }
                    }
                }
            }

            Random random = new Random(seed);
            seed = random.nextLong();
        }
    }

    public static long generateNewGameSeed() {
        clear(new Color(0, 0, 0));
        setPenColor(WHITE);
        setFont(new Font("Monaco", Font.BOLD, 40));
        text(0, 15, "CS61B: BYOW");
        setFont(new Font("Monaco", Font.BOLD, 25));
        text(0, 10, "Enter seed followed by S");
        show();

        // 获取输入的种子
        StringBuilder seed = new StringBuilder();
        boolean running = true;
        while (running) {
            if (hasNextKeyTyped()) {
                char key = nextKeyTyped();
                if (key == 'S' || key == 's') {
                    running = false;
                }
                if (Character.isDigit(key)) {
                    seed.append(key);
                }
                setFont(new Font("Monaco", Font.BOLD, 25));
                setPenColor(Color.BLACK);
                filledRectangle(0, 0, 10, 2);
                setPenColor(Color.YELLOW);
                text(0, 0, String.valueOf(seed));
                show();
            }
        }

        return Long.parseLong(String.valueOf(seed));
    }

    public World loadGame() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        In in = new In("./save.txt");
        String originalSeedString = "";
        if (in.hasNextLine()) {
            originalSeedString = in.readLine();
        }
        long seed = Long.parseLong(originalSeedString);

        World world1;
        while (true) {
            world1 = new World(WIDTH, HEIGHT, seed);
            TETile[][] world = world1.world;
            // 初始化avatar
            Avatar avatar = world1.avatar;
            boolean loadOver = false;
            boolean gameOver = false;
            while (!gameOver) {
                if (in.hasNextChar()) {
                    char key = in.readChar();
                    gameOver = moveAvatar(key, world, avatar, gameOver);
                    // for debugging
//                    ter.renderFrame(world);
                } else {
                    gameOver = true;
                    loadOver = true;
                }
            }

            if (loadOver) {
                break;
            }
            Random random = new Random(seed);
            seed = random.nextLong();
        }
        ter.renderFrame(world1.world);
        return world1;
    }



    private boolean moveAvatar(char key, TETile[][] world, Avatar avatar, boolean gameOver) {
        switch (key) {
            case 'w':
                if (world[avatar.x][avatar.y + 1].equals(Tileset.FLOOR)) {
                    avatar.moveUp();
                } else if (world[avatar.x][avatar.y + 1].equals(Tileset.FLOWER)) {
                    avatar.moveUp();
                    gameOver = true;
                }
                break;
            case 'a':
                if (world[avatar.x - 1][avatar.y].equals(Tileset.FLOOR)) {
                    avatar.moveLeft();
                } else if (world[avatar.x - 1][avatar.y].equals(Tileset.FLOWER)) {
                    avatar.moveLeft();
                    gameOver = true;
                }
                break;
            case 's':
                if (world[avatar.x][avatar.y - 1].equals(Tileset.FLOOR)) {
                    avatar.moveDown();
                } else if (world[avatar.x][avatar.y - 1].equals(Tileset.FLOWER)) {
                    avatar.moveDown();
                    gameOver = true;
                }
                break;
            case 'd':
                if (world[avatar.x + 1][avatar.y].equals(Tileset.FLOOR)) {
                    avatar.moveRight();
                } else if (world[avatar.x + 1][avatar.y].equals(Tileset.FLOWER)) {
                    avatar.moveLeft();
                    gameOver = true;
                }
                break;
        }
        return gameOver;
    }
}

