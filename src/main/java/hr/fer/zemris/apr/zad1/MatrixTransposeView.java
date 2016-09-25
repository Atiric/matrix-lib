package hr.fer.zemris.apr.zad1;

import javax.swing.text.WrappedPlainView;

/**
 * Class that is used as proxy for transpose view of matrix.
 * @author Armin Tirić
 *
 */
public class MatrixTransposeView extends AbstractMatrix {

	/**
	 * View that is being wrapped for transpose view.
	 */
	IMatrix wrapee;
	/**
	 * Constructor for {@link MatrixTransposeView} which is an proxy class for {@link AbstractMatrix}.
	 * In this case wrapee is {@link IMatrix} view.
	 * @param view View that is being adapted for transpose view.
	 */
	public MatrixTransposeView(IMatrix view) {
		this.wrapee = view;
	}

	@Override
	public int getRowsCount() {
		return wrapee.getColsCount();
	}
	@Override
	public int getColsCount() {
		return wrapee.getRowsCount();
	}
	@Override
	public double get(int row, int col) {
		return wrapee.get(col, row);
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		wrapee.set(col, row, value);
		return this;
	}

	@Override
	public IMatrix copy() {
		IMatrix copy = this.newInstance(this.getRowsCount(), this.getColsCount());
		for( int i = 0; i < this.getRowsCount(); i++){
			for( int j= 0 ; j< this.getColsCount();j++){
				copy.set(i, j, this.get(i, j));
			}
		}
		return copy;
		
	}
	@Override
	public IMatrix newInstance(int rows, int cols) {
		double [][] elements = new double[rows][];
		for( int i = 0 ; i< rows ;i++){
			elements[i] = new double[cols];
		}
		return new Matrix(elements, rows, cols, true);
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
			if ((i + 1) == getRowsCount()){
				break;
			}
			sb.append("\n");
		}
		//sb.append(" ]");
		return sb.toString();
	}
	
}
