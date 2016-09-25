package hr.fer.zemris.apr.zad1;

public class MatrixLowerTriangular extends AbstractMatrix {

	IMatrix wrapee;

	public MatrixLowerTriangular(IMatrix matrix) {
		this.wrapee = matrix;
	}

	@Override
	public int getRowsCount() {
		return wrapee.getRowsCount();
	}

	@Override
	public int getColsCount() {
		return wrapee.getColsCount();
	}

	@Override
	public double get(int row, int col) {
		if (row == col) {
			return 1d;
		} else if (col > row) {
			return 0d;
		}

		return wrapee.get(row, col);
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		if (row == col || col > row) {
			throw new IllegalArgumentException(
					"Changing the element will create invalid lower triangular matrix");
		}
		wrapee.set(row, col, value);
		return this;
	}

	@Override
	public IMatrix copy() {
		IMatrix copy = this.newInstance(this.getRowsCount(),
				this.getColsCount());
		for (int i = 0; i < this.getRowsCount(); i++) {
			for (int j = 0; j < this.getColsCount(); j++) {
				copy.set(i, j, this.get(i, j));
			}
		}
		return new MatrixLowerTriangular(copy);
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return wrapee.newInstance(rows, cols);
	}
	@Override
	public void swapRows(int firstRow, int secondRow) {
		wrapee.swapRows(firstRow, secondRow);
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//sb.append("[ ");
		for (int i = 0; i < getRowsCount(); i++) {
			for (int j = 0; j < getColsCount(); j++) {
				sb.append(get(i, j)).append(" ");
			}
			if ( (i + 1) == getRowsCount() ){
				break;
			}
			sb.append("\n");
		
		//sb.append(" ]");
		
	
		}
		return sb.toString();
	}

}
