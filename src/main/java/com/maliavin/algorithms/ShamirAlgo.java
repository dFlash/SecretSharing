package com.maliavin.algorithms;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.lang.Math.pow;;

public class ShamirAlgo {

	private int k;
	private int n;
	private BigInteger secret;
	

	public ShamirAlgo(BigInteger secret, int k, int n)
	{
		this.k = k;
		this.n = n;
		this.secret = secret;
	}
	
	public List<BigInteger> generatePartsOfSecret()
	{
		List<BigInteger> list = new ArrayList<BigInteger>();
		
		int [] randoms = generateKRandom();
//		for (int i = 0; i < randoms.length; i++)
//			System.out.println(randoms[i]);

		for (int i = 1; i < n+1; i++)
		{
			BigInteger sum = new BigInteger("0");
			for (int j = k - 1; j > 0; j--)
			{
				Double part = randoms[j-1] * pow(i, j);
				Integer intPart = part.intValue();
				sum = sum.add(new BigInteger(intPart.toString()));
			}
			sum = sum.add(secret);
			
			
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
	
	public BigInteger getSecret(List<Integer> listX, List<BigInteger> listIntY)
	{
		List<BigDecimal> listY = new ArrayList<BigDecimal>();
		for(BigInteger i : listIntY)
		{
			listY.add(new BigDecimal(i));
		}
		
		BigDecimal secret = BigDecimal.valueOf(0.0);
		for (int i = 0; i < listX.size(); i++)
		{
			int xCur = listX.get(i);
			double lCur = 1.0;
			for (int j = 0; j < listX.size(); j++)
			{
				if (i==j)
				{
					continue;
				}
				int x = listX.get(j);
				lCur *= (double)(-x)/((double)xCur - (double)x);
			}
			secret = secret.add(listY.get(i).multiply(BigDecimal.valueOf(lCur)));
		}
		
		BigInteger bi = secret.toBigInteger();
		
		return bi;
	}
}
