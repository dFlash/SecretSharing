package com.maliavin.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.math3.primes.Primes;
import org.ejml.simple.SimpleMatrix;
import org.jfree.ui.RefineryUtilities;

import Jama.Matrix;

import at.archistar.crypto.RabinBenOrEngine;
import at.archistar.crypto.data.Share;
import at.archistar.crypto.decode.BerlekampWelchDecoderFactory;
import at.archistar.crypto.math.gf256.GF256Factory;
import at.archistar.crypto.random.BCDigestRandomSource;
import at.archistar.crypto.secretsharing.KrawczykCSS;
import at.archistar.crypto.symmetric.AESEncryptor;

import com.maliavin.algorithms.BlakleyAlgo;
import com.maliavin.algorithms.MinjotAlgo;
import com.maliavin.algorithms.ShamirAlgo;


public class Main {

	/**
	 * @param args
	 * 			terminal input parameters 
	 */
	public static void main(String[] args) {

	JFrame frame = new JFrame("Secret Sharing Analyzer");
	frame.setVisible(true);
	frame.setLayout(null);
	frame.setSize(400, 300);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	String[] schemesStrings = { "ShamirRSS", "BlakleyRSS", "AsmutBlumRSS", "RabinBenOrRSS", "KrawczykCSS" };

	//Create the combo box, select item at index 4.
	//Indices start at 0, so 4 specifies the pig.
	final JComboBox schemesList = new JComboBox(schemesStrings);
	schemesList.setSize(180, 25);
	schemesList.setLocation(20, 160);
	schemesList.setVisible(true);

	frame.add(schemesList);
	
	final JLabel labStatus = new JLabel("");
	labStatus.setSize(60, 25);
	labStatus.setVisible(true);
	labStatus.setLocation(20, 200);
	
	frame.add(labStatus);
	
	JButton butAnalyze = new JButton("Analyze");
	butAnalyze.setVisible(true);
	butAnalyze.setSize(100, 50);
	butAnalyze.setLocation(100, 200);
	
	final JTextField t = new JTextField("3");
	t.setVisible(true);
	t.setSize(30, 25);
	t.setLocation(350, 20);
	
	frame.add(t);
	
	final JLabel labT = new JLabel("t = ");
	labT.setVisible(true);
	labT.setSize(25, 20);
	labT.setLocation(320, 20);
	
	frame.add(labT);
	
	final JTextField n = new JTextField("5");
	n.setVisible(true);
	n.setSize(30, 25);
	n.setLocation(350, 50);
	
	frame.add(n);
	
	final JTextField tfFile = new JTextField();
	tfFile.setVisible(true);
	tfFile.setSize(100, 20);
	tfFile.setLocation(20,125);
	
	frame.add(tfFile);
	
	JLabel labFile = new JLabel("File");
	labFile.setVisible(true);
	labFile.setSize(50, 20);
	labFile.setLocation(20, 105);
	
	frame.add(labFile);
	
	final JLabel labN = new JLabel("n = ");
	labN.setVisible(true);
	labN.setSize(25, 20);
	labN.setLocation(320, 50);
	
	frame.add(labN);
	
	butAnalyze.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			analyze(schemesList.getSelectedIndex(), labStatus, n, t, tfFile);
		}
	});

	frame.add(butAnalyze);
	
	JButton butCharts = new JButton("Charts");
	butCharts.setVisible(true);
	butCharts.setSize(100, 50);
	butCharts.setLocation(210, 200);
	
	butCharts.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Chart chartShare = new Chart("Share");
			chartShare.pack();
			RefineryUtilities.centerFrameOnScreen(chartShare);
			chartShare.setVisible(true);
			
			Chart chartRestore = new Chart("Restore");
			chartRestore.pack();
			RefineryUtilities.centerFrameOnScreen(chartRestore);
			chartRestore.setVisible(true);
			
		}
	});
	
	frame.add(butCharts);
	
	JTextField extShare = new JTextField("External scheme sharing command");
	extShare.setVisible(true);
	extShare.setSize(250, 25);
	extShare.setLocation(20, 15);
	frame.add(extShare);
	
	JTextField extRestore = new JTextField("External scheme restoring command");
	extRestore.setVisible(true);
	extRestore.setSize(250, 25);
	extRestore.setLocation(20, 45);
	frame.add(extRestore);
	
	JCheckBox useExt = new JCheckBox("Use external scheme");
	useExt.setVisible(true);
	useExt.setSelected(false);
	useExt.setSize(150, 20);
	useExt.setLocation(15, 80);
	frame.add(useExt);

	}
	
	private static void analyze(int selectedScheme, JLabel labStatus, JTextField n, JTextField t, JTextField file)
	{
		
		String str = null;
		try {
			str = new String(Files.readAllBytes(Paths.get(file.getText())));
		} catch (IOException e) {
			System.out.println("read error - " + e.toString());
		}
		
		BigInteger bi = new BigInteger(str.getBytes());
		
		int nCount = Integer.parseInt(n.getText());
		int tCount = Integer.parseInt(t.getText());
		
		switch (selectedScheme) {
		case 0:
			shamirAnalyze(nCount,tCount,bi);
			break;
		case 1:
			blakleyAnalyze(nCount,tCount,bi);
			break;
		case 2:
			asmutBlumAnalyze(nCount,tCount,bi);
			break;
		case 3:
			rabinBenOrAnalyze(nCount,tCount,bi);
			break;
		case 4:
			krawczykAnalyze(nCount,tCount,bi);
			break;

		default:
			JOptionPane.showMessageDialog(null, "Scheme not specified");
		}
		
		labStatus.setText("SUCCESS");
	}
	
	private static void krawczykAnalyze(int n, int t, BigInteger bi) {
		try
		{
		long start = System.currentTimeMillis();
			
		KrawczykCSS krawczyk = new KrawczykCSS(7, 5,
				new BCDigestRandomSource(), new AESEncryptor(),
				new BerlekampWelchDecoderFactory(new GF256Factory()),
				new GF256Factory().createHelper());
		

		Share[] shares = krawczyk.share(bi.toByteArray());
		
		long time = start - System.currentTimeMillis();
		
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter(
					"d:/Dmitriy/Share.txt", true));
			outFile.println("Krawczyk_" + Math.abs(time) + "_" + (bi.bitLength()/8/1024));
			outFile.close();

		} catch (Exception e) {
			System.out.println("write error - " + e.toString());
		}
		
		start = System.currentTimeMillis();

		byte[] result = krawczyk.reconstruct(shares);

		String str2 = new String(result);

		time = start - System.currentTimeMillis();
		
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter(
					"d:/Dmitriy/Restore.txt", true));
			outFile.println("Krawczyk_" + Math.abs(time) + "_"
					+ (bi.bitLength() / 8 / 1024));
			outFile.close();

		} catch (Exception e) {
			System.out.println("write error - " + e.toString());
		}

	} catch (Exception e) {
		System.out.println(e.toString());
	}
		
	}

	private static void rabinBenOrAnalyze(int n, int t, BigInteger bi) {
		try {
			long start = System.currentTimeMillis();

			RabinBenOrEngine rabin = new RabinBenOrEngine(n, t);

			Share[] shares = rabin.share(bi.toByteArray());

			long time = start - System.currentTimeMillis();

			try {
				PrintWriter outFile = new PrintWriter(new FileWriter(
						"d:/Dmitriy/Share.txt", true));
				outFile.println("RabiBenOr_" + Math.abs(time) + "_" + (bi.bitLength()/8/1024));
				outFile.close();

			} catch (Exception e) {
				System.out.println("write error - " + e.toString());
			}

			start = System.currentTimeMillis();

			byte[] result = rabin.reconstruct(shares);

			String str2 = new String(result);

			time = start - System.currentTimeMillis();

			try {
				PrintWriter outFile = new PrintWriter(new FileWriter(
						"d:/Dmitriy/Restore.txt", true));
				outFile.println("RabiBenOr_" + Math.abs(time) + "_"
						+ (bi.bitLength() / 8 / 1024));
				outFile.close();

			} catch (Exception e) {
				System.out.println("write error - " + e.toString());
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static void asmutBlumAnalyze(int n, int t, BigInteger bi) {
		long start = System.currentTimeMillis();
		MinjotAlgo mj = new MinjotAlgo(bi, t, n);
		
		mj.generateSecretParts();
		
		long time = start - System.currentTimeMillis();
		
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter("d:/Dmitriy/Share.txt", true)); 
			outFile.println("AsmutBlum_"+Math.abs(time)+"_"+(bi.bitLength()/8/1024));
			outFile.close();
			
		} catch (Exception e) {
			System.out.println("write error - " + e.toString());
		}
		
		start = System.currentTimeMillis();
		
		BigInteger sec = mj.secretRecovery(0,1,2,3,4);
		
		
		byte [] bytes = sec.toByteArray();
		
		String str2 = new String(bytes);
		
		time = start - System.currentTimeMillis();
		
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter("d:/Dmitriy/Restore.txt", true)); 
			outFile.println("AsmutBlum_"+Math.abs(time)+"_"+(bi.bitLength()/8/1024));
			outFile.close();
			
		} catch (Exception e) {
			System.out.println("write error - " + e.toString());
		}
		
	}

	private static void blakleyAnalyze(int n, int t, BigInteger bi) {
	
		long start = System.currentTimeMillis();
		BlakleyAlgo ba = new BlakleyAlgo(bi, t, n);
		ba.generateParts();
		
		long time = start - System.currentTimeMillis();
		
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter("d:/Dmitriy/Share.txt", true)); 
			outFile.println("Blakley_"+Math.abs(time)+"_"+(bi.bitLength()/8/1024));
			outFile.close();
			
		} catch (Exception e) {
			System.out.println("write error - " + e.toString());
		}
		
		start = System.currentTimeMillis();
		List<BigInteger> list1 = ba.getOnePartOfSecrets(1);
		List<BigInteger> list2 = ba.getOnePartOfSecrets(3);
		List<BigInteger> list3 = ba.getOnePartOfSecrets(4);
		
		
		BigInteger [][] a = createAMatrix(list1, list2, list3);
		BigInteger [] b = new BigInteger[3];
		b[0] = list1.get(list1.size()-1);
		b[1] = list2.get(list2.size()-1);
		b[2] = list3.get(list3.size()-1);
		
		BigInteger detMain = ba.determinant(a);
		BigInteger [][] mat1 = ba.createMatrix(a, b, 0);
			
		BigInteger det = ba.determinant(mat1);
		
		BigInteger secretBlackley = det.divide(detMain);
		
		byte [] bytes = secretBlackley.toByteArray();
		
		String str2 = new String(bytes);
		
		time = start - System.currentTimeMillis();
		
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter("d:/Dmitriy/Restore.txt", true)); 
			outFile.println("Blakley_"+Math.abs(time)+"_"+(bi.bitLength()/8/1024));
			outFile.close();
			
		} catch (Exception e) {
			System.out.println("write error - " + e.toString());
		}
		
	}

