public class Main {

    public static void main(String[] args) throws InterruptedException {

        int size = 20;

        GameOfLife gol = new GameOfLife();

        while (!gol.startGeneration()) {
            System.out.println("RI");
        }

        CurrentGeneration curGen = new CurrentGeneration(size);
        NextGeneration nextGen = new NextGeneration(curGen.getCurGen());

        int generation = curGen.getRandom();
        int countGen = 0;
        int alive = countAlive(size, curGen.getCurGen());

        while (generation != 0) {

            countGen++;

            Thread.sleep(100);
            gol.updateGridPanel(nextGen.getNextGen(), countGen, alive);

            nextGen.countNeighbours();
            alive = countAlive(size, nextGen.getNextGen());
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









