public class Cell {

    private final int x;
    private final int y;
    boolean alive;
    private int neighbour;

    public Cell(int x, int y, boolean alive) {
        this.x = x;
        this.y = y;
        this.alive = alive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void isDead() {
        this.alive = false;
    }

    public void isAlive() {
        this.alive = true;
    }

    public void setNeighbour(int num) {
        this.neighbour = num;
    }

    public int getNeighbour() {
        return neighbour;
    }

    @Override
    public String toString() {
        return x + "," + y + "," + alive + "," + neighbour;
    }
}

