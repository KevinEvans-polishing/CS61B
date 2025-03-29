package core;

import core.gameLogic.Game;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;

import java.awt.*;
import java.io.IOException;


import static core.gameLogic.Game.generateNewGameSeed;

import static core.menuRender.menuRender.renderMenuPage;
import static edu.princeton.cs.algs4.StdDraw.*;

public class Main {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;

    public static void main(String[] args) throws IOException {

        long seed = 0;
        enableDoubleBuffering();
        StdDraw.setCanvasSize(40 * 16, 40 * 16);
        setXscale(-25, 25);
        setYscale(-25, 25);
        renderMenuPage();
        show();


        Game game = new Game();
        boolean loadMode = false;

        while (true) {
            // 检测键盘输入
            if (hasNextKeyTyped()) {
                char key = nextKeyTyped();
                if (key == 'n' || key == 'N') {
                    seed = generateNewGameSeed();
                    break;
                } else if (key == 'l' || key == 'L') {
                    In in = new In("./save.txt");
                    if (in.hasNextLine()) {
                        loadMode = true;
                        break;
                    } else {
                        setPenColor(Color.YELLOW);
                        setFont(new Font("Monaco", Font.BOLD, 30));
                        text(0, 10, "You haven't saved yet!");
                        show();
                    }
                } else if (key == 'q' || key == 'Q') {
                    System.exit(0);
                } else {
                    setPenColor(Color.YELLOW);
                    setFont(new Font("Monaco", Font.BOLD, 30));
                    text(0, 10, "Please enter N, L or Q!");
                    show();
                }
            }
        }

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        game.runGame(seed, ter, loadMode);
    }
}
