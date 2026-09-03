
public class IslandMap {

    private final int WIDTH = 100;
    private final int HEIGHT = 20;
    private final Location[][] GRID_LOCATIONS;

    public IslandMap() {
        GRID_LOCATIONS = new Location[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                GRID_LOCATIONS[x][y] = new Location(x, y);
            }
        }
    }

    public Location getLocations(int x, int y) {
        if (x >= 0&& x < WIDTH && y >= 0 && y < HEIGHT) return GRID_LOCATIONS[x][y];
        return null;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }
}
