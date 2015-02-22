package com.maliavin.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlakleyAlgo {

	private int k;
	private int n;
	private int secret;
	
	private List<List<Integer>> secrets;
	

	public BlakleyAlgo(int secret, int k, int n)
	{
		this.k = k;
		this.n = n;
		this.secret = secret;
		
		secrets = new ArrayList<List<Integer>>();
	}
	
	public void generateParts()
	{
		int [] randomSecrets = generateKRandomSecrets();
		
		for (int i = 0; i < n; i++)
		{
			List<Integer> onePart = new ArrayList<Integer>();
			
			int [] randoms = generateKRandom();
			
			int d = 0;
			
			for (int j = 0; j < k; j++)
			{
				onePart.add(randoms[j]);
				d += randoms[j] * randomSecrets[j];
			}
			
			d *= (-1);
			
			onePart.add(d);
			
			secrets.add(onePart);
		}
		
//		System.out.println(secrets.toString());
		
		
	}
	
	public List<Integer> getOnePartOfSecrets(int index)
	{
		return secrets.get(index);
	}
	
	
	private int[] generateKRandomSecrets() {
		
		int [] randoms = new int [k];
		
		Random random = new Random();
		
		for (int i = 0; i < k; i++)
		{
			if (i==0)
			{
				randoms[i] = secret;
			}
			else
			{
				randoms[i] = random.nextInt(10) + 1;
			}
			
		}
		
		return randoms;
		
	}
	
	private int[] generateKRandom() {
		
		int [] randoms = new int [k];
		
		Random random = new Random();
		
		for (int i = 0; i < k; i++)
		{
				randoms[i] = random.nextInt(10) + 1;	
		}
		
		return randoms;
		
	}
	
	
}
