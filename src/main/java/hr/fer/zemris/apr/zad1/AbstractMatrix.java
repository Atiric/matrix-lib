package hr.fer.zemris.apr.zad1;

public abstract class AbstractMatrix implements IMatrix {

	@Override
	public IMatrix nTranspose(boolean liveView) {
		if( liveView ){
			return new MatrixTransposeView(this); 
		}else{
			return new MatrixTransposeView(this.copy());
		}
	}

	@Override
	public IMatrix add(IMatrix other) {
		if( (this.getRowsCount() != other.getRowsCount() )
				|| this.getColsCount() != other.getColsCount() ){
			throw new IllegalArgumentException("not the same size");
		}
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		for( int i = 0; i < rows; i++){
			for( int j= 0 ; j< cols;j++){
				this.set(i, j, this.get(i, j) + other.get(i, j));
			}
		}
		return this;
	}

	@Override
	public IMatrix nAdd(IMatrix other) {
		return this.copy().add(other);
	}

	@Override
	public IMatrix sub(IMatrix other) {
		if( (this.getRowsCount() != other.getRowsCount() )
				|| this.getColsCount() != other.getColsCount() ){
			throw new IllegalArgumentException("not the same size");
		}
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		for( int i = 0; i < rows; i++){
			for( int j= 0 ; j< cols;j++){
				this.set(i, j, this.get(i, j) - other.get(i, j));
			}
		}
		return this;
	}

	@Override
	public IMatrix nSub(IMatrix other) {
		return this.copy().sub(other);
	}

	@Override
	public IMatrix nMultiply(IMatrix other) {
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		int oRows = other.getRowsCount();
		int oCols = other.getColsCount();
		if( cols != oRows){
			throw new IllegalArgumentException("Number of columns of current matrix are not the same as number of rows of other matrix.");
		}
		IMatrix result = this.newInstance(rows, oCols);
		for(int i=0;i<rows;i++){
			for(int j=0;j<oCols;j++){
				double sum = 0;
				for(int k=0;k<rows;k++){
				  sum+=this.get(i, k)*other.get(k, j);
	           	}
				result.set(i, j, sum);
			}
	    }
		return result;
	}

	@Override
	public double determinant() {
		return 0d;
	}

	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[][] toArray() {
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		double[][] array = new double[rows][];
		
		for (int i = 0; i < rows; i++) {
			array[i] = new double[cols];
			for (int j = 0; j < cols; j++) {
				array[i][j] = get(i, j);
			}
		}
		return array;
	}

	@Override
	public IMatrix nScalarMultiply(double value) {
		return this.copy().scalarMultiply(value);
	}

	@Override
	public IMatrix scalarMultiply(double value) {
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		for( int i = 0; i < rows; i++){
			for( int j= 0 ; j< cols;j++){
				this.set(i, j, this.get(i, j)*value);
			}
		}
		return this;
	}

