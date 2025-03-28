package demo;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

import static edu.princeton.cs.algs4.StdDraw.*;
import static edu.princeton.cs.algs4.StdDraw.text;

public class DrawTextDemo {
    public static void main(String[] args) {
        // 设置窗口大小
        StdDraw.setCanvasSize(40 * 16, 40 * 16);

        setXscale(-25, 25);
        setYscale(-25, 25);

        //clear(new Color(0, 0, 0));
//        setPenColor(WHITE);
//        setFont(new Font("Monaco", Font.BOLD, 40));
//        text(0, 15, "CS61B: BYOW");
//        setFont(new Font("Monaco", Font.BOLD, 25));
//        text(0, 5,  "(N) New Game");
//        text(0, -2, "(L) Load Game");
//        text(0, -9, "(Q) Quit Game");
        // 为了保持窗口显示，防止程序立即退出
        while (true) {
            StdDraw.pause(100);
        }
    }
}
