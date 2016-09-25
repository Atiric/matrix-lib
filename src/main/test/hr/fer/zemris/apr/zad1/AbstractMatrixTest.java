package hr.fer.zemris.apr.zad1;

import static org.junit.Assert.* ;


import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link AbstractMatrix} class.
 * The convention for testing the methods is :
 * [the name of the tested feature]_[expected input / tested state]_[expected behavior]
 * The convention for testing the features is : 
 * [the name of the tested feature]_[expected input / tested state]_[expected behavior]
 * @author Armin Tirić
 *
 */
public class AbstractMatrixTest{
	public static double EPSILON = 1E-9;
	public static String sampleStringMatrix;
	public static IMatrix sampleMatrix;
	IMatrix testMatrix;
	
	@Before
	public void setUp(){
		sampleStringMatrix = 
				"1 2 3\n"
				+ "4 5 6\n"
				+ " 7 8 9";
		testMatrix = new SparseMatrix(4,4);
		testMatrix.set(1, 0, 5.0);
		testMatrix.set(1,1,3.0);
	}
	
	@Before 
	public  void beforeTest() throws IOException {
		
	}
	@Test
	public void parseMatrix_StringRepresentationOfMatrixGiven_validParse(){ 
		IMatrix m = IMatrix.parseMatrix(sampleStringMatrix);
		assertEquals(1d, m.get(0, 0), EPSILON);
		assertEquals(2d, m.get(0, 1), EPSILON);
		assertEquals(3d, m.get(0, 2), EPSILON);
		assertEquals(4d, m.get(1, 0), EPSILON);
		assertEquals(5d, m.get(1, 1), EPSILON);
		assertEquals(6d, m.get(1, 2), EPSILON);
		assertEquals(7d, m.get(2, 0), EPSILON);
		assertEquals(8d, m.get(2, 1), EPSILON);
		assertEquals(9d, m.get(2, 2), EPSILON);
	}
	
	 @Test
	 public void showStringRepresentation_validMatrixGiven_validPrint(){
		 sampleMatrix = IMatrix.parseMatrix(sampleStringMatrix);
		 System.out.println( sampleMatrix ); 
	 }
	  
	 @Test
	 public void luDecompose_parsedMatrixGiven_correctDecompose(){
		 IMatrix A = IMatrix.parseMatrix(
				 "2 3 1 5\n"
				 + "6 13 5 19\n"
				 + "2 19 10 23\n"
				 + "4 10 11 31");
		 A.luDecompose();
		 
		 IMatrix expectedL = IMatrix.parseMatrix(
				 "1 0 0 0\n"
				 +"3 1  0 0\n"
				 +"1 4 1 0\n"
				 +"2 1 7 1");
		 
		 IMatrix expectedU = IMatrix.parseMatrix(
				 "2 3 1 5\n"
				 +"0 4 2 4\n"
				 +"0 0 1 2\n"
				 +"0 0 0 3");
		 IMatrix actualL = A.nLowerTriangular(true).copy();
		 IMatrix actualU = A.nUpperTriangular(true).copy();
		 
		 assertEquals(expectedU.toString(), actualU.toString());
		 assertEquals(expectedL.toString(), actualL.toString());
		
	 }
	 
	 @Test
	 public void solveUsingLUorLUP_linearEquasionThatHasSolution_CorrectResult(){
		 IMatrix A = IMatrix.parseMatrix(
				 "5 6 3\n"
				 + "3 5 4\n"
				 + "1 2 0");
		 IMatrix copy = A.copy();
		 A.luDecompose();
		 IMatrix b = IMatrix.parseMatrix(
				 "10.3\n"
				 +"12.5\n"
				 +"0.1");
		 IMatrix expectedY = IMatrix.parseMatrix(
				 "10.3\n"
				 +"6.32\n"
				 +"-5.571428571428569");
		 IMatrix y = A.forwardSupstitute(b);
		 
		 assertTrue(expectedY.equals(y));
		 
		 IMatrix expectedX = IMatrix.parseMatrix(
				 "0.5\n"
				 +"-0.2\n"
				 +"3");
		 IMatrix x = A.backwardSupstitute(y);
		 assertEquals(expectedX, x);
		 
		 IMatrix perm = copy.lupDecompose();
		 y = copy.forwardSupstitute(b, perm);
		 x = copy.backwardSupstitute(y);
		 
		 assertEquals(expectedX, x);
	 }
	 
	 
	 
	 
	 
