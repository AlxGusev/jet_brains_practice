public class NextGeneration {

    Cell[][] nextGen;
    int[][] direction = {{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};
    int size;

    public NextGeneration(Cell[][] curGen) {
        nextGen = copyGen(curGen);
    }

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

    public Cell[][] copyGen(Cell[][] curGen) {

        Cell[][] gen = new Cell[curGen.length][curGen.length];

        for (int i = 0; i < gen.length; i++) {
            System.arraycopy(curGen[i], 0, gen[i], 0, gen.length);
        }
        return gen;
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

