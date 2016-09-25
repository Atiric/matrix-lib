package hr.fer.zemris.apr.zad1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * 
 * Interface used as a contract between the methods described and client classes
 * of this interface. This interface is a model for matrix operations and it
 * provides methods that are commonly used when doing calculations. Please note
 * that this interface is derivative of interface that is defined by college
 * Marko Čupić but is heavily modified and updated.
 * 
 * @author Armin Tirić
 *
 */
public interface IMatrix {
	
	final static double EPSILON = 1E-9; 

	/**
	 * Getter for number of rows.
	 * 
	 * @return number of rows.
	 */
	int getRowsCount();

	/**
	 * Getter for number of columns.
	 * 
	 * @return number of columns.
	 */
	int getColsCount();

	/**
	 * Getter for element in given row and column.
	 * 
	 * @param row
	 *            row, starts with 0.
	 * @param col
	 *            column, starts with 0.
	 * @return Wanted element.
	 */
	double get(int row, int col);

	/**
	 * Setter for element in given row and column.
	 * 
	 * @param row
	 *            row, starts with 0.
	 * @param col
	 *            column, starts with 0.
	 * @return reference of this matrix.
	 */
	IMatrix set(int row, int col, double value);

	/**
	 *
	 * Method that returns a copy of matrix.
	 * 
	 * @return Reference of new matrix that has the same elements as the latter
	 *         but the reference is not connected with latter.
	 */
	IMatrix copy();

	/**
	 * Method that creates the new matrix of wanted dimensions. Implementation
	 * of wanted matrix is given by the implementation of the class implementing
	 * this interface. The all elements in created instance are 0d.
	 * 
	 * @param rows
	 *            wanted number of rows.
	 * @param cols
	 *            wanted number of columns.
	 * @return Reference of new matrix.
	 */
	IMatrix newInstance(int rows, int cols);

	/**
	 * Metoda omogu�ava stvaranje transponirane matrice u odnosu na originalnu
	 * matricu. Ako je parametar {@code liveView} postavljen na {@code true},
	 * objekt koji se vrati mora biti "�ivi" pogled na originalnu matricu (za
	 * rje�avanje ovoga prikladan je oblikovni obrazac Proxy). U suprotnom
	 * stvara se nova matrica koja �uva vlastitu kopiju podataka.
	 * 
	 * @param liveView
	 *            Flag, if set the resulting reference is a live view on current
	 *            matrix, otherwise it is a reference that can't be connected to
	 *            this reference.
	 * @return Reference on transposed matrix with properties defined with flag
	 *         liveView.
	 */
	IMatrix nTranspose(boolean liveView);
	/**
	 * Method that creates the view or another matrix as lower triangular matrix that has
	 * all elements on main diagonal equal to 1d.
	 * @param liveView Flag for live view on matrix.
	 * @return Lower triangular matrix.
	 */
	IMatrix nLowerTriangular(boolean liveView);
	/**
	 * Method that creates the view or another matrix as upper triangular matrix.
	 * @param liveView Flag for live view on matrix.
	 * @return Upper triangular matrix.
	 */
	IMatrix nUpperTriangular(boolean liveView);
	/**
	 * Method that adds other matrix to this matrix changing it elements.
	 * 
	 * @param other
	 *            matrix that will be added.
	 * @return Reference on this matrix.
	 */
	IMatrix add(IMatrix other);

	/**
	 * Method that adds other matrix to this matrix without changing it
	 * elements.
	 * 
	 * @param other
	 *            matrix that will be added.
	 * @return Reference on new matrix that is result if addition.
	 */
	IMatrix nAdd(IMatrix other);

	/**
	 * Method that subtracts other matrix to this matrix changing it elements.
	 * 
	 * @param other
	 *            matrix that will be subtracted.
	 * @return Reference on this matrix.
	 */
	IMatrix sub(IMatrix other);

	/**
	 * Method that subtracts other matrix to this matrix without changing it
	 * elements.
	 * 
	 * @param other
	 *            matrix that will be subtracted.
	 * @return Reference on new that is result of subtraction.
	 */
	IMatrix nSub(IMatrix other);

