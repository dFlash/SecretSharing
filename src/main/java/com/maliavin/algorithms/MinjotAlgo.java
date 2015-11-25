package com.maliavin.algorithms;

import java.math.BigInteger;
import java.util.ArrayList;

import org.apache.commons.math3.primes.Primes;

public class MinjotAlgo {
	
	private int k;
	private int n;
	private BigInteger secret;
	private BigInteger [] parts;
	private BigInteger [] p;
	private BigInteger startValue = new BigInteger(
			"100000000000000000000000000000");

	public MinjotAlgo(BigInteger secret, int k, int n)
	{
		this.k = k;
		this.n = n;
		
		parts = new BigInteger[n];
		
		this.secret = secret;
	}
	
	public void generateSecretParts()
	{
		p = getNPrimeNumbers(startValue);
		BigInteger a = setA(p);
		BigInteger b = setB(p);
		
		long count = 0l;
		
		//System.out.println("secret = " + secret);
		
		while (secret.abs().compareTo(a.abs()) != -1 || secret.compareTo(b.abs()) != 1)
		{
			p = getNPrimeNumbers(p[0]);
			a = setA(p);
			b = setB(p);
			System.out.println("count = " + count++);
		}
		
//		setSecret(a, b);
		
		for (int i = 0; i < n; i++)
		{
			parts[i] = secret.mod(p[i]);
		}
	}
	
	private BigInteger[] getNPrimeNumbers(BigInteger startValue)
	{
		BigInteger [] p = new BigInteger[n];
		
		BigInteger z = startValue;
		for (int i = 0; i < n; i++) {
			BigInteger aaaa = z.nextProbablePrime();
			z = aaaa;
			p[i] = aaaa;
			z = z.add(BigInteger.valueOf(1));
		}
		
		return p;
	}
	
	private BigInteger setA(BigInteger[] p)
	{
		BigInteger a = BigInteger.valueOf(1);
		for (int i = 0; i < k; i++)
		{
			a = a.multiply(p[i]); 
		}
		
		return a;
	}
	
	private BigInteger setB(BigInteger[] p)
	{
		BigInteger b = BigInteger.valueOf(1);
		for (int i = n-k+2; i < n; i++)
		{
			b = b.multiply(p[i]); 
		}
		
		return b;
	}
	
//	private void setSecret(int a, int b)
//	{
//		secret = b + (int)((a - b) / 2);
//	}
	
	public BigInteger getSecret()
	{
		return secret;
	}
	
	public BigInteger[] getParts()
	{
		return parts;
	}
	
	public BigInteger secretRecovery(int ... partsIndexes)
	{
		BigInteger M = BigInteger.valueOf(1);
		
		for (int partInd : partsIndexes)
		{
			M = M.multiply(p[partInd]);
		}
		
		System.out.println("recovery 1");
		
		ArrayList<BigInteger> mi = new ArrayList<>();
		
		for (int partInd : partsIndexes)
		{
			BigInteger m = M.divide( p[partInd]);
			mi.add(m);
		}
		
		System.out.println("recovery 2");
		
		ArrayList<BigInteger> M_i = new ArrayList<>();
		int index = 0;
		for (int part : partsIndexes)
		{
			BigInteger m_ = getRevertNum(p[part], mi.get(index++));
			M_i.add(m_);
		}
		
		System.out.println("recovery 3");
		
		BigInteger sec = BigInteger.valueOf(0);
		index = 0;
		for (int part : partsIndexes)
		{
			BigInteger biMi = mi.get(index);
			BigInteger biM_i = M_i.get(index);
			BigInteger temp = this.parts[part].multiply(biMi).multiply(biM_i);
			sec = sec.add(temp);
			index++;
		}
		
		System.out.println("recovery 4");
		
		sec = sec.mod(M);
		
		return sec;
	}
	
	private BigInteger getRevertNum(BigInteger m, BigInteger M)
	{
		BigInteger m_ = BigInteger.valueOf(1);
		
		while(true)
		{
			BigInteger tempMultiply = M.multiply(m_);
			if( !tempMultiply.divide(m).equals(BigInteger.valueOf(1)))
			{
				m_ = m_.add(BigInteger.valueOf(1));
				continue;
			}
			else
			{
				break;
			}
		}
		
		return m_;
	}
	
}
