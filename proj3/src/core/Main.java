package core;

import tileengine.TERenderer;

public class Main {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;
    public static final int SEED = 72613;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        World world = new World(WIDTH, HEIGHT, SEED);
        ter.renderFrame(world.world);
    }
}
