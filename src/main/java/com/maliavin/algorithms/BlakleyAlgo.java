package com.maliavin.algorithms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlakleyAlgo {

	private int k;
	private int n;
	private BigInteger secret;
	
	private List<List<BigInteger>> secrets;
	

	public BlakleyAlgo(BigInteger secret, int k, int n)
	{
		this.k = k;
		this.n = n;
		this.secret = secret;
		
		secrets = new ArrayList<List<BigInteger>>();
	}
	
	public void generateParts()
	{
		BigInteger [] randomSecrets = generateKRandomSecrets();
		
		for (int i = 0; i < n; i++)
		{
			List<BigInteger> onePart = new ArrayList<BigInteger>();
			
			BigInteger [] randoms = generateKRandom();
			
			BigInteger d = new BigInteger("0");
			
			for (int j = 0; j < k; j++)
			{
				onePart.add(randoms[j]);
				BigInteger tempMul = randoms[j].multiply(randomSecrets[j]);
				d = d.add(tempMul);
			}
			
			//d = d.multiply(BigInteger.valueOf(-1));
			
			onePart.add(d);
			
			secrets.add(onePart);
		}
		
		//System.out.println("secrets - " + secrets.toString());
		
		
	}
	
	public List<BigInteger> getOnePartOfSecrets(int index)
	{
		return secrets.get(index);
	}
	
	
	private BigInteger[] generateKRandomSecrets() {
		
		BigInteger [] randoms = new BigInteger [k];
		
		//Random random = new Random();
		
		for (int i = 0; i < k; i++)
		{
			if (i==0)
			{
				randoms[i] = secret;
			}
			else
			{
				randoms[i] = new BigInteger(2, new Random());
			}
			
		}
		
		return randoms;
		
	}
	
	private BigInteger[] generateKRandom() {
		
		BigInteger [] randoms = new BigInteger [k];
		
		//Random random = new Random();
		
		for (int i = 0; i < k; i++)
		{
				randoms[i] = new BigInteger(2, new Random());	
		}
		
		return randoms;
		
	}
	
	public BigInteger determinant(BigInteger[][] matrix){ //method sig. takes a matrix (two dimensional array), returns determinant.
		BigInteger sum=new BigInteger("0");
		BigInteger s;
	    if(matrix.length==1){  //bottom case of recursion. size 1 matrix determinant is itself.
	      return(matrix[0][0]);
	    }
	    for(int i=0;i<matrix.length;i++){ //finds determinant using row-by-row expansion
	    	BigInteger[][]smaller= new BigInteger[matrix.length-1][matrix.length-1]; //creates smaller matrix- values not in same row, column
	      for(int a=1;a<matrix.length;a++){
	        for(int b=0;b<matrix.length;b++){
	          if(b<i){
	            smaller[a-1][b]=matrix[a][b];
	          }
	          else if(b>i){
	            smaller[a-1][b-1]=matrix[a][b];
	          }
	        }
	      }
	      if(i%2==0){ //sign changes based on i
	        s = new BigInteger("1");
	      }
	      else{
	        s = new BigInteger("-1");;
	      }
	      BigInteger tempMul = s.multiply(matrix[0][i]).multiply((determinant(smaller)));
	      sum = sum.add(tempMul); //recursive step: determinant of larger determined by smaller.
	    }
	    return sum; //returns determinant value. once stack is finished, returns final determinant.
	  }
	
	public BigInteger[][] createMatrix(BigInteger[][] matrix, BigInteger[] values, int index)
	{
		BigInteger[][] res = new BigInteger[matrix.length][matrix.length];
		
		for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix.length; j++)
			{
				if (j == index)
				{
					res[i][j] = values[i];
				}
				else
				{
					res[i][j] = matrix[i][j];
				}
			}
		}
		
		return res;
	}
}
