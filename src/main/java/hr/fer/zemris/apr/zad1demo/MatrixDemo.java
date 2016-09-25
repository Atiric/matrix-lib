package hr.fer.zemris.apr.zad1demo;

import hr.fer.zemris.apr.zad1.IMatrix;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MatrixDemo {

	public static void main(String[] args) {
		IMatrix X = null;
//		//drugi zadatak
//		Path secondTaskPath = Paths.get("res/2A.txt");
//		Path secondB = Paths.get("res/2B.txt");
//		IMatrix matrix2A = IMatrix.readFromPath(secondTaskPath);
//		IMatrix matrix2B = IMatrix.readFromPath(secondB);
//		
//		X = IMatrix.solveLU(matrix2A, matrix2B);
//		System.out.println(
//				"Rješenje sustava 2 sa LU je :\n" 
//				+ X);
//		
//		X = IMatrix.solveLUP(matrix2A, matrix2B);
//		System.out.println(
//				"Rješenje sustava 2 sa LUP je :\n" 
//				+ X);
//		
//		//treci zadatak
//		
//		IMatrix matrix3A = IMatrix.readFromPath(Paths.get("res/3.txt"));
//		
//		IMatrix matrix3B = IMatrix.parseMatrix(
//				"6\n"
//				+"15\n"
//				+"24");
//		
//		
//		X =IMatrix.solveLU(matrix3A, matrix3B);
//		System.out.println(
//				"Rješenje sustava 3 sa LU je :\n" 
//				+ X);
//		
//		X = IMatrix.solveLUP(matrix3A, matrix3B);
//		System.out.println(
//				"Rješenje sustava 3 sa LUP je :\n" 
//				+ X);
//	//četvrti zadatak
//	IMatrix matrix4A = IMatrix.readFromPath(Paths.get("res/4A.txt"));
//	IMatrix matrix4B = IMatrix.readFromPath(Paths.get("res/4B.txt"));
//	
//	X =IMatrix.solveLU(matrix4A, matrix4B);
//	System.out.println(
//			"Rješenje sustava 4 sa LU je :\n" 
//			+ X);
//	
//	X = IMatrix.solveLUP(matrix4A, matrix4B);
//	System.out.println(
//			"Rješenje sustava 4 sa LUP je :\n" 
//			+ X);
//	//peti zadatak
	IMatrix matrix5A = IMatrix.readFromPath(Paths.get("res/5A.txt"));
	IMatrix matrix5B = IMatrix.readFromPath(Paths.get("res/5B.txt"));
	
//	X = IMatrix.solveLU(matrix5A, matrix5B);
//	System.out.println(
//			"Rješenje sustava 5 sa LU je :\n" 
//			+ X);
	
//	X = IMatrix.solveLUP(matrix5A, matrix5B);
//	System.out.println(
//			"Rješenje sustava 5 sa LUP je :\n" 
//			+ X);
//	
//	//šesti zadatak
//	IMatrix matrix6A = IMatrix.readFromPath(Paths.get("res/6A.txt"));
//	IMatrix matrix6B = IMatrix.readFromPath(Paths.get("res/6B.txt"));
//	
//	X = IMatrix.solveLU(matrix6A, matrix6B);
//	System.out.println(
//			"Rješenje sustava 6 sa LU je :\n" 
//			+ X);
//	
//	X = IMatrix.solveLUP(matrix6A, matrix6B);
//	System.out.println(
//			"Rješenje sustava 6 sa LUP je :\n" 
//			+ X);
	X = IMatrix.parseMatrix("1 5 3\n2 1 0\n3 0 2");
	IMatrix B = IMatrix.parseMatrix("1\n2\n3");
	IMatrix solution = IMatrix.solveLUP(X, B);
	System.out.println("Rješenje je :\n" + solution );
	

	
	}
	
}
