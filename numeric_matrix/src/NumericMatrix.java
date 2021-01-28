import java.util.Arrays;
import java.util.Scanner;

public class NumericMatrix {
    static Scanner scanner = new Scanner(System.in);
    static double[][] matrix1;
    static double[][] matrix2;
    static int rowMatrix1;
    static int columnMatrix1;
    static int rowMatrix2;
    static int columnMatrix2;
    static int exit = 0;

    public static void main(String[] args) {

        while (exit == 0) {
            System.out.println("1. Add matrices");
            System.out.println("2. Multiply matrix by a constant");
            System.out.println("3. Multiply matrices");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());
            showMenu(choice);
        }
        System.exit(0);
    }

    static void showMenu(int choice) {
        switch (choice) {
            case 1:
                addMatrices();
                break;
            case 2:
                multiplyMatrixByConst();
                break;
            case 3:multiplyMatrices();
                break;
            case 0:
                exit = 1;
                break;
        }
    }

    static void addMatrices() {

        createTwoMatrix();

        if (rowMatrix1 != rowMatrix2 && columnMatrix1 != columnMatrix2) {
            System.out.println("The operation cannot be performed.");
            System.exit(0);
        }

        double[][] sumMatrix = new double[rowMatrix2][columnMatrix1];
        for (int i = 0; i < rowMatrix1; i++) {
            for (int j = 0; j < columnMatrix1; j++) {
                sumMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        printCalculatedMatrix(sumMatrix);
    }

    static void multiplyMatrices() {

        createTwoMatrix();

        if (columnMatrix1 != rowMatrix2) {
            System.out.println("The operation cannot be performed.");
        }

        double[][] matrix = new double[rowMatrix1][columnMatrix2];
        for (int i = 0; i < rowMatrix1; i++) {
            for (int j = 0; j < columnMatrix2; j++) {
                double num = 0;
                for (int k = 0; k < columnMatrix1; k++) {
                    num += matrix1[i][k] * matrix2[k][j];
                }
                matrix[i][j] = num;
            }
        }

        printCalculatedMatrix(matrix);
    }

    static void multiplyMatrixByConst() {

        System.out.print("Enter size of matrix: ");
        String[] matrixDimension = scanner.nextLine().split(" ");
        rowMatrix1 = Integer.parseInt(matrixDimension[0]);
        columnMatrix1 = Integer.parseInt(matrixDimension[1]);

        System.out.println("Enter matrix: ");
        double[][] matrix = fillUpMatrixFromInput(rowMatrix1, columnMatrix1);

        System.out.print("Enter constant: ");
        double constanta = Double.parseDouble(scanner.nextLine());

        double[][] newMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                newMatrix[i][j] = matrix[i][j] * constanta;
            }
        }
        printCalculatedMatrix(newMatrix);
    }

    static double[][] fillUpMatrixFromInput(int row, int column) {

        double[][] matrix = new double[row][column];

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

    static void printCalculatedMatrix(double[][] matrix) {
        System.out.print("The result is:");
        for (double[] ints : matrix) {
            System.out.println();
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
        }
        System.out.println();
    }

    static void createTwoMatrix() {

        System.out.print("Enter size of first matrix: ");
        String[] sizeMatrix1 = scanner.nextLine().split(" ");
        rowMatrix1 = Integer.parseInt(sizeMatrix1[0]);
        columnMatrix1 = Integer.parseInt(sizeMatrix1[1]);

        System.out.println("Enter first matrix: ");
        matrix1 = fillUpMatrixFromInput(rowMatrix1, columnMatrix1);

        System.out.print("Enter size of second matrix: ");
        String[] sizeMatrix2 = scanner.nextLine().split(" ");
        rowMatrix2 = Integer.parseInt(sizeMatrix2[0]);
        columnMatrix2 = Integer.parseInt(sizeMatrix2[1]);

        System.out.println("Enter second matrix: ");
        matrix2 = fillUpMatrixFromInput(rowMatrix2, columnMatrix2);
    }
}
