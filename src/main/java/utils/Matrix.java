package utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor
@Data
public class Matrix {

    private int[][] matrixElements;
    private int size;


    public Matrix(int size) {
        matrixElements = new int[size][size];
        this.size = size;
    }

    public void subtractMinFromRow() {

        for (int column = 0; column < getSize(); column++) {
            int min = Arrays.stream(getMatrixElements()[column]).min().getAsInt();

            for (int row = 0; row < getSize(); row++) {
                getMatrixElements()[column][row] = getMatrixElements()[column][row] - min;
            }
        }
    }

    public void rotate90right(Matrix matrix) {
        int[][] rotate90right = new int[matrix.getSize()][matrix.getSize()];
        for (int i = 0; i < matrix.getSize(); i++) {
            for (int j = 0; j < matrix.getSize(); j++) {
                rotate90right[i][j] = matrix.getMatrixElements()[j][i];
            }
        }
        matrix.setMatrixElements(rotate90right);
    }

    public void subtractMinFromColumn() {
        rotate90right(this);
        subtractMinFromRow();
        for (int i = 0; i < 3; i++) {
            rotate90right(this);
        }
    }

    public int[][] copy() {
        int[][] newData = new int[getSize()][getSize()];
        for (int i = 0; i < newData.length; i++) {
            for (int j = 0; j < newData.length; j++) {
                newData[i][j] = getMatrixElements()[i][j];
            }
        }
        return newData;
    }

    public boolean isContainZero() {

        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (getMatrixElements()[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}