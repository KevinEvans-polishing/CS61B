package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;

import java.awt.*;

import static edu.princeton.cs.algs4.StdDraw.*;

public class Main {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;
    public static long SEED;

    public static void main(String[] args) {
        renderMenu();
    }

    public static void renderMenu() {
        // 设置窗口大小
        enableDoubleBuffering();
        StdDraw.setCanvasSize(40 * 16, 40 * 16);
        setXscale(-25, 25);
        setYscale(-25, 25);
        renderMenuPage();
        show();

        while (true) {
            // 检测键盘输入
            if (hasNextKeyTyped()) {
                char key = nextKeyTyped();
                if (key == 'n' || key == 'N') {
                    newGame();
                    break;
                } else if (key == 'l' || key == 'L') {
                    loadGame();
                    break;
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
        // 为了保持窗口显示，防止程序立即退出
        while (true) {
            StdDraw.pause(100);
        }
    }

    private static void renderMenuPage() {
        clear(new Color(0, 0, 0));
        setPenColor(WHITE);
        setFont(new Font("Monaco", Font.BOLD, 40));
        text(0, 15, "CS61B: BYOW");
        setFont(new Font("Monaco", Font.BOLD, 25));
        text(0, 5,  "(N) New Game");
        text(0, -2, "(L) Load Game");
        text(0, -9, "(Q) Quit Game");
    }

    private static void newGame() {
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
                filledRectangle(0, 0 ,10, 2);
                setPenColor(Color.YELLOW);
                text(0, 0, String.valueOf(seed));
                show();
            }
        }

        seed.deleteCharAt(seed.length() - 1);
        SEED = Long.parseLong(String.valueOf(seed));

        // 由种子初始化世界
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        World world = new World(WIDTH, HEIGHT, SEED);
        ter.renderFrame(world.world);
    }

    private static void loadGame() {

    }
}
