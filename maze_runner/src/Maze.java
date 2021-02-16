import java.io.*;
import java.util.*;

public class Maze {

    public static void main(String[] args) {

        menu();

    }

    public static void menu() {

        Scanner scanner = new Scanner(System.in);

        Board board = null;

        boolean exit = false;

        while (!exit) {

            System.out.println("=== Menu ===");
            System.out.println("1. Generate a new maze");
            System.out.println("2. Load a maze");

            String fileName;

            if (board == null) {

                System.out.println("0. Exit");

                int pick = Integer.parseInt(scanner.nextLine());

                switch (pick) {
                    case 1:
                        System.out.println("Enter the size of a new maze");
                        int size = Integer.parseInt(scanner.nextLine());
                        board = new Board(size, size);
                        board.printMazeWithOutPath();
                        break;
                    case 2:
                        fileName = scanner.nextLine();
                        board = loadMaze(fileName);
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println("Incorrect option. Please try again");
                        break;
                }
            } else {

                System.out.println("3. Save the maze");
                System.out.println("4. Display the maze");
                System.out.println("5. Find the escape");
                System.out.println("0. Exit");

                int pick = Integer.parseInt(scanner.nextLine());

                switch (pick) {
                    case 1:
                        System.out.println("Enter the size of a new maze");
                        int size = Integer.parseInt(scanner.nextLine());
                        board = new Board(size, size);
                        break;
                    case 2:
                        fileName = scanner.nextLine();
                        Board newBoard = loadMaze(fileName);
                        if (newBoard != null) {
                            board = newBoard;
                        }
                        break;
                    case 3:
                        fileName = scanner.nextLine();
                        saveMaze(board, fileName);
                        break;
                    case 4:
                        board.printMazeWithOutPath();
                        break;
                    case 5:
                        if (board.mazeWithPath == null) {
                            board.findExit();
                        }
                        board.printMazeWithExit();
                        break;
                    case 0:
                        exit = true;
                        System.out.println("Bye!");
                        break;
                    default:
                        System.out.println("Incorrect option. Please try again");
                        break;
                }
            }
        }
    }

    public static void saveMaze(Board board, String fileName) {

        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream ooj = new ObjectOutputStream(fos)) {

            ooj.writeObject(board);

        } catch (IOException e) {
            System.out.println("Cannot load the maze. It has an invalid format");
        }
    }

    public static Board loadMaze(String fileName) {

        File file = new File(fileName);

        Board newBoard = null;

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            newBoard = (Board) ois.readObject();

            if (checkBoardFormat(newBoard)) {
                return newBoard;
            }
            newBoard = null;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The file " + fileName + " does not exist");
        }

        return newBoard;
    }

    public static boolean checkBoardFormat(Board board) {

        int count = 0;

        for (int i = 0; i < board.maze.length; i++) {
            if (board.maze[i][0].equals("  ")) {
                count++;
            }
            if (board.maze[i][board.maze[0].length - 1].equals("  ")) {
                count++;
            }
            if (board.maze[0][i].equals("  ")) {
                count++;
            }
            if (board.maze[board.maze.length - 1][i].equals("  ")) {
                count++;
            }
        }
        return count == 2;
    }
}




class Board implements Serializable {

    int height;
    int width;
    Cell[][] vertices;
    List<Edge> edges;
    List<Edge> notVisited;
    Random random = new Random();
    String[][] maze;
    int[][] direction  = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    String[][] mazeWithPath;


    Board (int height, int width) {
        this.height = height;
        this.width = width;
        notVisited = new ArrayList<>();
        maze = new String[height][width];
        vertices = new Cell[height][width];
        edges = new ArrayList<>();
        createBoard(height, width);
        createMaze();
        primAlgorithm();
    }

