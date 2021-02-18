import java.io.IOException;
import java.util.Random;

public class GameOfLife {

    public static void main(String[] args) throws InterruptedException {

//        Scanner scanner = new Scanner(System.in);

//        int size = scanner.nextInt();
//        long seed = scanner.nextLong();
//        int generation = scanner.nextInt();

        int size = Integer.parseInt(args[0]);

        CurrentGeneration cg = new CurrentGeneration(size);
        NextGeneration ng = new NextGeneration();
        ng.copyGen(cg.getCurGen());

        int generation = cg.getRandom();
        int countGen = 1;
        int alive = countAlive(size, cg.getCurGen());
        System.out.println("Generation #" + countGen);
        System.out.println("Alive: " + alive);
        cg.printCurGen();

        while (generation != 0) {
            Thread.sleep(200);
            try {
                if (System.getProperty("os.name").contains("Windows"))
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                else
                    Runtime.getRuntime().exec("clear");
            } catch (IOException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
            countGen++;
            System.out.println("Generation #" + countGen);
            ng.countNeighbours();
            alive = countAlive(size, ng.getNextGen());
            System.out.println("Alive: " + alive);
            ng.printNextGeneration();
            generation--;
        }
    }

    private static int countAlive(int size, Cell[][] generation) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (generation[i][j].alive) {
                    count++;
                }
            }
        }
        return count;
    }
}

class CurrentGeneration {

    int size;
    Cell[][] curGen;
    Random random;

    CurrentGeneration(int size) {
        this.size = size;
        this.random = new Random();
        this.curGen = createCurrentGeneration(size);
    }

    public int getRandom() {
        return random.nextInt(15) + 10;
    }

    public Cell[][] createCurrentGeneration(int size) {

        curGen = new Cell[size][size];

        for (int i = 0; i < curGen.length; i++) {
            for (int j = 0; j < curGen.length; j++) {
                if (random.nextBoolean()) {
                    curGen[i][j] = new Cell(i, j, true);
                } else {
                    curGen[i][j] = new Cell(i, j, false);
                }
            }
        }
        return curGen;
    }

    public Cell[][] getCurGen() {
        return curGen;
    }

    public void printCurGen() {
        for (Cell[] row : curGen) {
            for (Cell cell : row) {
                if (cell.alive) {
                    System.out.print("O");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}

class NextGeneration {

    Cell[][] nextGen;
    int[][] direction = {{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};
    int size;

    public Cell[][] getNextGen() {
        return nextGen;
    }

    public void checkNeighbours(Cell curCell) {

        int neighbours = 0;

        for (int[] dir : direction) {
            if (curCell.getX() + dir[0] < 0) {
                if (curCell.getY() + dir[1] < curCell.getY()) {
                    if (curCell.getY() == 0) {
                        if (nextGen[size - 1][size - 1].alive) {
                            neighbours++;
                        }
                    } else if (nextGen[size - 1][curCell.getY() + dir[1]].alive) {
                        neighbours++;
                    }
                } else if (curCell.getY() + dir[1] == curCell.getY()) {
                    if (nextGen[size - 1][curCell.getY() + dir[1]].alive) {
                        neighbours++;
                    }
                } else if (curCell.getY() + dir[1] > curCell.getY()) {
                    if (curCell.getY() == size - 1) {
                        if (nextGen[size - 1][0].alive) {
                            neighbours++;
                        }
                    } else if (nextGen[size - 1][curCell.getY() + dir[1]].alive) {
                        neighbours++;
                    }
                }
            } else if (curCell.getX() + dir[0] > curCell.getX() && curCell.getY() + dir[1] < 0) {
                if (curCell.getX() == size - 1 && curCell.getY() == 0) {
                    if (nextGen[0][size - 1].alive) {
                        neighbours++;
                    }
                } else if (nextGen[curCell.getX() + dir[0]][size - 1].alive) {
                    neighbours++;
                }
            } else if (curCell.getX() + dir[0] > curCell.getX() && curCell.getY() + dir[1] > size - 1) {
                if (curCell.getX() == size - 1 && curCell.getY() == size - 1) {
                    if (nextGen[0][0].alive) {
                        neighbours++;
                    }
                } else if (nextGen[curCell.getX() + dir[0]][0].alive) {
                    neighbours++;
                }
            } else if (curCell.getX() + dir[0] == curCell.getX() && curCell.getY() + dir[1] < 0) {
                if (nextGen[curCell.getX()][size - 1].alive) {
                    neighbours++;
                }
            } else if (curCell.getX() + dir[0] == curCell.getX() && curCell.getY() + dir[1] > size - 1) {
                if (nextGen[curCell.getX()][0].alive) {
                    neighbours++;
                }
            } else if (curCell.getX() + dir[0] < curCell.getX() && curCell.getY() + dir[1] < 0) {
                if (nextGen[curCell.getX() + dir[0]][size - 1].alive) {
                    neighbours++;
                }
            } else if (curCell.getX() + dir[0] < curCell.getX() && curCell.getY() + dir[1] > size - 1) {
                if (nextGen[curCell.getX() + dir[0]][0].alive) {
                    neighbours++;
                }
            } else if (curCell.getX() + dir[0] > size - 1 && curCell.getY() + dir[1] > size - 1) {
                if (curCell.getY() == size - 1) {
                    if (nextGen[0][0].alive) {
                        neighbours++;
                    } else if (nextGen[0][curCell.getY() + dir[1]].alive) {
                        neighbours++;
                    }
                }
            } else if (curCell.getX() + dir[0] > size - 1 && curCell.getY() + dir[1] > curCell.getY()) {
                if (nextGen[0][curCell.getY() + dir[1]].alive) {
                    neighbours++;
                }
            } else if (curCell.getX() + dir[0] > size - 1 && curCell.getY() + dir[1] == curCell.getY()) {
                if (nextGen[0][curCell.getY()].alive) {
                    neighbours++;
                }
            } else if (curCell.getX() + dir[0] > size - 1 && curCell.getY() + dir[1] < curCell.getY()) {
                if (nextGen[0][curCell.getY() + dir[1]].alive) {
                    neighbours++;
                }
            } else if (nextGen[curCell.getX() + dir[0]][curCell.getY() + dir[1]].alive) {
                neighbours++;
            }
        }
        curCell.setNeighbour(neighbours);
    }

    public void countNeighbours() {
        size = nextGen.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                checkNeighbours(nextGen[i][j]);
            }
        }
        for (Cell[] cells : nextGen) {
            for (int j = 0; j < nextGen.length; j++) {
                if (cells[j].alive) {
                    if (cells[j].getNeighbour() != 2 && cells[j].getNeighbour() != 3) {
                        cells[j].isDead();
                    }
                } else {
                    if (cells[j].getNeighbour() == 3) {
                        cells[j].isAlive();
                    }
                }
            }
        }
    }

    public void copyGen(Cell[][] curGen) {

        nextGen = new Cell[curGen.length][curGen.length];

        for (int i = 0; i < nextGen.length; i++) {
            System.arraycopy(curGen[i], 0, nextGen[i], 0, nextGen.length);
        }
    }

    public void printNextGeneration() {

        for (Cell[] row : nextGen) {
            for (Cell cell : row) {
                if (cell.alive) {
                    System.out.print("O");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}

class Cell {

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
