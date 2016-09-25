package hr.fer.zemris.apr.zad1;

import java.util.HashMap;
import java.util.Map.Entry;


public class SparseMatrix extends AbstractMatrix{

	HashMap<String, Double> matrix;
	
	private int rows;
	/**
	 * Number of cols.
	 */
	private int cols;

	public SparseMatrix(int rows, int cols) {
		matrix = new HashMap<String, Double>();
		this.rows = rows;
		this.cols = cols;
	}

	static String formIndex(int x,  int y){
		return x +","+ y;
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
		if( row > rows || col > cols ){
			throw new IllegalArgumentException("Can't get element, unreachable index");
		}
		Double res = matrix.get(formIndex(row, col)); 
		return( res == null ?  0d : res);
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		matrix.put(formIndex(row, col), value);
		return this;
	}
	
	@Override
	public IMatrix copy() {
		SparseMatrix sm = new SparseMatrix(rows, cols);
		//yeah yeah i know, but not enough time
		for(Entry<String, Double> entry : matrix.entrySet()){
			sm.matrix.put(entry.getKey(), entry.getValue().doubleValue());
		}
		return sm;
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return new SparseMatrix(rows,cols);
		
	}

	@Override
	public void swapRows(int firstRow, int secondRow) {
		throw new IllegalStateException("Pls don't swap row");		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for( int i = 0; i < rows;i ++){
			for ( int j = 0; j < cols; j++){
				sb.append( get(i,j) ).append(" ");
			}
			if ((i + 1) == rows){
				break;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
