import java.util.Arrays;
import java.util.Scanner;

public class NumericMatrix {
    static Scanner scanner = new Scanner(System.in);
    static double[][] matrix;
    static int row;
    static int column;
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
            System.out.println("5. Calculate a determinant");
            System.out.println("6. Inverse matrix");
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
            case 5:
                determinant();
                break;
            case 6:
                inverseMatrix();
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
                transposeByVerticalLine();
                break;
            case 4:
                transposeByHorizontalLine();
                break;
        }
    }

    static void inverseMatrix() {

        matrix = createOneMatrix();
        if (calculateDeterminant(matrix) == 0) {
            System.out.println("This matrix doesn't have an inverse.");
        } else {
            printCalculatedMatrix(calculateInverseMatrix(matrix));
        }
    }

    static double[][] calculateInverseMatrix(double[][] matrix) {

        double[][] temp = new double[matrix.length][matrix.length];
        double[][] invertedMatrix = new double[matrix.length][matrix.length];
        int[] index = new int[matrix.length];

        for (int i = 0; i < matrix.length; ++i) {
            temp[i][i] = 1;
        }

        transformToUpperTriangle (matrix, index);

        for (int i = 0; i < (matrix.length - 1); ++i) {
            for (int j = (i + 1); j < matrix.length; ++j) {
                for (int k = 0; k < matrix.length; ++k) {
                    temp[index[j]][k] -= matrix[index[j]][i] * temp[index[i]][k];
                }
            }
        }

        for (int i = 0; i < matrix.length; ++i) {
            invertedMatrix[matrix.length - 1][i] = (temp[index[matrix.length - 1]][i] / matrix[index[matrix.length - 1]][matrix.length - 1]);

            for (int j = (matrix.length - 2); j >= 0; --j) {
                invertedMatrix[j][i] = temp[index[j]][i];

                for (int k = (j + 1); k < matrix.length; ++k) {
                    invertedMatrix[j][i] -= (matrix[index[j]][k] * invertedMatrix[k][i]);
                }

                invertedMatrix[j][i] /= matrix[index[j]][j];
            }
        }

        return invertedMatrix;
    }

    static void transformToUpperTriangle(double[][] matrix, int[] index) {

        double c0;
        double c1;
        double pi0;
        double pi1;
        double pj;
        int temp;
        int k;

        double[] c = new double[matrix.length];

        for (int i = 0; i < matrix.length; ++i) {
            index[i] = i;
        }

        for (int i = 0; i < matrix.length; ++i) {
            c1 = 0;

            for (int j = 0; j < matrix.length; ++j) {
                c0 = Math.abs (matrix[i][j]);

                if (c0 > c1) {
                    c1 = c0;
                }
            }
            c[i] = c1;
        }

        k = 0;

        for (int j = 0; j < (matrix.length - 1); ++j) {
            pi1 = 0;

            for (int i = j; i < matrix.length; ++i) {
                pi0 = Math.abs (matrix[index[i]][j]);
                pi0 /= c[index[i]];

                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            temp = index[j];
            index[j] = index[k];
            index[k] = temp;

            for (int i = (j + 1); i < matrix.length; ++i) {
                pj = matrix[index[i]][j] / matrix[index[j]][j];
                matrix[index[i]][j] = pj;

                for (int l = (j + 1); l < matrix.length; ++l) {
                    matrix[index[i]][l] -= pj * matrix[index[j]][l];
                }
            }
        }
    }


    static void determinant() {
        matrix = createOneMatrix();
        System.out.println(calculateDeterminant(matrix));
    }

    static double calculateDeterminant(double[][] matrix) {

        double determinant = 0;

        if (matrix.length == 1) {
            determinant = matrix[0][0];
            return determinant;
        }

        if (matrix.length == 2) {
            determinant = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
            return determinant;
        }

        for (int i = 0; i < matrix[0].length; i++) {

            double[][] temp = new double[matrix.length - 1][matrix[0].length - 1];

            for (int j = 1; j < matrix.length; j++) {
                for (int k = 0; k < matrix[0].length; k++) {
                    if (k < i) {
                        temp[j - 1][k] = matrix[j][k];
                    } else if (k > i) {
                        temp[j - 1][k - 1] = matrix[j][k];
                    }
                }
            }

            determinant += matrix[0][i] * Math.pow(-1, i) * calculateDeterminant(temp);
        }
        return determinant;
    }

    static void transposeByHorizontalLine() {
        
        matrix = createOneMatrix();

        for (int i = 0; i < row / 2; i++) {
            for (int j = 0; j < column; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[row - 1 - i][j];
                matrix[row - 1 - i][j] = temp;
            }
        }
        printCalculatedMatrix(matrix);
    }

    static void transposeByVerticalLine() {
        
        matrix = createOneMatrix();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column / 2; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[i][row - 1 - j];
                matrix[i][row - 1 - j] = temp;
            }
        }
        printCalculatedMatrix(matrix);
    }

    static void transposeByMainDiagonal() {

        matrix = createOneMatrix();

        for (int i = 0; i < row; i++) {
            for (int j = i + 1; j < row; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        printCalculatedMatrix(matrix);
    }

    static void transposeBySideDiagonal() {

        matrix = createOneMatrix();
        for (int i = 0; i < row - 1; i++) {
            for (int j = row - 2 - i; j >= 0; j--) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[row - 1 - j][row - 1 - i];
                matrix[row - 1 - j][row - 1 - i] = temp;
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
                for (int k = 0; k < columnMatrix1; k++) {
                    matrix[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        printCalculatedMatrix(matrix);
    }

    static void multiplyMatrixByConst() {

        matrix = createOneMatrix();

        System.out.print("Enter constant: ");
        double constanta = Double.parseDouble(scanner.nextLine());

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = matrix[i][j] * constanta;
            }
        }
        printCalculatedMatrix(matrix);
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
        String matrixDimension = scanner.nextLine().replaceAll("[^0-9]+", "");
        System.out.println("Enter matrix: ");
        row = matrixDimension.charAt(0) - '0';
        column = matrixDimension.charAt(1) - '0';

        return fillUpMatrixFromInput(row, column);
    }

}