	/**
	 * Method that calculates the left matrix multiplication, original matrixes
	 * are unaffected with this calculation.
	 * 
	 * @param other
	 *            Matrix that will multiply this matrix.
	 * @return Reference on new matrix that is result of left matrix
	 *         multiplication.
	 */
	IMatrix nMultiply(IMatrix other);

	/**
	 * Method that calculates determinant of matrix.
	 * 
	 * @return Determinant of this matrix.
	 * @throws RuntimeException
	 *             if matrix is not square.
	 */
	double determinant();

	/**
	 * Method that returns matrix that corresponds the current matrix after
	 * removing the given row and column. New matrix has one row and one column
	 * less then this matrix. If liveView is set than matrix is liveVew of this
	 * matrix, otherwise it is a new copy independent of this matrix.
	 * 
	 * @param row
	 *            row that will be eliminated.
	 * @param col
	 *            column that will be elimintated.
	 * @param liveView
	 *            flag for live view of matrix.
	 * @return Matrix that is submatrix of this matrix.
	 */
	IMatrix subMatrix(int row, int col, boolean liveView);
	/**
	 * Inverts the current matrix and returns the new matrix.
	 * 
	 * @return New inverted matrix.
	 * @throws RuntimeException
	 *             If matrix can't be inverted.
	 */
	IMatrix nInvert();

	/**
	 * Content of current matrix is copied in two dimensional field that is then
	 * returned.
	 * 
	 * @return Array that represents matrix.
	 */
	double[][] toArray();

	/**
	 * All elements of current matrix are multiplied with scalar. Current matrix
	 * is unchanged and new matrix that is result of operation is returned.
	 * 
	 * @param value
	 *            Value that all elements of matrix are multiplied.
	 * @return Reference of new resulting matrix.
	 */
	IMatrix nScalarMultiply(double value);

	/**
	 * Method that decomposes the matrix on it lower triangular matrix and its
	 * upper triangular matrix
	 * 
	 * @return IMatrix that is compact and it contains all elements of lower and
	 *         upper triangular matrix in a way that lower matrix has all
	 *         elements on main diagonal equal to one. Elements of upper
	 *         triangular matrix have elements on main diagonal.
	 */
	IMatrix luDecompose();
	/**
	 * Method that decomposes the matrix on it lower triangular matrix and its
	 * upper triangular matrix.
	 * This method changes this object so it is compact and it contains all elements of lower and
	 * upper triangular matrix in a way that lower matrix has all
	 * elements on main diagonal equal to one. Elements of upper
	 * triangular matrix have elements on main diagonal.
	 * @return Permutation matrix that contains order of rows. This matrix is then for forward and backward substitution.
	 */
	IMatrix lupDecompose();
	
	IMatrix forwardSupstitute(IMatrix b);
	
	IMatrix forwardSupstitute(IMatrix b, IMatrix permutationMatrix);
	
	IMatrix backwardSupstitute(IMatrix y);

	/**
	 * All elements of current are multiplied with scalar and changed.
	 * 
	 * @param value
	 *            Value that all elements of matrix are multiplied.
	 * @return Reference of current matrix.
	 */
	IMatrix scalarMultiply(double value);
	

	/**
	 * Method that solves the linear system Ax=B using LU decomposition and forward and backward substitution.
	 * @param A Matrix that can be found on the left side of equation that is used to multiply vector X.
	 * @param B Matrix that is a representation of vector that can be found on the right side of equation.
	 * @return Solution, matrix that holds solution(elements that are found in vector x)
	 *  or null if equation can't be solved. 
	 */
	public static IMatrix solveLU(IMatrix A, IMatrix B){
		IMatrix matCopy = A.copy();
		
		IMatrix Y = null;
		
		try {
			Y = matCopy.luDecompose().nLowerTriangular(true).forwardSupstitute(B);
		} catch (IllegalArgumentException e1) {
//			System.out.println(e1.getMessage());
//			return null;
			throw e1;
		}
		IMatrix X = null;
		try {			
			X = matCopy.nUpperTriangular(true).backwardSupstitute(Y);			
		} catch (IllegalArgumentException e) {
//			System.out.println("Can't solve linear system : \n" + e.getMessage());
			throw e;
		}
		return  X;
	}
	