	@Override
	public IMatrix makeIdentity() {
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		for(int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++) {
				if ( i==j ){
					set(i, j, 1d);
				} else {
					set(i, j, 0d);
				}
			}
		}
		return this;
	}
	
	@Override
	public IMatrix nSwapRowMatrix(int rows, int cols, int firstRow,
			int secondRow) {
		if ( firstRow < 0 || firstRow >= rows ) {
			throw new IllegalArgumentException("Invalid parameter for first row of matrix for swaping given");
		}
		
		if ( secondRow < 0 || secondRow >= rows ) {
			throw new IllegalArgumentException("Invalid parameter for second row of matrix for swaping given");
		}
		IMatrix swapMatrix = newInstance(rows, cols);
		
		swapMatrix.makeIdentity();
		swapMatrix.set(firstRow, firstRow, 0d);
		swapMatrix.set(secondRow, secondRow, 0d);
		
		swapMatrix.set(firstRow, secondRow, 1d);
		swapMatrix.set(secondRow, firstRow, 1d);
		
		return swapMatrix;
	}
	
	@Override
	public IMatrix luDecompose() {
		// it should be the same cols or rows since its square matrix
		int rows = this.getRowsCount();
		int cols = this.getColsCount();

		
		if (rows != cols) {
			throw new IllegalArgumentException(
					"given matrix is not square matrix");
		}
		System.out.println("Matrix: \n" + this);
		for (int k = 0; k < rows - 1; k++) {
			double pivot = this.get(k, k);
			System.out.println("==========\nIteration K = " + (k + 1)+ " \n==========");
			System.out.println("Current pivot is " + pivot + "\n----------");
			for (int i = k + 1; i < rows; i++) {
				if( Math.abs(pivot) < 1E-9 ){
					
					throw new IllegalArgumentException(
							"Element on main diagonal can't be zero value");
					
				}
				this.set( i, k, this.get(i, k) / pivot );
				
				for (int j = k + 1; j < cols; j++) {
					this.set(i, j, this.get(i, j) - this.get(i, k) * this.get(k, j) );
				}
			}
			
			System.out.println(this);
		}
		
		return this;
	}
	
	@Override
	public IMatrix nLowerTriangular(boolean liveView) {
		if ( liveView ) {
			return new MatrixLowerTriangular(this);
		} else {
			return new MatrixLowerTriangular(this.copy());
		}
	}
	
	@Override
	public IMatrix nUpperTriangular(boolean liveView) {
		if ( liveView ) {
			return new MatrixUpperTriangular(this);
		} else {
			return new MatrixUpperTriangular(this.copy());
		}
	}
	
	@Override
	public IMatrix forwardSupstitute(IMatrix b) {
		IMatrix permMatrix = this.newInstance(
				this.getRowsCount(),
				this.getColsCount()).makeIdentity();
		
		return forwardSupstitute(
				b,
				permMatrix);
	}
	
	@Override
	public IMatrix forwardSupstitute(IMatrix b, IMatrix permutationMatrix) {
//		if ( b.getColsCount() != 1 ){
//			throw new IllegalArgumentException(
//					"Given parameter matrix b is not a column  vector");			
//		}
		//TODO 5 zadatak nesto tu zajebava
		int rows = this.getRowsCount();
		int cols = this.getColsCount();

		
		if (rows != cols) {
			throw new IllegalArgumentException(
					"Given matrix is not square matrix");
		}
		
		IMatrix result = permutationMatrix.nMultiply(b);
		
		for (int i = 0; i < rows - 1; i++) {
			for (int j = i + 1; j < rows; j++) {
				double temp = get(j, i)* result.get(i, 0);
				result.set(j, 0, result.get(j, 0) - temp  );
			}
			
		}
		return result;
	}
	
	@Override
	public IMatrix lupDecompose() {
		// it should be the same cols or rows since its square matrix
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
					
		if (rows != cols) {
			throw new IllegalArgumentException(
					"given matrix is not square matrix");
		}
		
		//initialize array for pivot 
		int[] P = new int[rows];
		for (int i = 0; i < P.length; i++) {
			P[i] = i;  
		}
		
		//System.out.println("Matrix: \n" + this);
		for (int k = 0; k < rows - 1; k++) {
			int pivotIndex = k;
			
			for(int i = k + 1; i < cols; i++){
				double testPivot = Math.abs(get(P[i],k));
				double currentPivot = Math.abs( get(P[pivotIndex], k));
				if(  (testPivot - currentPivot)  > IMatrix.EPSILON ){
						pivotIndex = i;
				}
			}
			
			//if pivot index has changed
			if( P[k] != P[pivotIndex] ){
				int temp = P[k];
				P[k] = P[pivotIndex];
				P[pivotIndex] = temp;
			}
			double pivot = this.get(P[k], k);
			//System.out.println("==========\nIteration K = " + (k + 1)+ " \n==========");
			//System.out.println("Current pivot is " + pivot + "\n----------");
			
			for (int i = k + 1; i < rows; i++) {
				if( Math.abs(pivot) < 1E-9 ){
					
					throw new IllegalArgumentException(
							"Element on main diagonal can't be zero value");
					
				}
				this.set( P[i], k, this.get(P[i], k) / pivot );
				
				for (int j = k + 1; j < cols; j++) {
					this.set(P[i], j, this.get(P[i], j) - this.get(P[i], k) * this.get(P[k], j) );
				}
			}
			//System.out.println(createPermutationMatrix(P).nMultiply(this));
		}
		return createPermutationMatrix(P);
		
	}
	/**
	 * Method that inverts the matrix.
	 * @return Inverted matrix.
	 */
	@Override
	public IMatrix nInvert() {
		
		IMatrix matCopy = copy();
		IMatrix perm = matCopy.lupDecompose();
		matCopy = perm.nMultiply(matCopy);
		IMatrix inverted = newInstance(getRowsCount(), getColsCount());
		for (int i = 0; i < getColsCount(); i++) {
			
			IMatrix B = newInstance(getRowsCount(), 1);
			B.set(i, 0, 1);
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
			for (int j = 0; j < X.getRowsCount(); j++) {
				inverted.set(j, i, X.get(j, 0));
			}
		}
		return inverted;
			
	}
	
	/**
	 * Method that copies the given column.
	 * @param i Index of column starting with 0.
	 * @return IMatrix column matrix with elements on index i.
	 */
	private IMatrix copyColumn(int i){
		IMatrix column = this.newInstance(getRowsCount(), 1);
		for(int j = 0; i < getRowsCount();j++){
			column.set(j, 0, get(j, i));
		}
		return column;
	}
	/**
	 * Method that creates the permutation matrix that is instance of {@link Matrix}
	 *  from array that contains the order of rows.
	 * @param arrayOfPermutation Array that contains order of row vectors.
	 * @return New instance of {@link Matrix} that is square matrix specified by the rank defined
	 *  by the length of the array of permutation.
	 */
	private IMatrix createPermutationMatrix(int[] arrayOfPermutation){
		int N = arrayOfPermutation.length;
		IMatrix permutationMatrix = this.newInstance(N, N);
		for (int i = 0; i < arrayOfPermutation.length; i++) {
			permutationMatrix.set(i, arrayOfPermutation[i], 1d);
		}
		return permutationMatrix;
	}
	@Override
	public IMatrix backwardSupstitute(IMatrix y) {
//		if ( y.getColsCount() != 1 ){
//			throw new IllegalArgumentException(
//					"Given parameter matrix y is not a column  vector");			
//		}
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		
		if (rows != cols) {
			throw new IllegalArgumentException(
					"Given matrix is not square matrix");
		}
		IMatrix finalResult = y.copy();
		for( int i = rows-1; i >= 0 ; i--){
			double currentPivot = get(i,i);
			if ( Math.abs(currentPivot) < IMatrix.EPSILON ){
				throw new IllegalArgumentException(
						"Can't divide by zero on index ("+i+","+i+")."
						+"\nZero division happened in backward supstituton.");
			}
			finalResult.set(i, 0, finalResult.get(i, 0) / get(i, i) );
			for (int j = 0; j < i; j++) {
				finalResult.set(j, 0, finalResult.get(j, 0) - get(j, i) * finalResult.get(i, 0));
			}
		}
		return finalResult;
	}

}
