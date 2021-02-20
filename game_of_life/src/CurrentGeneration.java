import java.util.Random;

public class CurrentGeneration {

    int size;
    Cell[][] curGen;
    Random random;

    CurrentGeneration(int size) {
        this.size = size;
        this.random = new Random();
        this.curGen = createCurrentGeneration(size);
    }

    public int getRandom() {
        return random.nextInt(1000000000) + 10;
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
