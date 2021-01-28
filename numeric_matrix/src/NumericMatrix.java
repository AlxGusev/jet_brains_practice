import java.util.Scanner;

public class NumericMatrix {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("1. Add matrices");
        System.out.println("2. Multiply matrix by a constant");
        System.out.println("3. Multiply matrices");
        System.out.println("0. Exit");
        System.out.print("Your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                addMatrices();
                break;
            case 2:
                multiplyMatrixByConst();
                break;
            case 3:
                break;
            case 0:
                System.exit(0);
                break;
        }
        System.exit(0);
    }

    static void addMatrices() {

        System.out.print("Enter size of first matrix: ");
        String[] sizeMatrix1 = scanner.nextLine().split(" ");
        System.out.println("Enter first matrix: ");
        double[][] matrix1 = createMatrix(sizeMatrix1);

        System.out.print("Enter size of second matrix: ");
        String[] sizeMatrix2 = scanner.nextLine().split(" ");
        System.out.println("Enter second matrix: ");
        double[][] matrix2 = createMatrix(sizeMatrix2);

        if (!sizeMatrix1[0].equals(sizeMatrix2[0]) || !sizeMatrix1[1].equals(sizeMatrix2[1])) {
            System.out.println("The operation cannot be performed.");
            System.exit(0);
        }

        double[][] sumMatrix = new double[matrix1.length][matrix1[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                sumMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        printMatrix(sumMatrix);
    }

    static void

    static void multiplyMatrixByConst() {

        System.out.print("Enter size of matrix: ");
        String[] matrixDimension = scanner.nextLine().split(" ");
        System.out.println("Enter matrix: ");
        double[][] matrix = createMatrix(matrixDimension);

        System.out.println("Enter constant: ");
        double constanta = Double.parseDouble(scanner.nextLine());

        double[][] newMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                newMatrix[i][j] = matrix[i][j] * constanta;
            }
        }
        printMatrix(newMatrix);
    }
    static double[][] createMatrix(String[] matrixDimension) {

        double[][] matrix = new double[Integer.parseInt(matrixDimension[0])][Integer.parseInt(matrixDimension[1])];

        int row = Integer.parseInt(matrixDimension[0]);
        while (row != 0) {
            for (int i = 0; i < matrix.length; i++) {
                String[] nextRow = scanner.nextLine().split(" ");
                for (int j = 0; j < matrix[0].length; j++) {
                    matrix[i][j] = Double.parseDouble(nextRow[j]);
                }
                row--;
            }
        }
        return matrix;
    }
    static void printMatrix(double[][] matrix) {
        System.out.print("The result is:");
        for (double[] ints : matrix) {
            System.out.println();
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
        }
    }
}