	/**
	 * Method that solves the linear system PAx=PB, where A=LU using LUP decomposition and forward and backward substitution.
	 * @param A Matrix that can be found on the left side of equation that is used to multiply vector X.
	 * @param B Matrix that is a representation of vector that can be found on the right side of equation.
	 * @return Solution, matrix that holds solution(elements that are found in vector x) . 
	 */
	public static IMatrix solveLUP(IMatrix A, IMatrix B){
		IMatrix matCopy = A.copy();
		IMatrix perm = matCopy.lupDecompose();
		matCopy = perm.nMultiply(matCopy);
		
		IMatrix Y = null;
		try {
			Y = matCopy.nLowerTriangular(true).forwardSupstitute(B, perm);
		} catch (IllegalArgumentException e1) {
			throw e1;
//			System.out.println(e1.getMessage());
//			return null;
		}
		IMatrix X = null;
		try {
			X = matCopy.nUpperTriangular(true).backwardSupstitute(Y);
		} catch (IllegalArgumentException e) {
//			System.out.println("Can't solve linear system : \n" + e.getMessage());
			throw e;
		
		}
		return X;
	}
	/**
	 * Modifies current matrix in a way that it becomes identity matrix.
	 * 
	 * @return Reference of current matrix.
	 */
	IMatrix makeIdentity();
	/**
	 * Method that creates the swap matrix of required dimensions and swaps the first row with second.
	 * @param rows Number of rows of matrix.
	 * @param cols Number of columns of matrix.
	 * @param firstRow First row to swamp.
	 * @param secondRow Second row to swamp.
	 * @return Matrix that can be multiplied with arbitrary matrix to swap the rows. 
	 */
	IMatrix nSwapRowMatrix(int rows, int cols, int firstRow, int secondRow);
	
	/**
	 * Method that swaps specified rows in matrix.
	 * @param firstRow First row that will be swapped.
	 * @param secondRow Second row that will be swapped with first.
	 */
	void swapRows(int firstRow, int secondRow);

	/**
	 * Method that parses textual representation of the matrix and creates the
	 * new instance of Matrix.
	 * 
	 * @param sMatrix Textual representations of matrix contains elements with
	 *            delimiter of space or empty sign ,new row of matrix is
	 *            specified by \n sign.
	 * @return New instance of Matrix that is specified by textual representation.
	 */
	static IMatrix parseMatrix(String sMatrix) {
		String[] sMatrixRows = sMatrix.split("\\n");
		double[][] elements = new double[sMatrixRows.length][];
		int cols = 0;
		for (int i = 0; i < sMatrixRows.length; i++) {
			String sMatrixRow = sMatrixRows[i];
			if (sMatrixRow.equals("")) {
				throw new RuntimeException(
						"String representation of matrix contains empty row");
			}
			String[] rowElem = sMatrixRow.trim().split("\\s+");
			if (rowElem.length > cols) {
				cols = rowElem.length;
			}
			double[] rowElements = new double[cols];
			for (int j = 0; j < cols; j++) {
				rowElements[j] = Double.parseDouble(rowElem[j]);
			}
			elements[i] = rowElements;

		}

		for (int iRow = 0; iRow < elements.length; iRow++) {
			// if the number of columns is not the same as maximum number of
			// columns add zero elements 0d
			if (elements[iRow].length != cols) {
				elements[iRow] = Arrays.copyOf(elements[iRow], cols);
			}
		}
		return new Matrix(elements, elements.length, cols, true);
	}
	
	static IMatrix readFromPath(Path path){
		String stringMatrix = null;
		try {
			stringMatrix = new String(Files.readAllBytes(path));
		} catch (IOException e) {
			System.out.println("Can't read file with given path \n" + path);
			System.exit(0);
		} 
		return parseMatrix(stringMatrix);
	}
	
	static void writeToPath(Path path, IMatrix matrix){
		try {
			Files.write(
					path,
					matrix.toString().getBytes(),
					StandardOpenOption.CREATE,
					StandardOpenOption.WRITE);
		} catch (IOException e) {
			System.out.println("Can't write to given path \n"+ path);
		}
	}
}
