package algorithms;

import utils.Matrix;

import java.util.Arrays;

public class HungarianMethod implements Algorithm {

    private final int ONCE_CROSS_OUT = 150;
    private final int TWICE_CROSS_OUT = 180;
    private final int ZERO = 999;
    private final int NOT_ZERO = 500;
    private Matrix matrix;
    private int cross = 0;
    private int noSolve = 0;

    public HungarianMethod(Matrix matrix) {
        this.matrix = new Matrix(matrix.getSize());
        this.matrix.setMatrixElements(matrix.copy());
    }

    private static int findIndex(int[] arr, int value) {

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public static int sum(Matrix inputMatrix, Matrix solvedMatrix) {
        int sum = 0;
        for (int i = 0; i < inputMatrix.getSize(); i++) {
            for (int j = 0; j < inputMatrix.getSize(); j++) {
                if (solvedMatrix.getMatrixElements()[i][j] == 1) {
                    sum = sum + inputMatrix.getMatrixElements()[i][j];
                }
            }
        }
        return sum;
    }

    @Override
    public Matrix solve() {
        Matrix auxiliaryMatrix = new Matrix(matrix.getSize());
        matrix.subtractMinFromRow();
        matrix.subtractMinFromColumn();
        auxiliaryMatrix.setMatrixElements(matrix.copy());

        crossOutZeros(auxiliaryMatrix);
        Matrix solve;
        Matrix afterReduce;
        if (cross != matrix.getSize()) {
            afterReduce = reduceMatrix(matrix, auxiliaryMatrix);
            solve = getSolvedMatrix(afterReduce);
        } else {
            solve = getSolvedMatrix(matrix);
        }


        changeToZeroOneArray(solve);
        return solve;
    }

    private void markAllRow(int rowIndex, Matrix matrix) {
        for (int i = 0; i < matrix.getSize(); i++) {
            if (matrix.getMatrixElements()[rowIndex][i] != ZERO) {
                matrix.getMatrixElements()[rowIndex][i] = NOT_ZERO;
            }
        }
    }

    private void markAllColumn(int columnIndex, Matrix matrix) {
        for (int i = 0; i < matrix.getSize(); i++) {
            if (matrix.getMatrixElements()[i][columnIndex] != ZERO) {
                matrix.getMatrixElements()[i][columnIndex] = NOT_ZERO;
            }
        }
    }

    private int[][] crossOutZeros(Matrix matrix) {


        while (matrix.isContainZero()) {
            int[] zerosInRow = countRowNumbers(matrix, 0);
            int[] zerosInColumn = countColumnNumbers(matrix, 0);


            int maxInRow = Arrays.stream(countRowNumbers(matrix, 0)).max().getAsInt();
            int maxInColumn = Arrays.stream(countColumnNumbers(matrix, 0)).max().getAsInt();
            if (maxInRow >= maxInColumn) {
                for (int i = 0; i < matrix.getSize(); i++) {
                    int rowToMark = findIndex(zerosInRow, maxInRow);
                    if (matrix.getMatrixElements()[rowToMark][i] == ONCE_CROSS_OUT) {
                        matrix.getMatrixElements()[rowToMark][i] = TWICE_CROSS_OUT;
                    } else {
                        matrix.getMatrixElements()[rowToMark][i] = ONCE_CROSS_OUT;
                    }
                }
            } else {
                for (int i = 0; i < matrix.getSize(); i++) {
                    int columnToMark = findIndex(zerosInColumn, maxInColumn);
                    if (matrix.getMatrixElements()[i][columnToMark] == ONCE_CROSS_OUT) {
                        matrix.getMatrixElements()[i][columnToMark] = TWICE_CROSS_OUT;
                    } else {
                        matrix.getMatrixElements()[i][columnToMark] = ONCE_CROSS_OUT;
                    }
                }
            }
            cross++;
        }
        return matrix.getMatrixElements();
    }

    private int[] countRowNumbers(Matrix matrix, int numberToCount) {
        int[] rowZeroCounter = new int[matrix.getSize()];
        for (int i = 0; i < matrix.getSize(); i++) {
            for (int j = 0; j < matrix.getSize(); j++) {
                if (matrix.getMatrixElements()[i][j] == numberToCount) {
                    rowZeroCounter[i]++;
                }
            }
        }
        return rowZeroCounter;
    }

    private int[] countColumnNumbers(Matrix matrix, int numberToCount) {
        int[] columnZeroCounter;
        matrix.rotate90right(matrix);
        columnZeroCounter = countRowNumbers(matrix, numberToCount);
        matrix.rotate90right(matrix);
        matrix.rotate90right(matrix);
        matrix.rotate90right(matrix);
        return columnZeroCounter;

    }

    private Matrix reduceMatrix(Matrix toReduce, Matrix crossOut) {
        int min = crossOut.getMatrixElements()[1][1];
        for (int i = 0; i < crossOut.getSize(); i++) {
            for (int j = 0; j < crossOut.getSize(); j++) {
                if (crossOut.getMatrixElements()[i][j] < min) {
                    min = crossOut.getMatrixElements()[i][j];
                }
            }
        }
        for (int i = 0; i < toReduce.getSize(); i++) {
            for (int j = 0; j < toReduce.getSize(); j++) {
                if (crossOut.getMatrixElements()[i][j] == TWICE_CROSS_OUT) {
                    toReduce.getMatrixElements()[i][j] = toReduce.getMatrixElements()[i][j] + min;
                }
                if ((crossOut.getMatrixElements()[i][j] != TWICE_CROSS_OUT) && (crossOut.getMatrixElements()[i][j] != ONCE_CROSS_OUT)) {
                    toReduce.getMatrixElements()[i][j] = toReduce.getMatrixElements()[i][j] - min;
                }
            }
        }
        return toReduce;
    }

    private void changeToZeroOneArray(Matrix matrix) {
        for (int i = 0; i < matrix.getSize(); i++) {
            for (int j = 0; j < matrix.getSize(); j++) {
                if (matrix.getMatrixElements()[i][j] == ZERO) {
                    matrix.getMatrixElements()[i][j] = 1;
                }
                if (matrix.getMatrixElements()[i][j] == NOT_ZERO) {
                    matrix.getMatrixElements()[i][j] = 0;
                }
            }
        }

    }

    private Matrix getLocalSolve(Matrix matrix, int row, int col) {

        Matrix matr = new Matrix(matrix.getSize());
        matr.setMatrixElements(matrix.copy());

        matr.getMatrixElements()[row][col] = ZERO;
        markAllRow(row, matr);
        markAllColumn(col, matr);

        for (int i = 0; i < matr.getSize(); i++) {
            for (int j = 0; j < matr.getSize(); j++) {
                if (matr.getMatrixElements()[i][j] == 0) {
                    matr.getMatrixElements()[i][j] = ZERO;
                    markAllRow(i, matr);
                    markAllColumn(j, matr);
                }
            }
        }

        int[] tab = new int[matr.getSize()];
        Arrays.fill(tab, 1);

        noSolve++;

        if (Arrays.equals(countRowNumbers(matr, ZERO), tab) && (Arrays.equals(countColumnNumbers(matr, ZERO), tab))) {

            return matr;
        }
        if (noSolve == matrix.getSize()) {

            for (int i = 0; i < matr.getSize(); i++) {
                int[] rw = countRowNumbers(matr, ZERO);
                if (rw[i] != 0) continue;
                for (int j = 0; j < matrix.getSize(); j++) {
                    int[] cl = countColumnNumbers(matr, ZERO);
                    if (cl[j] == 0) {
                        matr.getMatrixElements()[i][j] = ZERO;
                        break;
                    }
                }
            }
            noSolve = 0;
            return matr;
        }
        return null;
    }

    private Matrix getSolvedMatrix(Matrix matrix) {

        for (int i = 0; i < matrix.getSize(); i++) {
            for (int j = 0; j < matrix.getSize(); j++) {
                if (matrix.getMatrixElements()[i][j] == 0) {
                    Matrix matrixTest = getLocalSolve(matrix, i, j);

                    if (matrixTest != null) {
                        return matrixTest;
                    }
                }
            }
        }
        return null;
    }


}