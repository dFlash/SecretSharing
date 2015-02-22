package com.maliavin.main;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.primes.Primes;
import org.ejml.simple.SimpleMatrix;

import com.maliavin.algorithms.BlakleyAlgo;
import com.maliavin.algorithms.ShamirAlgo;


public class Main {

	/**
	 * @param args
	 * 			terminal input parameters 
	 */
	public static void main(String[] args) {
		
		ShamirAlgo sa = new ShamirAlgo(17, 4, 23);
		List<Integer> list = sa.generatePartsOfSecret();
		
		System.out.println(list.toString());
		
		List<Integer> listX = Arrays.asList(4,6,2,13);
		List<Integer> listY = Arrays.asList(list.get(3),list.get(5),list.get(1),list.get(12));
		
		System.out.println(sa.getSecret(listX, listY));
		
		BlakleyAlgo ba = new BlakleyAlgo(12, 3, 5);
		ba.generateParts();
		
		List<Integer> list1 = ba.getOnePartOfSecrets(1);
		List<Integer> list2 = ba.getOnePartOfSecrets(3);
		List<Integer> list3 = ba.getOnePartOfSecrets(4);
		
		SimpleMatrix a = new SimpleMatrix(3,3);
		
		setMatrixByList(0, a, list1);
		setMatrixByList(1, a, list2);
		setMatrixByList(2, a, list3);
		
		System.out.println(a);
		
		SimpleMatrix b = new SimpleMatrix(3, 1);
		
		b.set(0, 0, (-1) * list1.get(list1.size()-1));
		b.set(1, 0, (-1) * list2.get(list2.size()-1));
		b.set(2, 0, (-1) * list3.get(list3.size()-1));
		
		//System.out.println(b);
		
		
		SimpleMatrix x = a.solve(b);
		
		System.out.println(x.toString());
		


		
	}
	
	private static void setMatrixByList(int row, SimpleMatrix sm, List<Integer> list)
	{
		for (int i = 0; i < list.size() - 1; i++)
		{
			sm.set(row, i, list.get(i));
		}
	}

}
