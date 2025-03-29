package core.menuRender;

import java.awt.*;

import static edu.princeton.cs.algs4.StdDraw.*;
import static edu.princeton.cs.algs4.StdDraw.text;

public class menuRender {
    public static void renderMenuPage() {
        clear(new Color(0, 0, 0));
        setPenColor(WHITE);
        setFont(new Font("Monaco", Font.BOLD, 40));
        text(0, 15, "CS61B: BYOW");
        setFont(new Font("Monaco", Font.BOLD, 25));
        text(0, 5,  "(N) New Game");
        text(0, -2, "(L) Load Game");
        text(0, -9, "(Q) Quit Game");
    }
}
