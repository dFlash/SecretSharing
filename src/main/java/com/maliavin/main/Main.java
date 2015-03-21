package com.maliavin.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.primes.Primes;
import org.ejml.simple.SimpleMatrix;

import Jama.Matrix;

import com.maliavin.algorithms.BlakleyAlgo;
import com.maliavin.algorithms.MinjotAlgo;
import com.maliavin.algorithms.ShamirAlgo;


public class Main {

	/**
	 * @param args
	 * 			terminal input parameters 
	 */
	public static void main(String[] args) {
		
		String str = null;
		try {
			str = new String(Files.readAllBytes(Paths.get("d:/Dmitriy/3.txt")));
		} catch (IOException e) {
			System.out.println("read error - " + e.toString());
		}
		System.out.println("1");
		
		BigInteger bi = new BigInteger(str.getBytes());
		
		ShamirAlgo sa = new ShamirAlgo(bi, 3, 5);
		List<BigInteger> list = sa.generatePartsOfSecret();
		
		long start = System.currentTimeMillis();
		
		List<Integer> listX = Arrays.asList(1,3,5);
		List<BigInteger> listY = Arrays.asList(list.get(0),list.get(2),list.get(4));
		
		BigInteger sec = sa.getSecret(listX, listY);
		
		byte [] bytes = sec.toByteArray();
		
		String str2 = new String(bytes);
		
		long time = start - System.currentTimeMillis();
		System.out.println("Time = " + time);
		
		try {
			PrintWriter pw = new PrintWriter("d:/testShamir.txt");
			pw.write(str2);
			pw.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("write error - " + e.toString());
		}
		
//		BlakleyAlgo ba = new BlakleyAlgo(bi, 3, 5);
//		ba.generateParts();
//		
//		long start = System.currentTimeMillis();
//		List<BigInteger> list1 = ba.getOnePartOfSecrets(1);
//		List<BigInteger> list2 = ba.getOnePartOfSecrets(3);
//		List<BigInteger> list3 = ba.getOnePartOfSecrets(4);
//		
//		
//		BigInteger [][] a = createAMatrix(list1, list2, list3);
//		BigInteger [] b = new BigInteger[3];
//		b[0] = list1.get(list1.size()-1);
//		b[1] = list2.get(list2.size()-1);
//		b[2] = list3.get(list3.size()-1);
//		
//		BigInteger detMain = ba.determinant(a);
//		BigInteger [][] mat1 = ba.createMatrix(a, b, 0);
//			
//		BigInteger det = ba.determinant(mat1);
//		
//		BigInteger secretBlackley = det.divide(detMain);
//		
//		byte [] bytes = secretBlackley.toByteArray();
//		
//		String str2 = new String(bytes);
//		
//		long time = start - System.currentTimeMillis();
//		System.out.println("Time = " + time);
//		
//		try {
//			PrintWriter pw = new PrintWriter("d:/testBlackley.txt");
//			pw.write(str2);
//			pw.close();
//			
//		} catch (FileNotFoundException e) {
//			System.out.println("write error - " + e.toString());
//		}
		
		
//		SimpleMatrix a = new SimpleMatrix(3,3);
		
//		setMatrixByList(0, a, list1);
//		setMatrixByList(1, a, list2);
//		setMatrixByList(2, a, list3);
		
//		System.out.println(a);
		
		//SimpleMatrix b = new SimpleMatrix(3, 1);
		
//		b.set(0, 0, (-1) * list1.get(list1.size()-1));
//		b.set(1, 0, (-1) * list2.get(list2.size()-1));
//		b.set(2, 0, (-1) * list3.get(list3.size()-1));
		
		
		
		//SimpleMatrix x = a.solve(b);
//		
//		System.out.println(x.toString());
		
//		MinjotAlgo mj = new MinjotAlgo(bi, 5, 7);
//		
//		System.out.println("2");
//		
//		mj.generateSecretParts();
//		
//		System.out.println("3");
//		
//		
//		BigInteger sec = mj.secretRecovery(0,1,2,3,4);
//		
//		
//		byte [] bytes = sec.toByteArray();
//		
//		String str2 = new String(bytes);
//		
//		try {
//			PrintWriter pw = new PrintWriter("d:/testMinjot.txt");
//			pw.write(str2);
//			pw.close();
//			
//		} catch (FileNotFoundException e) {
//			System.out.println("write error - " + e.toString());
//		}
//		
//		System.out.println(bi.toString());
		
	}
	
//	private static void setMatrixByList(int row, SimpleMatrix sm, List<Integer> list)
//	{
//		for (int i = 0; i < list.size() - 1; i++)
//		{
//			sm.set(row, i, list.get(i));
//		}
//	}
	
	private static BigInteger[][] createAMatrix(List<BigInteger> ... lists)
	{
		BigInteger[][] res = new BigInteger[lists.length][lists.length];
		
		int rowInd = 0;
		for (List<BigInteger> list : lists)
		{
			for (int i = 0; i < list.size() - 1; i++)
			{
				res[rowInd][i] = list.get(i);
			}
			rowInd++;
		}
		
		return res;
	}

}
