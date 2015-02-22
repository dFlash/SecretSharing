package com.maliavin.algorithms;

import org.apache.commons.math3.primes.Primes;

public class MinjotAlgo {
	
	private int k;
	private int n;
	private int secret;
	

	public MinjotAlgo(int k, int n)
	{
		this.k = k;
		this.n = n;
	}
	
	public void generateSecretParts()
	{
		
	}
	
	private int[] getNPrimeNumbers()
	{
		int [] p = new int[n];
		
		int z = 5;
		for (int i = 0; i < 10; i++) {
			int aaaa = Primes.nextPrime(z);
			z = aaaa;
			System.out.println(aaaa);
			z++;
		}
		
		return p;
	}
	
	
}
