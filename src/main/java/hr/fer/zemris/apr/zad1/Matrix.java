package hr.fer.zemris.apr.zad1;

import java.util.Arrays;

public class Matrix extends AbstractMatrix {

	/**
	 * Elements of matrix that are defined for two dimensional matrix.
	 */
	private double[][] elements;
	/**
	 * Number of rows.
	 */
	private int rows;
	/**
	 * Number of cols.
	 */
	private int cols;

	public Matrix(double[][] elements, int rows, int cols,
			boolean canTakeElements) {
		this.rows = rows;
		this.cols = cols;
		if (canTakeElements) {
			this.elements = elements;
		} else {
			double[][] newElements = new double[elements.length][];
			for (int i = 0; i < elements.length; i++) {
				newElements[i] = Arrays.copyOf(elements[i], elements[i].length);
			}
			this.elements = newElements;
		}

	}

	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		double[][] elements = new double[rows][];
		for (int i = 0; i < rows; i++) {
			elements[i] = new double[cols];
		}
		this.elements = elements;
	}

	@Override
	public int getRowsCount() {
		return rows;
	}

	@Override
	public int getColsCount() {
		return cols;
	}

	@Override
	public double get(int row, int col) {
		return elements[row][col];
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		elements[row][col] = value;
		return this;
	}

	@Override
	public IMatrix copy() {
		return new Matrix(elements, rows, cols, false);
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		double[][] elements = new double[rows][];
		for (int i = 0; i < rows; i++) {
			elements[i] = new double[cols];
		}
		return new Matrix(elements, rows, cols, true);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cols;
		result = prime * result + Arrays.hashCode(elements);
		result = prime * result + rows;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matrix other = (Matrix) obj;
		if (cols != other.cols)
			return false;
		if (rows != other.rows)
			return false;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if( Math.abs(elements[i][j] - other.elements[i][j]) > IMatrix.EPSILON){
					return false;
				}
			}
			
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//sb.append("[ ");
		for (int i = 0; i < elements.length; i++) {
			for (double el : elements[i]) {
				sb.append(el).append(" ");
			}
			if ((i + 1) == elements.length){
				break;
			}
			sb.append("\n");
		}
		//sb.append(" ]");
		return sb.toString();
	}
	
	@Override
	public void swapRows(int firstRow, int secondRow) {
		double[] temp = elements[firstRow];
		elements[firstRow] = elements[secondRow];
		elements[secondRow] = temp;
	}

}
