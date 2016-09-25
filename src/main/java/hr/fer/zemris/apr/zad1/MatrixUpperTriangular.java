package hr.fer.zemris.apr.zad1;
/**
 * Proxy class for {@link AbstractMatrix}. This class is used as a model for upper triangle matrix.
 * @author Armin Tirić
 *
 */
public class MatrixUpperTriangular extends AbstractMatrix {
	
		IMatrix wrapee;

		public MatrixUpperTriangular(IMatrix matrix) {
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
			if (col < row) {
				return 0d;
			}
			return wrapee.get(row, col);
		}

		@Override
		public IMatrix set(int row, int col, double value) {
			if (col < row) {
				throw new IllegalArgumentException(
						"Changing the element will create invalid upper triangular matrix");
			}
			wrapee.set(row, col, value);
			return this;
		}

		@Override
		public IMatrix copy() {
			IMatrix copy = this.newInstance(
					this.getRowsCount(),
					this.getColsCount());
			for (int i = 0; i < this.getRowsCount(); i++) {
				for (int j = 0; j < this.getColsCount(); j++) {
					copy.set(i, j, this.get(i, j));
				}
			}
			return new MatrixUpperTriangular(copy);
		}

		@Override
		public IMatrix newInstance(int rows, int cols) {
			return wrapee.newInstance(rows, cols);
		}
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < getRowsCount(); i++) {
				for (int j = 0; j < getColsCount(); j++) {
					sb.append(get(i, j)).append(" ");
				}
				if ( (i + 1) == getRowsCount() ){
					break;
				}
				sb.append("\n");
			
			}
			return sb.toString();
		}

		@Override
		public void swapRows(int firstRow, int secondRow) {
			wrapee.swapRows(firstRow, secondRow);
			
		}

	}
