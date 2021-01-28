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
            System.out.println("4. Transpose matrix");
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
            case 3:
                multiplyMatrices();
                break;
            case 4:
                showMenuNumberFour();
                break;
            case 0:
                exit = 1;
                break;
        }
    }

    static void showMenuNumberFour() {

        System.out.println("1. Main diagonal");
        System.out.println("2. Side diagonal");
        System.out.println("3. Vertical line");
        System.out.println("4. Horizontal line");
        System.out.print("Your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                transposeByMainDiagonal();
                break;
            case 2:
                transposeBySideDiagonal();
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    static void transposeByMainDiagonal() {

        double[][] matrix = createOneMatrix();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix.length; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        printCalculatedMatrix(matrix);
    }

    static void transposeBySideDiagonal() {

        double[][] matrix = createOneMatrix();
        for (int i = 0; i < matrix.length - 1; i++) { //2
            for (int j = matrix.length - 1 - i; j >= 0; j--) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[matrix.length - 1 - j][matrix.length - 1 - i];
                matrix[matrix.length - 1 - j][matrix.length - 1 - i] = temp;
            }
        }
        printCalculatedMatrix(matrix);
    }

    static void addMatrices() {

        createTwoMatrices();

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

        createTwoMatrices();

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

        double[][] matrix = createOneMatrix();

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

    static void createTwoMatrices() {

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

    static double[][] createOneMatrix() {

        System.out.print("Enter size of matrix: ");
        String[] matrixDimension = scanner.nextLine().split(" ");
        System.out.println("Enter matrix: ");

        return fillUpMatrixFromInput(Integer.parseInt(matrixDimension[0]), Integer.parseInt(matrixDimension[1]));
    }
}