//	private static void setMatrixByList(int row, SimpleMatrix sm, List<Integer> list)
//	{
//		for (int i = 0; i < list.size() - 1; i++)
//		{
//			sm.set(row, i, list.get(i));
//		}
//	}
	
	private static void shamirAnalyze(int n, int t, BigInteger bi) {
		
		long start = System.currentTimeMillis();
		ShamirAlgo sa = new ShamirAlgo(bi, 3, 5);
		List<BigInteger> list = sa.generatePartsOfSecret();
		
		long time = start - System.currentTimeMillis();
		
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter("d:/Dmitriy/Share.txt", true)); 
			outFile.println("Shamir_"+Math.abs(time)+"_"+(bi.bitLength()/8/1024));
			outFile.close();
			
		} catch (Exception e) {
			System.out.println("write error - " + e.toString());
		}
		
		start = System.currentTimeMillis();
		
		List<Integer> listX = Arrays.asList(1,3,5);
		List<BigInteger> listY = Arrays.asList(list.get(0),list.get(2),list.get(4));
		
		BigInteger sec = sa.getSecret(listX, listY);
		
		byte [] bytes = sec.toByteArray();
		
		String str2 = new String(bytes);
		
		time = start - System.currentTimeMillis();
		
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter("d:/Dmitriy/Restore.txt", true)); 
			outFile.println("Shamir_"+Math.abs(time)+"_"+(bi.bitLength()/8/1024));
			outFile.close();
			
		} catch (Exception e) {
			System.out.println("write error - " + e.toString());
		}
		
	}

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