    public void createBoard(int height, int width) {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = new Cell(i, j);
                vertices[i][j] = cell;

                if ((i - 1) >= 0) {
                    Edge edge = new Edge(i - 1, j, i, j, random.nextInt(height + width));
                    edges.add(edge);
                }
                if ((j - 1) >= 0) {
                    Edge edge2 = new Edge(i, j - 1, i, j, random.nextInt(height + width));
                    edges.add(edge2);
                }
            }
        }
    }

    public void findExit() {

        LinkedList<Cell> nextToVisit = new LinkedList<>();

        Cell start = findEntryCell();

        nextToVisit.add(start);

        while (!nextToVisit.isEmpty()) {

            Cell current = nextToVisit.remove();

            if (!vertices[current.getX()][current.getY()].visited || vertices[current.getX()][current.getY()].isExplore) {
                continue;
            }

            if (findExitCell(current)) {
                backTrack(current);
                break;
            }

            current.SetIsExplore();
            for (int[] direction: direction) {
                if (current.getX() + direction[0] == 0 ||
                        current.getY() + direction[1] == 0 ||
                        current.getX() + direction[0] > vertices.length - 1 ||
                        current.getY() + direction[1] > vertices.length - 1) {
                    continue;
                }

                Cell nextCell = vertices[current.getX() + direction[0]][current.getY() + direction[1]];

                if (nextCell.isExplore || !nextCell.visited) {
                    continue;
                }

                nextCell.setParent(current);
                nextToVisit.add(nextCell);
            }
        }
    }

    public void backTrack(Cell exit) {

        List<Cell> path = new ArrayList<>();
        Cell iter = exit;

        while (iter != null) {
            path.add(iter);
            iter = iter.getParent();
        }

        for (Cell cell: path) {
            mazeWithPath[cell.getX()][cell.getY()] = "//";
        }

    }

    public Cell findEntryCell() {
        mazeWithPath = Arrays.stream(maze).map(String[]::clone).toArray(String[][]::new);
        Cell start = new Cell(0,0);
        for (Cell[] vertex : vertices) {
            if (vertex[0].visited) {
                start = vertex[1];
                mazeWithPath[start.getX()][0] = "//";
                break;
            }
        }
        return start;
    }

    public boolean findExitCell(Cell cell) {
        Cell exit = new Cell(0,0);
        for (Cell[] vertex: vertices) {
            if (vertex[vertex.length - 1].visited) {
                exit = vertex[vertex.length - 2];
                mazeWithPath[exit.getX()][exit.getY() + 1] = "//";
                break;
            }
        }
        return exit.getX() == cell.getX() && exit.getY() == cell.getY();
    }

    public void primAlgorithm() {

        Cell cellEnter = new Cell(1, 1);

        findAllIncidentsOfVertex(cellEnter);

        while (!notVisited.isEmpty()) {

            Edge edgeWithMinValue = findEdgeWithMinimumValue(notVisited);

            if (!vertices[edgeWithMinValue.getX1()][edgeWithMinValue.getY1()].visited) {
                if (checkNeighbours(edgeWithMinValue.getX1(), edgeWithMinValue.getY1())) {
                    findAllIncidentsOfVertex(new Cell(edgeWithMinValue.getX1(), edgeWithMinValue.getY1()));
                }
            } else if (!vertices[edgeWithMinValue.getX2()][edgeWithMinValue.getY2()].visited) {
                if (checkNeighbours(edgeWithMinValue.getX2(), edgeWithMinValue.getY2())) {
                    findAllIncidentsOfVertex(new Cell(edgeWithMinValue.getX2(), edgeWithMinValue.getY2()));

                }
            } else {
                break;
            }
        }

        enterAndExit();

    }

    public boolean checkNeighbours(int x, int y) {

        int count = 0;

        if (x - 1 < 0 || y - 1 < 0 || x + 1 > vertices.length - 1 || y + 1 > vertices[0].length - 1) {
            return false;
        }

        if (!vertices[x - 1][y].visited) {
            count++;
        }
        if (!vertices[x + 1][y].visited) {
            count++;
        }
        if (!vertices[x][y - 1].visited) {
            count++;
        }
        if (!vertices[x][y + 1].visited) {
            count++;
        }

        return count == 3;
    }

    public Edge findEdgeWithMinimumValue(List<Edge> incidents) {

        Edge edgeWithMinValue = incidents.get(random.nextInt(notVisited.size()));

        for (int i = 1; i < incidents.size(); i++) {
            if (incidents.get(i).getValue() < edgeWithMinValue.getValue()) {
                edgeWithMinValue = incidents.get(i);
            }
        }

        edges.remove(edgeWithMinValue);
        notVisited.remove(edgeWithMinValue);

        return edgeWithMinValue;

    }

    public void findAllIncidentsOfVertex(Cell cell) {

        for (Edge edge : edges) {
            if ((edge.getX1() == cell.getX() && edge.getY1() == cell.getY() ||
                    edge.getX2() == cell.getX() && edge.getY2() == cell.getY()) && !cell.visited) {
                notVisited.add(edge);
            }
        }
        vertices[cell.getX()][cell.getY()].isVisited();
        maze[cell.getX()][cell.getY()] = "  ";
    }

    public void createMaze() {

        String wall = "\u2588\u2588";

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j] = wall;
            }
        }
    }

    public void printMazeWithOutPath() {
        printMaze(maze);

    }
    public void printMazeWithExit() {
        printMaze(mazeWithPath);
    }

    private void printMaze(String[][] maze) {
        System.out.println();
        for (String[] str : maze) {
            System.out.println();
            for (String s : str) {
                System.out.print(s);
            }
        }
        System.out.println();
        System.out.println();
    }



    public void enterAndExit() {

        boolean flag = true;

        while (flag) {

            int num = random.nextInt(height - 1);
            if (num == 0) {
                num += 1;
            }

            if (vertices[num][1].visited) {
                maze[num][0] = "  ";
                vertices[num][0].isVisited();
                flag = false;
            }
        }

        flag = true;

        while (flag) {

            int num = random.nextInt(height - 1);
            if (num == 0) {
                num += 1;
            }

            if (vertices[num][vertices[0].length - 2].visited) {
                maze[num][vertices[0].length - 1] = "  ";
                vertices[num][vertices[0].length - 1].isVisited();
                flag = false;
            }
        }
    }
}

class Cell implements Serializable {

    private final int x;
    private final int y;
    boolean visited;
    boolean isExplore;
    Cell parent = null;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.visited = false;
        this.isExplore = false;
    }

    public void isVisited() {
        this.visited = true;
    }

    public void SetIsExplore() {
        this.isExplore = true;
    }

    public void setParent(Cell cell) {
        this.parent = cell;
    }

    public Cell getParent() {
        return parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return x + "," + y + "," + visited;
    }
}

class Edge implements Serializable {

    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;
    private final int value;

    Edge(int x1, int y1, int x2, int y2, int value) {

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.value = value;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "{" + x1 +
                "," + y1 +
                "," + x2 +
                "," + y2 +
                ",v=" + value +
                '}';
    }
}