	 //not my tests
	 
		

		
		@Test
		public void nTransposeLiveViewTrueTest(){
			IMatrix transposeMatrix = testMatrix.nTranspose(true);
			Assert.assertTrue(Math.abs(testMatrix.get(1, 0) - transposeMatrix.get(0, 1))<0.0001);
			
			testMatrix.set(2, 3,4.5);
			Assert.assertTrue(Math.abs(testMatrix.get(2, 3) - transposeMatrix.get(3, 2))<0.0001);
			
		}
		
		@Test
		public void nTransposeLiveViewFalseTest(){
			IMatrix transposeMatrix = testMatrix.nTranspose(false);
			Assert.assertTrue(Math.abs(testMatrix.get(1, 0) - transposeMatrix.get(0, 1))<0.0001);
			
			testMatrix.set(2, 3,4.5);
			Assert.assertFalse(Math.abs(testMatrix.get(2, 3) - transposeMatrix.get(3, 2))<0.0001);
			Assert.assertTrue(Math.abs(0 - transposeMatrix.get(3, 2))<0.0001);
		}
		
		@Test
		public void addTest(){
			Matrix secondMatrix = new Matrix(4,4);
			secondMatrix.set(1, 0, 4.3);
			secondMatrix.set(3, 3, 3.4);
			
			testMatrix.add(secondMatrix);
			Assert.assertTrue(Math.abs(testMatrix.get(1, 0) - 9.3)<0.0001);
			Assert.assertTrue(Math.abs(testMatrix.get(3, 3) - 3.4)<0.0001);
			
		}
		
		@Test(expected=IllegalArgumentException.class)
		public void addTestRowsFails(){
			Matrix secondMatrix = new Matrix(5,8);
			secondMatrix.set(1, 0, 4.3);
			secondMatrix.set(3, 3, 3.4);
			
			testMatrix.add(secondMatrix);
		}
		
		@Test(expected=IllegalArgumentException.class)
		public void addTestColsFails(){
			Matrix secondMatrix = new Matrix(4,8);
			secondMatrix.set(1, 0, 4.3);
			secondMatrix.set(3, 3, 3.4);
			
			testMatrix.add(secondMatrix);
		}
		
		@Test
		public void naddTest(){
			Matrix secondMatrix = new Matrix(4,4);
			secondMatrix.set(1, 0, 4.3);
			secondMatrix.set(3, 3, 3.4);
			
			IMatrix result = testMatrix.nAdd(secondMatrix);
			Assert.assertTrue(Math.abs(result.get(1, 0) - 9.3)<0.0001);
			Assert.assertTrue(Math.abs(testMatrix.get(1,0) - 5.0)<0.0001);
			
		}
		
		
		
		@Test
		public void subTest(){
			Matrix secondMatrix = new Matrix(4,4);
			secondMatrix.set(1, 0, 4.3);
			secondMatrix.set(3, 3, 3.4);
			
			testMatrix.sub(secondMatrix);
			Assert.assertTrue(Math.abs(testMatrix.get(1, 0) - 0.7)<0.0001);
			Assert.assertTrue(Math.abs(testMatrix.get(3, 3) - -3.4)<0.0001);
			
		}
		
		@Test(expected=IllegalArgumentException.class)
		public void subTestRowsFails(){
			Matrix secondMatrix = new Matrix(5,8);
			secondMatrix.set(1, 0, 4.3);
			secondMatrix.set(3, 3, 3.4);
			
			testMatrix.sub(secondMatrix);
		}
		
		@Test(expected=IllegalArgumentException.class)
		public void subTestColsFails(){
			Matrix secondMatrix = new Matrix(4,8);
			secondMatrix.set(1, 0, 4.3);
			secondMatrix.set(3, 3, 3.4);
			
			testMatrix.sub(secondMatrix);
		}
		
		@Test
		public void nsubTest(){
			Matrix secondMatrix = new Matrix(4,4);
			secondMatrix.set(1, 0, 4.3);
			secondMatrix.set(3, 3, 3.4);
			
			IMatrix result = testMatrix.nSub(secondMatrix);
			Assert.assertTrue(Math.abs(result.get(1, 0) - 0.7)<0.0001);
			Assert.assertTrue(Math.abs(testMatrix.get(1,0) - 5.0)<0.0001);
			
		}
		
		@Test(expected=IllegalArgumentException.class)
		public void nMultiplyTestFails(){
			Matrix secondMatrix = new Matrix(5,4);
			secondMatrix.set(1, 0, 4.3);
			secondMatrix.set(3, 3, 3.4);
			testMatrix.nMultiply(secondMatrix);

		}
		
