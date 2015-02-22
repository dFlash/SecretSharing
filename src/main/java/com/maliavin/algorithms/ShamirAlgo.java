package com.maliavin.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.lang.Math.pow;;

public class ShamirAlgo {

	private int k;
	private int n;
	private int secret;
	

	public ShamirAlgo(int secret, int k, int n)
	{
		this.k = k;
		this.n = n;
		this.secret = secret;
	}
	
	public List<Integer> generatePartsOfSecret()
	{
		List<Integer> list = new ArrayList<Integer>();
		
		int [] randoms = generateKRandom();
		for (int i = 0; i < randoms.length; i++)
			System.out.println(randoms[i]);

		for (int i = 1; i < n+1; i++)
		{
			int sum = 0;
			for (int j = k - 1; j > 0; j--)
			{
				sum += randoms[j-1] * pow(i, j);
			}
			sum += secret;
			
			
			list.add(sum);
		}
		
		return list;
	}

	private int[] generateKRandom() {
		
		int [] randoms = new int [k-1];
		
		Random random = new Random();
		
		for (int i = 0; i < k - 1; i++)
		{
			randoms[i] = random.nextInt(10) + 1;
		}
		
		return randoms;
		
	}
	
	public long getSecret(List<Integer> listX, List<Integer> listY)
	{
		double secret=0;
		for (int i = 0; i < listX.size(); i++)
		{
			int xCur = listX.get(i);
			double lCur = 1;
			for (int j = 0; j < listX.size(); j++)
			{
				if (i==j)
				{
					continue;
				}
				int x = listX.get(j);
				lCur *= (double)(-x)/((double)xCur - (double)x);
			}
			secret += lCur*listY.get(i);
		}
		return Math.round(secret);
	}
}