		@Test
		public void nMultiplyTest(){
			Matrix secondMatrix = new Matrix(4,5);
			secondMatrix.set(1, 0, 4.3);
			secondMatrix.set(3, 3, 3.4);
			
			IMatrix result =testMatrix.nMultiply(secondMatrix);
			Assert.assertTrue(Math.abs(result.get(1, 0) - 12.9)<0.0001);
		}
		
		@Test
		public void determinantOneElementTest(){
			Matrix matrix = new Matrix(1,1);
			matrix.set(0, 0, -3);
			Assert.assertTrue(Math.abs(matrix.determinant() - (-3))<0.0001);
		}
		
		@Test
		public void determinantTest(){
			double[][] elements = {{1,2,3},{4,-5,4.5},{3,4,2}};
			Matrix matrix = new Matrix(elements, 3, 3, false);
		
			Assert.assertTrue(Math.abs(matrix.determinant() - 76)<0.0001);
			
		}
	
		
		@Test
		public void subMatrixLiveViewTrueTest(){
			IMatrix subMatrix = testMatrix.subMatrix(1, 1, true);
			Assert.assertEquals(3, subMatrix.getRowsCount());
			Assert.assertEquals(3, subMatrix.getColsCount());
			
			subMatrix.set(0, 0, -2);
			Assert.assertTrue(Math.abs(testMatrix.get(0, 0) - (-2))<0.0001);
		}
		
		@Test
		public void subMatrixLiveViewFalseTest(){
			IMatrix subMatrix = testMatrix.subMatrix(3, 3, false);
			Assert.assertEquals(3, subMatrix.getRowsCount());
			Assert.assertEquals(3, subMatrix.getColsCount());
			
			subMatrix.set(0, 0, -2);
			Assert.assertTrue(Math.abs(testMatrix.get(0, 0) - 0)<0.0001);
		}
		
		@Test(expected=UnsupportedOperationException.class)
		public void nInvertFailsTest(){
			double[][] elements = {{1,2},{1,2}};
			Matrix matrix = new Matrix(elements, 2, 2, false);
			matrix.nInvert();
			
		}
		
		@Test
		public void nInvertTest(){
			double[][] elements = {{4,4,4},{12,6,8}, {2,7,99}};
			Matrix matrix = new Matrix(elements, 3, 3, false);
			IMatrix inverted= matrix.nInvert();

			Assert.assertTrue(Math.abs(inverted.get(0, 0) - (-0.2393))<0.0001);
			Assert.assertTrue(Math.abs(inverted.get(0, 1) - 0.1637)<0.0001);
			Assert.assertTrue(Math.abs(inverted.get(0, 2) - (-0.0036))<0.0001);
			Assert.assertTrue(Math.abs(inverted.get(1, 0) - 0.5214)<0.0001);
			Assert.assertTrue(Math.abs(inverted.get(1, 1) - (-0.1726))<0.0001);
			Assert.assertTrue(Math.abs(inverted.get(1, 2) - (-0.0071))<0.0001);
			Assert.assertTrue(Math.abs(inverted.get(2, 0) - (-0.0320))<0.0001);
			Assert.assertTrue(Math.abs(inverted.get(2, 1) - (-0.0089))<0.0001);
			Assert.assertTrue(Math.abs(inverted.get(2, 2) - 0.0107)<0.0001);
		}
		
		@Test
		public void toArrayTest(){
			double[][] elements = {{1,2,3},{4,-5,4.5},{3,4,2}};
			Matrix matrix = new Matrix(elements, 3, 3, false);
			
			double[][] result= matrix.toArray();
			Assert.assertArrayEquals(elements, result);
			
		}
		
		@Test 
		public void nScalarMultiplyTest(){
			IMatrix newMatrix = testMatrix.nScalarMultiply(3.0);
		
			
			Assert.assertTrue(Math.abs(newMatrix.get(1, 0) - (15))<0.0001);
			Assert.assertTrue(Math.abs(newMatrix.get(1, 1) - 9)<0.0001);
			Assert.assertNotSame(newMatrix, testMatrix);
		}
		
		
		
		@Test
		public void makeIdentityTest(){
			testMatrix.makeIdentity();
			
			Assert.assertTrue(Math.abs(1 - testMatrix.get(0, 0))<0.0001);
			Assert.assertTrue(Math.abs(1 - testMatrix.get(1, 1))<0.0001);
			Assert.assertTrue(Math.abs(1 - testMatrix.get(2, 2))<0.0001);
			Assert.assertTrue(Math.abs(1 - testMatrix.get(3, 3))<0.0001);
			
		}
	 
	
	
}
